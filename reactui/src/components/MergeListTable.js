import React, {Component, PureComponent, useEffect, useRef, useState} from 'react';
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
                    {row.merged_at}
                </TableCell>
                <TableCell align="right">{row.title}</TableCell>
                <TableCell align="right">{row.mrScore}</TableCell>
                <TableCell align="right">{row.diffs}</TableCell>
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
                                        <TableRow key={commitsRow.created_at}>
                                            <TableCell component="th" scope="row">
                                                {commitsRow.created_at}
                                            </TableCell>
                                            <TableCell>{commitsRow.author_email}</TableCell>
                                            <TableCell>{commitsRow.commitScore}</TableCell>
                                            <TableCell align="right">{commitsRow.message}</TableCell>
                                            <TableCell align="right">{commitsRow.diffs}</TableCell>
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


// const rows = [
//     createData('Frozen yoghurt', 159, 6.0, 24, 4.0, 3.99),
//     createData('Ice cream sandwich', 237, 9.0, 37, 4.3, 4.99),
//     createData('Eclair', 262, 16.0, 24, 6.0, 3.79),
//     createData('Cupcake', 305, 3.7, 67, 4.3, 2.5),
//     createData('Gingerbread', 356, 16.0, 49, 3.9, 1.5),
// ];

function createData(id, date, score, title, fullDiff, commits) {
    return {
        date,
        score,
        title,
        fullDiff,
        commits // [
        //     { created_at: null, author_email: null, commitScore: 1, message: null, diffs : [{diff:null}] }]
    };
}

//         {merged_at: '',
//             mrScore: '',
//             title: '',
//             diffs: '',
//             commits:[
//                 {created_at: '',
//                     author_email: '',
//                     commitScore: '',
//                     message: '',
//                     diffs : [
//                         {diff:''}
//                     ]
//                 }
//                 ]
// }

export default function MergeListTable  ({devName}) { //extends Component

    const [merges, getMerges] = useState([]);
    const mounted = useRef();

    useEffect(()=> {
        if (!mounted.current) {
            getDataFromBackend(devName)
            mounted.current = true;
        } else {
            getDataFromBackend(devName)
        }
    }, [merges]);

    function getDataFromBackend (username) {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];
        axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + "/2021-01-01/2021-05-09")
        .then(res => {
            getMerges(res.data);
            console.log(merges);
        }).catch((error) => {
        console.error(error);
    });}

    const output = merges.map(function(item) {
        return {
            date: item.merged_at,
            title: item.title,
            score: item.mrScore,
            diffs: item.diffs.map(function (diffs) {
                return {
                    diff: diffs.diff
                };
            }),
            commits: item.commits.map(function (commit) {
                return {
                    date: commit.date,
                    message: commit.message,
                    score: commit.commitScore,
                    author: commit.author_email,

                    diffs: commit.diffs.map(function (diffs) {
                        return {
                            diff: diffs.diff
                        };
                    })
                };
            })
        };
    });

    console.log(output);
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();


    return (
        <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell/>
                        <TableCell aligh="left">Date</TableCell>
                        <TableCell>Merge Title</TableCell>
                        <TableCell align="right">Score</TableCell>
                        <TableCell align="right">Full Diff</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    <TableRow className={classes.root}>
                        <TableCell>
                            <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                                {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                            </IconButton>
                        </TableCell>
                        <TableCell component="th" scope="row">
                            {output.date}
                        </TableCell>
                        <TableCell align="right">{output.title}</TableCell>
                        <TableCell align="right">{output.score}</TableCell>
                        {/*<TableCell align="right">{output.diffs}</TableCell>*/}
                    </TableRow>
                </TableBody>
            </Table>
        </TableContainer>
    );
}



Row.propTypes = {
    row: PropTypes.shape({
        merged_at: PropTypes.string.isRequired,
        title: PropTypes.string.isRequired,
        mrScore: PropTypes.number.isRequired,
        commits: PropTypes.arrayOf(
            PropTypes.shape({
                created_at: PropTypes.string.isRequired,
                author_email: PropTypes.string.isRequired,
                commitScore: PropTypes.number.isRequired,
                message: PropTypes.string.isRequired,
                diffs: PropTypes.string.isRequired,
            }),
        ).isRequired,
        diffs: PropTypes.string.isRequired,
    }).isRequired,
};

//
// constructor(props) {
//     super(props);
//     this.state = {
//         merges:[
//             //     {
//             //     merged_at: '',
//             //     mrScore: '',
//             //     title: '',
//             //     diffs: '',
//             //     commits:[
//             //         {created_at: '',
//             //             author_email: '',
//             //             commitScore: '',
//             //             message: '',
//             //             diffs : [
//             //                 {diff:''}
//             //             ]
//             //         }]
//             // }
//         ],
//         parentdata: this.props.devName,
//         startTime: this.props.startTime,
//         endTime: this.props.endTime
//     }
// }
//
// async componentDidMount(){
//     const {parentdata} = this.state;
//     await this.getDataFromBackend(parentdata, this.props.startTime,  this.props.endTime )
// }
//
// async getDataFromBackend (username, startTm, endTm) {
//     var pathArray = window.location.pathname.split('/');
//     var id = pathArray[2];
//     var name = username;
//     if(sessionStorage.getItem('DeveloperNames' + id) != null && sessionStorage.getItem('Developers' + id) != null) {
//         for (var i = 0; i < JSON.parse(sessionStorage.getItem('Developers' + id)).length; i++) {
//             if (JSON.stringify(username) === JSON.stringify(JSON.parse(sessionStorage.getItem('Developers' + id))[i])) {
//                 name = JSON.parse(sessionStorage.getItem('DeveloperNames' + id))[i]//use name to retrieve data
//             }
//         }
//     }
//
//     const response = await axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + '/' +
//         startTm + '/' + endTm)
//
//     const mergeData = await response.data
//     await this.setState({merges : mergeData, parentdata: username, startTime: startTm,
//         endTime: endTm})
//     await console.log(this.state.merges)
// }
//
// async componentDidUpdate(prevProps){
//     if (this.props.devName !== prevProps.devName ||
//         this.props.startTime !== prevProps.startTime ||
//         this.props.endTime !== prevProps.endTime){
//         await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
//     }
// }
