// Table structure is based on the library from [https://material-ui.com/components/tables/]
import React from "react";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import moment from "moment";
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import {OverlayTrigger, Popover} from "react-bootstrap";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core/styles";
import Highlight from "react-highlight";
import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";

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

export default function CommitList(props) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();

    return (
        <TableContainer component={Paper} margin-right="300px">
            <Table aria-label="collapsible table" size="small" aria-label="commits">
                <TableHead className="tableCell">
                    <TableRow>
                        <TableCell>Date</TableCell>
                        <TableCell>Commit Message</TableCell>
                        <TableCell align="right">Committer</TableCell>
                        <TableCell align="right">Score</TableCell>
                        <TableCell align="right">Code Diff</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {row.map((commitsRow) => (
                        // item.commits.map ((commitsRow) => (
                        <TableRow key={commitsRow.commitDate}>
                            <TableCell component="th" scope="row">
                                {moment(commitsRow.commitDate).format('LLL')}

                            </TableCell>

                            <TableCell>{commitsRow.message}</TableCell>
                            <TableCell align="right">{commitsRow.author}</TableCell>
                            <TableCell align="right">{commitsRow.score.toFixed(1)}</TableCell>
                            <TableCell align="right" >
                                <OverlayTrigger trigger="focus" placement="right" overlay={<PopOver Diffs={commitsRow.commitDiffs}/>}>
                                    <button type="button" className="btn btn-outline-secondary">View</button>
                                </OverlayTrigger>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
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