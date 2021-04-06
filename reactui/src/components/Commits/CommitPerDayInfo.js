import React from "react";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import IconButton from "@material-ui/core/IconButton";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import {makeStyles} from "@material-ui/core/styles";
import {OverlayTrigger} from 'react-bootstrap'
import Highlight from "react-highlight";
import 'bootstrap/dist/css/bootstrap.min.css';
import moment from "moment";
import './TableStyle.css'
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';

function CommitPerDayInfo(props) {

    const [open, setOpen] = React.useState(
        false
    );

    if(sessionStorage.getItem("excludedFiles") === null){
        var fileArray = []
        sessionStorage.setItem("excludedFiles",  JSON.stringify(fileArray))
    }

    var tempArray = []
    tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
    var isInArray = false
    props.commits.map(function (item) {
        item.diffs.map(function (item2) {
            for (var i = 0; i < tempArray.length; i++) {
                if (tempArray[i] === props.commit.id + "_" + item2.new_path) {
                    isInArray = true
                }
            }
        })
    });

    return (
        <React.Fragment>
            <TableRow className="commitTable">
                <TableCell component="th" scope="row">
                    {moment(props.commit.committed_date).format('lll').substring(12,21)}
                </TableCell>
                <TableCell>
                {props.commit.title.length > 10 ? props.commit.title.substring(0,10) + "..." :
                    props.commit.title.substring(0,10)}
                </TableCell>
                <TableCell align="right">
                    {isInArray? <div style={{color:"red", fontSize:"15", fontWeight:"bold"}}> {"+" + props.commit.commitScore.toFixed(1)} </div> : <div style={{color:"blue", fontSize:"15", fontWeight:"bold"}}> {"+" + props.commit.commitScore.toFixed(1)} </div>}

                </TableCell>

                <TableCell align="right">
                    <IconButton aria-label="expand row" size="small"
                                onClick={() => setOpen(!open)}>
                                {<MoreHorizIcon />}
                    </IconButton>
                </TableCell>

                <TableCell align ="right">
                    <button style={{
                            backgroundColor: 'lightblue',
                            color: 'black',
                            borderRadius: '0%'
                            }}
                            type="button"
                            onClick={(e) => {
                                e.preventDefault();
                                {props.resetSingleCommitScore()}
                                {props.handler(props.commit.id)}
                            }}>
                        DIFF
                    </button>
                </TableCell>
            </TableRow>

            <TableRow
                style={{backgroundColor:"rgb(242, 242, 242)"}}>
                <TableCell
                    style={{ paddingBottom: 0, paddingTop: 0 }}
                    colSpan={6}>
                    <Collapse
                        in={open}
                        timeout="auto"
                        unmountOnExit>
                        <Box>
                            <Table size="small" aria-label="commits">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Title</TableCell>
                                        <TableCell align="left">
                                            Full Message
                                        </TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    <TableRow>
                                        <TableCell style={{wordWrap: "break-word",
                                            maxWidth:"250px"}}>
                                            {props.commit.title}
                                        </TableCell>
                                        <TableCell style={{wordWrap: "break-word",
                                            maxWidth:"250px"}}>
                                            {props.commit.message}
                                        </TableCell>
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

export default CommitPerDayInfo;