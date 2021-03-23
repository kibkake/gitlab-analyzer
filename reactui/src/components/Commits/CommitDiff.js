import React, {Component} from 'react'
import "../Projects/ProjectList.css";
import {Table} from "react-bootstrap";
import HighlightCodeDiffs from "./HighlightCodeDiffs"
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Row from "./RowsCodeDiff";
import Typography from "@material-ui/core/Typography";
import moment from "moment";
import ExpandButton from "./ExpandButton";

class SingleCommitDiff extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            hash : this.props.hash,
            expanded: false
        };

    }



    render() {
        return (
            <div>
                </div>
        )
    }
}

export default SingleCommitDiff;

