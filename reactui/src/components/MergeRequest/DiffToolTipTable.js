import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import TableContainer from "@material-ui/core/TableContainer";
import React from "react";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";
import Highlight from "react-highlight";

export default function DiffTable (props) {
    const {diffs} = props;

    const [open, setOpen] = React.useState(false);
    var  expanded  = props.expanded

    // const [expanded, setExpand] = React.useState(false);
    //
    // React.useEffect(() => {
    //     if(expanded) {
    //         setOpen(true)
    //     }
    //     if(!expanded){
    //         setOpen(false)
    //     }
    // }, [expanded]);

    return (
            <TableContainer component={Paper}>
                <Table hover aria-label="collapsible table">
                    <TableBody>
                        <React.Fragment>
                            <TableRow>
                                <TableCell align="left" component="th" scope="row">
                                    <h5><u>{diffs.path}</u> (+{diffs.diffScore})</h5>
                                </TableCell>
                                <TableCell align="right">
                                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                                    <Collapse in={open} timeout="auto" unmountOnExit>
                                        <Box>
                                            <Table size="small" aria-label="commits">
                                                <TableBody>
                                                    <TableRow>
                                                        <TableCell><Highlight
                                                            className="highlighted-text">{HighlightCodeDiffs(diffs.diff)}</Highlight></TableCell>
                                                    </TableRow>
                                                </TableBody>
                                            </Table>
                                        </Box>
                                    </Collapse>
                                </TableCell>
                            </TableRow>
                        </React.Fragment>
                    </TableBody>
                </Table>
            </TableContainer>
    )
}
//<DiffRow key={diffs.path} row={diffs}/>

const DiffRow = (props) =>{
    const {row} = props;
    var  expanded  = props.expanded

    const [open, setOpen] = React.useState(false);

    React.useEffect(() => {

        if(expanded) {
            setOpen(true)
        }
        if(!expanded){
            setOpen(false)
        }

    }, [expanded]);

    return (
        <React.Fragment>
            <TableRow>
                <TableCell align="left" component="th" scope="row">
                    <h5><u>{row.path}</u> (+{row.diffScore})</h5>
                </TableCell>
                <TableCell align="right">
                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box>
                            <Table size="small" aria-label="commits">
                                <TableBody>
                                    <TableRow>
                                        <TableCell><Highlight className="highlighted-text">{HighlightCodeDiffs(row.diff)}</Highlight></TableCell>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}