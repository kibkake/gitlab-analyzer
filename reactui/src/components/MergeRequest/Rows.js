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
import Diffs from './Diffs'
import {OverlayTrigger, Popover} from 'react-bootstrap'
import Button from "react-bootstrap/Button";
import Highlight from "react-highlight";
import 'bootstrap/dist/css/bootstrap.min.css';

//[https://stackoverflow.com/questions/48780494/how-to-pass-value-to-popover-from-renderer]
const PopOver = ({Diffs}) => {
    return (
         <div>
             <Popover id="popover-basic" placement='right' class="justify-content-end" >
                 {Diffs.map((item => {
                     return(
                         <ul>
                             <Popover.Title as="h3">{item.path}</Popover.Title>
                             <Popover.Content><Highlight className="highlighted-text"> {item.diff} </Highlight>
                             </Popover.Content>
                         </ul>
                     )
                }))}
             </Popover>
         </div>
    )
}


export default function Row(props) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const [showDiff, setShowDiff] = React.useState(false);
    const [showCommitDiff, setShowCommitDiff] = React.useState(false);

    const classes = useRowStyles();

    return (
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
                <TableCell align ="right">
                    <OverlayTrigger trigger="focus" placement="right" class = "justify-content-end" overlay={<PopOver Diffs={row.diffs} />}>
                        <button type="button" className="btn btn-secondary">View</button>
                        {/*<IconButton aria-label="expand column" size="small" onClick={() => setShowDiff(!showDiff)}>*/}
                        {/*    {showDiff ? <KeyboardArrowLeftRounded /> : <KeyboardArrowRightRounded />}*/}
                        {/*</IconButton>*/}
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
                                                {commitsRow.commitDate}
                                            </TableCell>
                                            <TableCell>{commitsRow.message}</TableCell>
                                            <TableCell align="right">{commitsRow.author}</TableCell>
                                            <TableCell align="right">{commitsRow.score}</TableCell>
                                            <TableCell align="right" >
                                                <OverlayTrigger trigger="focus"  placement="right" overlay={<PopOver Diffs={commitsRow.commitDiffs}/>}>
                                                    <button type="button" className="btn btn-outline-secondary">View</button>

                                                    {/*<IconButton aria-label="expand column" size="small" onClick={() => setShowCommitDiff(!showCommitDiff)}>*/}
                                                    {/*  {showCommitDiff ? <KeyboardArrowLeftRounded /> : <KeyboardArrowRightRounded />}*/}
                                                    {/*</IconButton>*/}
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
    tablecell: {
        '& > *': {
            borderBottom: 'unset',
            fontSize: '20pt',
            fontWeight: 'bold',
        },
    }
});

// {/*    <div class="p-2">*/}
// {/*        */}
// {/*    </div>*/}
// {/*        */}
// {/*</div>*/}
