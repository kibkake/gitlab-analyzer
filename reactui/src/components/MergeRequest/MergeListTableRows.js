import React from "react";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core/styles";
import {OverlayTrigger, Popover} from 'react-bootstrap'
import Highlight from "react-highlight";
import 'bootstrap/dist/css/bootstrap.min.css';
import moment from "moment";
import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";
import Flexbox from "flexbox-react";
import DiffTable from "./DiffTable";
import TableContainer from "@material-ui/core/TableContainer";

//[https://stackoverflow.com/questions/48780494/how-to-pass-value-to-popover-from-renderer]
const PopOver = ({Diffs}) => {
    return (
            <Popover id="popover-basic" placement='right' class="justify-content-end" >
                 {Diffs.map((item => {
                     return(
                         <ul>
                             <Popover.Title as="h3">{item.path}</Popover.Title>
                             <Popover.Content><Highlight className="highlighted-text"> {HighlightCodeDiffs(item.diff)} </Highlight>
                             </Popover.Content>
                         </ul>
                     )
                }))}
            </Popover>
    )
}


// Table structure is based on the library from [https://material-ui.com/components/tables/]
export default function Row(props) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();


    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell component="th" scope="row">
                    {moment(row.date).format('lll')}
                </TableCell>
                <TableCell>#{row.id} <a href= {row.mrUrl}> {row.title}</a> </TableCell>
                <TableCell align="right">{row.score.toFixed(1)}</TableCell>
                <TableCell align="right"> {row.sum}</TableCell>

                <TableCell align="right">
                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>

                <TableCell align ="right">
                    <OverlayTrigger trigger="focus" placement="right" justifyContent="flex-start"
                                    display="flex" flexDirection="row" p={1} m={1}
                                    overlay={<PopOver order={3} Diffs={row.diffs} />}>
                        <button type="button" order={1} className="btn btn-secondary">View</button>
                    </OverlayTrigger>
                </TableCell>
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
                                        <TableCell>Commit Message</TableCell>
                                        <TableCell align="right">Committer</TableCell>
                                        <TableCell align="right">Score</TableCell>
                                        <TableCell align="right">Code Diff</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {row.commits.map((commitsRow) => (
                                        <TableRow key={commitsRow.commitDate}>
                                            <TableCell component="th" scope="row">
                                                {moment(commitsRow.commitDate).format('LLL')}

                                            </TableCell>

                                            <TableCell>{commitsRow.message}</TableCell>
                                            <TableCell align="right">{commitsRow.author}</TableCell>
                                            <TableCell align="right">{commitsRow.score.toFixed(1)}</TableCell>
                                            <TableCell align="right" >
                                                <OverlayTrigger trigger="focus"  placement="right" overlay={<PopOver Diffs={commitsRow.commitDiffs}/>}>
                                                    <button type="button" className="btn btn-outline-secondary">View</button>
                                                </OverlayTrigger>
                                            </TableCell>
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

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
            fontSize: '15pt',
            backgroundColor: 'lightgrey',

        },
    },
});
