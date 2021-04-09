import React from "react";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core/styles";
import 'bootstrap/dist/css/bootstrap.min.css';
import {ClickAwayListener, Tooltip, withStyles} from "@material-ui/core";
import './MergeListTable.css'
import DiffTable from "./DiffTable";

// Table structure is based on the library from [https://material-ui.com/components/tables/]
export default function Row(props) {
    const {row} = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();

    const [tooltipOpen, tooltipSetOpen] = React.useState(false);
    const handleTooltipClose = () => { tooltipSetOpen(false);}
    const handleTooltipOpen = () => {tooltipSetOpen(true);}

    const [commitTooltipOpen, commitTooltipSetOpen] = React.useState(false);
    const handleCommitTooltipClose = () => {commitTooltipSetOpen(false);}
    const handleCommitTooltipOpen = () => {commitTooltipSetOpen(true);}

    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell align="left" component="th" scope="row">
                    {row.date}
                </TableCell>
                <TableCell>#{row.id} <a href={row.mrUrl}> {row.title}</a> </TableCell>
                <TableCell align="right">{row.score}</TableCell>
                <TableCell align="right"> {row.sum}</TableCell>

                <TableCell align="right">
                    <ClickAwayListener onClickAway={handleTooltipClose}>
                        <div>
                            <StyledTooltip arrow
                                           data-trigger="focus"
                                           placement={"right-start"}
                                           onClose={handleTooltipClose}
                                           open={tooltipOpen}
                                           disableFocusListener
                                           disableHoverListener
                                           disableTouchListener
                                           title={
                                               <div style={{"maxHeight": 1500, "overflow-y": "auto", "pointer-events": "auto"}}>
                                                  <DiffTable data={row}/>
                                               </div>
                                           }>
                                <button aria-label="expand row" size="small" onClick={() => {
                                    setOpen(!open);
                                    handleTooltipOpen();
                                }}
                                        type="button" order={1} className="btn btn-secondary">View
                                </button>
                            </StyledTooltip>
                        </div>
                    </ClickAwayListener>
                </TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
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
                                            <TableCell component="th" scope="row">{commitsRow.commitDate}</TableCell>
                                            <TableCell>{commitsRow.message}</TableCell>
                                            <TableCell align="right">{commitsRow.author}</TableCell>
                                            <TableCell align="right">{commitsRow.score}</TableCell>
                                            <TableCell align="right">
                                                <ClickAwayListener onClickAway={handleCommitTooltipClose}>
                                                    <div>
                                                        <StyledTooltip arrow
                                                                       placement={"right-start"}
                                                                       onClose={handleCommitTooltipClose}
                                                                       open={commitTooltipOpen}
                                                                       disableFocusListener
                                                                       disableHoverListener
                                                                       disableTouchListener
                                                                       title={
                                                                           <div style={{"maxHeight": 1500, "overflow-y": "auto", "pointer-events": "auto"}}>
                                                                               <DiffTable data={commitsRow}/>
                                                                           </div>
                                                                       }>
                                                            <button type="button" className="btn btn-outline-secondary"
                                                                    onClick={() => handleCommitTooltipOpen()}>View
                                                            </button>
                                                        </StyledTooltip>
                                                    </div>
                                                </ClickAwayListener>
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
            fontSize: '11pt',
            backgroundColor: 'lightgrey',

        },
    },
});

const StyledTooltip = withStyles((theme) => ({
    tooltip: {
        backgroundColor: '#f5f5f9',
        color: 'rgba(0, 0, 0, 0.87)',
        maxWidth: 550,
        fontSize: theme.typography.pxToRem(12),
        border: '1px solid #dadde9',
    },
    tooltipPlacementRight: {
        margin: "10px",
    },
}))(Tooltip);
