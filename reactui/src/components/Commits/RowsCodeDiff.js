import React from "react";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import {makeStyles} from "@material-ui/core/styles";
import {OverlayTrigger} from 'react-bootstrap'
import Highlight from "react-highlight";
import 'bootstrap/dist/css/bootstrap.min.css';
import './TableStyle.css'
import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";
import Flexbox from "flexbox-react";
import TableContainer from "@material-ui/core/TableContainer";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import Table from "@material-ui/core/Table";
import Collapse from "@material-ui/core/Collapse";

export default function Row(props) {

    const { row } = props;
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

    const oldF = row.old_path;
    const newF = row.new_path;

    return (
        <React.Fragment >
            <TableRow
                className="commitTable">
                <TableCell
                    align="top"
                    style={{wordWrap: "break-word", maxWidth:"390px", fontSize:"15", fontWeight:"bold"}}>
                    {row.renamed_file ? oldF + "\nRENAMED TO:\n" + newF : newF}
                </TableCell>
                <TableCell
                    align="right"
                    style={{color:"blue", fontSize:"15", fontWeight:"bold"}}>
                    {"+" + row.diffScore}
                </TableCell>

                <TableCell
                    align="right">
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>

                <TableCell
                    align ="right">
                    <button
                        type="button"
                        onClick={(e) => {
                            e.preventDefault();
                        }}>
                        TODO
                    </button>
                </TableCell>
            </TableRow>

            <TableRow
                style={{backgroundColor: "rgb(242, 242, 242)"}}>
                <TableCell
                    style={{ paddingBottom: 0, paddingTop: 0 }}
                    colSpan={6}>
                    <Collapse
                        in={open}
                        timeout="auto"
                        unmountOnExit>
                        <Box>
                            <Table size="small" aria-label="commits">
                                <tbody>
                                <tr>
                                    <td
                                        style={{wordWrap: "break-word", maxWidth:"500px"}} >
                                        {HighlightCodeDiffs(row.diff)}
                                    </td>
                                </tr>
                                </tbody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}
