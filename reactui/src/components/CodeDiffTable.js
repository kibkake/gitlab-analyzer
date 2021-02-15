import "../App.css"
import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import Highlight from 'react-highlight'

// class CodeDiffTable extends Component{
function CodeDiffTable() {

    // TODO: Do not delete, will be used once the API is set
    // constructor(props) {
    //     super(props);
    //     this.state = {
    //         WrapperUser:[]
    //     }
    // }
    //
    // componentDidMount() {
    //     ProjectService.getCodeDiff().then((response) => {
    //         this.setState({WrapperUser: response.data})??
    //     });
    // }

    //Fake data for table creation testing
    const codeDiff = [
        {mergeRequestId:3, date: 204, score: 2, commitComments: "initial commit", codeDiff: "@@ -5,7 +5,10 @@ public class Main {\n         // f1-1a\n         //f1-1a\n \n+        //f1-2a\n+\n+\n+        //f1-2a\n \n-        //f1-1a\n     }\n }\n"},
        {mergeRequestId:8, date: 304, score: 0, commitComments: "check out commit", codeDiff: "@@ -1,5 +1,9 @@\n public class NewClass {\n \n+    //f1-2a\n+    //f1-2a\n     //f1-1a\n     //f1-1a\n+    //f1-2a\n+    //f1-2a\n }\n"},
    ];


    return (
        <div className="CodeDiffTable">
            <Table striped bordered hover>
                <tbody>
                <tr>
                    <td>MR ID</td>
                    <td>Date</td>
                    <td>Score</td>
                    <td>Commit Comments</td>
                    <td>Code Diff</td>
                </tr>
                {
                    codeDiff.map((item, index)=>
                        <tr key ={index}>
                            <td>{item.mergeRequestId}</td>
                            <td>{item.date}</td>
                            <td>{item.score}</td>
                            <td>{item.commitComments}</td>
                            <td><Highlight className="highlighted-text">{item.codeDiff}</Highlight></td>
                        </tr>
                    )}
                </tbody>
            </Table>

        </div>
    );
}

export default CodeDiffTable;
