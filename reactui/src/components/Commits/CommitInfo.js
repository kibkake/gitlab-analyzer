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
import {OverlayTrigger} from 'react-bootstrap'
import Highlight from "react-highlight";
import 'bootstrap/dist/css/bootstrap.min.css';
import moment from "moment";
import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";
import Flexbox from "flexbox-react";
import TableContainer from "@material-ui/core/TableContainer";



// Table structure is based on the library from [https://material-ui.com/components/tables/]
export default function Row(props) {

    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();


    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell component="th" scope="row">
                    {moment(row.committed_date).format('lll')}

                </TableCell>
                <TableCell><a href= {row.mrUrl}> {row.title.substring(0,10)} {row.title.length > 10 ? <div>...</div> : <div></div>}</a> </TableCell>
                <TableCell align="right">{row.commitScore.toFixed(1)}</TableCell>

                <TableCell align="right">
                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>

                <TableCell align ="right">

                </TableCell>
            </TableRow>


        </React.Fragment>

    );
}

