import React from "react";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import {KeyboardArrowLeftRounded, KeyboardArrowRightRounded} from "@material-ui/icons";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core/styles";
import MergeListTable from "./MergeListTable";
import Diffs from './Diffs'

const useRowStyles = makeStyles({
    root: {
        '& > *': {
              borderBottom: 'unset',
            fontSize: '15pt',
            backgroundColor: 'lightgrey',

        },
    },
    tablecell: {
        '& > *': {
            borderBottom: 'unset',
            fontSize: '20pt',
            fontWeight: 'bold',
        },
    }
});

export default function Row(props) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const [showDiff, setShowDiff] = React.useState(false);
    const classes = useRowStyles();

    return (
        // <div className="d-flex flex-row">
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell component="th" scope="row">
                    {row.date}
                </TableCell>
                <TableCell>#{row.id} {row.title}</TableCell>
                <TableCell align="right">{row.score}</TableCell>
                <TableCell align="right">
                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell>
                    <IconButton align ="right" aria-label="expand column" size="small" onClick={() => setShowDiff(true)}>
                        {showDiff ? <KeyboardArrowLeftRounded /> : <KeyboardArrowRightRounded />}
                    </IconButton>
                    {/*<Diffs data = {showDiff}></Diffs>*/}
                    <Diffs closeOnOutsideClick={true} trigger={showDiff} setTrigger = {setShowDiff} >
                        {row.diffs}
                    </Diffs>
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
                                                {commitsRow.commitDate}
                                            </TableCell>
                                            <TableCell>{commitsRow.message}</TableCell>
                                            <TableCell align="right">{commitsRow.author}</TableCell>
                                            <TableCell align="right">{commitsRow.score}</TableCell>
                                            <TableCell>
                                            <IconButton aligh="right" aria-label="expand column" size="small" onClick={() => setShowDiff(!showDiff)}>
                                                {showDiff ? <KeyboardArrowLeftRounded /> : <KeyboardArrowRightRounded />}
                                            </IconButton>
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

// {/*    <div class="p-2">*/}
// {/*        */}
// {/*    </div>*/}
// {/*        */}
// {/*</div>*/}
