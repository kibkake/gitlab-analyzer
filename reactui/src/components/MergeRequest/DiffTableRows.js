import React from "react";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import Highlight from "react-highlight";
import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";


export default function DiffRow (props) {
    const {diff} = props;
    const expanded  = props.expanded;

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
                    <h6><u>{diff.path}</u></h6>
                </TableCell>
                <TableCell>
                    <h6>+{diff.diffScore}</h6>
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
                                            className="highlighted-text">{HighlightCodeDiffs(diff.diff)}</Highlight></TableCell>
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
