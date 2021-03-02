import React, {useEffect, useRef, useState} from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import axios from "axios";

//[https://material-ui.com/components/tables/]
const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

function createData(id, date, score, title, fullDiff) {
    return {
        id,
        date,
        score,
        title,
        fullDiff,
        commits: [
            { date: '2020-01-05', committer: '11091700', score: 1, msg: 3, codeDiff:"" },
            { date: '2020-01-02', committer: 'Anonymous', score: 1, msg: 1, codeDiff:"" },
        ],
    };
}

function Row(props) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();

    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell>
                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell component="th" scope="row">
                    {row.name}
                </TableCell>
                <TableCell align="right">{row.date}</TableCell>
                <TableCell align="right">{row.title}</TableCell>
                <TableCell align="right">{row.score}</TableCell>
                <TableCell align="right">{row.fullDiff}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Typography variant="h6" gutterBottom component="div">
                                Commits
                            </Typography>
                            <Table size="small" aria-label="commits">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Date</TableCell>
                                        <TableCell>Committer</TableCell>
                                        <TableCell align="right">Score</TableCell>
                                        <TableCell align="right">Commit Message</TableCell>
                                        <TableCell align="right">Code Diff</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {row.commits.map((commitsRow) => (
                                        <TableRow key={commitsRow.date}>
                                            <TableCell component="th" scope="row">
                                                {commitsRow.date}
                                            </TableCell>
                                            <TableCell>{commitsRow.committer}</TableCell>
                                            <TableCell align="right">{commitsRow.score}</TableCell>
                                            <TableCell align="right">{commitsRow.msg}</TableCell>
                                            <TableCell align="right">{commitsRow.codeDiff}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}

// Row.propTypes = {
//     row: PropTypes.shape({
//         calories: PropTypes.number.isRequired,
//         carbs: PropTypes.number.isRequired,
//         fat: PropTypes.number.isRequired,
//         history: PropTypes.arrayOf(
//             PropTypes.shape({
//                 amount: PropTypes.number.isRequired,
//                 customerId: PropTypes.string.isRequired,
//                 date: PropTypes.string.isRequired,
//             }),
//         ).isRequired,
//         name: PropTypes.string.isRequired,
//         price: PropTypes.number.isRequired,
//         protein: PropTypes.number.isRequired,
//     }).isRequired,
// };

const rows = [
    createData('Frozen yoghurt', 159, 6.0, 24, 4.0, 3.99),
    createData('Ice cream sandwich', 237, 9.0, 37, 4.3, 4.99),
    createData('Eclair', 262, 16.0, 24, 6.0, 3.79),
    createData('Cupcake', 305, 3.7, 67, 4.3, 2.5),
    createData('Gingerbread', 356, 16.0, 49, 3.9, 1.5),
];

export default function CollapsibleTable() {
    const [commits, getCommits]=useState([]);
    const [buttonPopup, setButtonPopup] = useState(false);

    const mounted = useRef();
    useEffect(()=>{
        if (!mounted.current) {
            getDataFromBackend(devName)

            mounted.current = true;
        } else {
            getDataFromBackend(devName)}
    }, [commits]);

    function getDataFromBackend (username) {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];

        axios.get("/api/v1/projects/" + id + "/Commits/" + devName + "/2021-01-01/2021-05-09")
            .then(response => {
                getCommits(response.data)
            }).catch((error) => {
            console.error(error);
        });}

    return (
        <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell />
                        <TableCell>Date</TableCell>
                        <TableCell align="right">Merge Title</TableCell>
                        <TableCell align="right">Score</TableCell>
                        <TableCell align="right">Full Diff</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row) => (
                        <Row key={row.name} row={row} />
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
