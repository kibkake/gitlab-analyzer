import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import moment from "moment";

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
        </TableContainer>

    );
}


