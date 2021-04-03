import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import moment from "moment";
import {Table} from "react-bootstrap";
import TableBody from "@material-ui/core/TableBody";
import Row from "./RowsCodeDiff";
import TableCell from "@material-ui/core/TableCell";

export default function ExpandButton(props) {
    return (
        <TableContainer style={{ overflowX: "scroll" , height: "1050px", width: "600px"}}
                        component={Paper}
                        display="flex"
                        flexDirection="row"
                        p={1}
                        m={1}
                        justifyContent="flex-start">
                {props.ever.map((item) => (
                    <div style={{fontWeight: 'bold',
                        fontSize: '20px',
                        color: 'black',
                        backgroundColor: 'lightgreen'}}>
                        {moment(item.committed_date).format('lll').substring(12,21)}
                        {"  |   Total Commit Score: "}
                        {item.commitScore}</div>
                ))}
                <button style={{
                    backgroundColor: 'lightblue',
                    color: 'black',
                    borderRadius: '0%'}}
                    type="button"
                    onClick={(e) => {
                        e.preventDefault();
                        props.handler()
                    }}>
                    EXPAND ALL
                </button>

                <Table aria-label="collapsible table" >
                    <TableBody>
                        {props.ever.map((item) =>

                            item.diffs.map((item2, index) => (
                                <Row key={item2.new_path} row={item2}
                                handler = {props.handler}
                                expanded = {props.expanded}/>
                            )))}
                    </TableBody>
                </Table>
        </TableContainer>
    );
}


