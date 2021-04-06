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

    const [open, setOpen] = React.useState(
        false
    );

    const [checked,setChecked] = React.useState(true)

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

    if(sessionStorage.getItem("excludedFiles") === null){
        var fileArray = []
        sessionStorage.setItem("excludedFiles",  JSON.stringify(fileArray))
    }
    var tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))

    return (
        <React.Fragment >
            <TableRow
                className="commitTable">
                <TableCell
                    align="top"
                    style={{wordWrap: "break-word", maxWidth:"300px", fontSize:"15", fontWeight:"bold"}}>
                    {row.renamed_file ? oldF + "\nRENAMED TO:\n" + newF : newF}
                </TableCell>

                <TableCell
                    style={{maxWidth:"50px"}}

                align="right">
                    {checkIfFileIsExcluded(tempArray, newF, props.hash) ? <div style={{color:"red", fontSize:"15", fontWeight:"bold"}}> {"+" + row.diffScore} </div> : <div style={{color:"blue", fontSize:"15", fontWeight:"bold"}}> {"+" + row.diffScore} </div>}
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
                    style={{display:"flex", flexDirection:"column"}}
                    align ="left">
                    <button
                        style={{
                            backgroundColor: 'red',
                            color: 'black',
                            borderRadius: '0%'
                        }}
                        type="button"
                        onClick={(e) => {
                            e.preventDefault();
                            {checkIfFileIsExcluded(tempArray, newF, props.hash) ? console.log("already excluded") : props.addExcludedPoints(row.diffScore)}
                            addFileToListOfExcluded(tempArray, newF, props.hash)
                            setChecked(!checked)
                        }}>
                        IGNORE
                    </button>

                    <button
                        style={{
                            backgroundColor: 'cyan',
                            color: 'black',
                            borderRadius: '0%'
                        }}
                        type="button"
                        onClick={(e) => {
                            e.preventDefault();
                            {checkIfFileIsExcluded(tempArray, newF, props.hash) ?  props.addExcludedPoints(-row.diffScore) : console.log("not excluded")}
                            removeFromExcludedFiles(tempArray, newF, props.hash)
                            setChecked(!checked)
                        }}>
                        {"RE-ADD"}
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

function addFileToListOfExcluded(tempArray, newF, hash){
    tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
    for(var i = 0; i < tempArray.length; i++){
        if(tempArray[i] === hash + "_" + newF ){
            return
        }
    }
    tempArray.push(hash + "_" + newF)
    sessionStorage.setItem("excludedFiles",JSON.stringify( tempArray))
    console.log("hello", JSON.parse(sessionStorage.getItem("excludedFiles")))
}

function checkIfFileIsExcluded(tempArray, newF, hash){
    tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
    for(var i = 0; i < tempArray.length; i++){
        if(tempArray[i] === hash + "_" + newF ){
            return true;
        }
    }
    return false;
}

function removeFromExcludedFiles(tempArray, newF, hash){
    tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
    for(var i = 0; i < tempArray.length; i++){
        if(tempArray[i] === hash + "_" + newF ){
            tempArray.splice(i, 1);
            //return
        }
    }
    sessionStorage.setItem("excludedFiles",JSON.stringify( tempArray))
    console.log("hello", JSON.parse(sessionStorage.getItem("excludedFiles")))
}

