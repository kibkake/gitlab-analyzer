import "../App.css"
import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import Highlight from 'react-highlight'

// class CodeDiffTable extends Component{
function FullDiffTable() {

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
    const mergeFullDiff = [
        {mergeRequestId:3, date: 204, score: 2, codeDiff: ["@@ -5,7 +5,10 @@ public class Main {\n         // f1-1a\n         //f1-1a\n \n+        //f1-2a\n+\n+\n+        //f1-2a\n \n-        //f1-1a\n     }\n }\n","@@ -1,5 +1,9 @@\n public class NewClass {\n \n+    //f1-2a\n+    //f1-2a\n     //f1-1a\n     //f1-1a\n+    //f1-2a\n+    //f1-2a\n }\n"]},
        {mergeRequestId:8, date: 304, score: 0, codeDiff: ["@@ -5,7 +5,10 @@ public class Main {\n         // f1-1a\n         //f1-1a\n \n+        //f1-2a\n+\n+\n+        //f1-2a\n \n-        //f1-1a\n     }\n }\n","@@ -1,5 +1,9 @@\n public class NewClass {\n \n+    //f1-2a\n+    //f1-2a\n     //f1-1a\n     //f1-1a\n+    //f1-2a\n+    //f1-2a\n }\n"]},
    ];


    return (
        <div className="FullDiffTable">
            <Table striped bordered hover>
                <tbody>
                <tr>
                    <td>Merge ID</td>
                    <td>Date</td>
                    <td>Score</td>
                    <td>Full Diff</td>
                </tr>
                {
                    mergeFullDiff.map((item, index)=>
                        <tr key ={index}>
                            <td>{item.mergeRequestId}</td>
                            <td>{item.date}</td>
                            <td>{item.score}</td>
                            <td><Highlight className="highlighted-text">{item.codeDiff}</Highlight></td>
                        </tr>
                    )}
                </tbody>
            </Table>

        </div>
    );
}

export default FullDiffTable;
