import "../App.css"
import React, { Component } from "react";
import {Button, Table} from 'react-bootstrap'
import Highlight from 'react-highlight'
import axios from "axios";

class CodeDiffTable extends Component{
// function CodeDiffTable() {

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
    // const codeDiff = [
    //     {mergeRequestId:3, date: 204, score: 2, commitMsg: "initial commit", codeDiff: "@@ -5,7 +5,10 @@ public class Main {\n         // f1-1a\n         //f1-1a\n \n+        //f1-2a\n+\n+\n+        //f1-2a\n \n-        //f1-1a\n     }\n }\n"},
    //     {mergeRequestId:8, date: 304, score: 0, commitMsg: "check out commit", codeDiff: "@@ -1,5 +1,9 @@\n public class NewClass {\n \n+    //f1-2a\n+    //f1-2a\n     //f1-1a\n     //f1-1a\n+    //f1-2a\n+    //f1-2a\n }\n"},
    // ];

    state={
        commits:[]
    }

    componentDidMount(){
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var name = str.split("/")[4];
        axios.get("http://localhost:8080/api/v1/projects/allCommits/"+repNum+"/"+name)
        .then(response=>{
            const commits = response.data
            this.setState({commits})
        })
    }
    render(){
        return (
            <div className="CodeDiffTable">
                <Table striped bordered hover>
                    <tbody>
                    <tr>
                        <td>Commit ID</td>
                        <td>Date</td>
                        <td>Score</td>
                        <td>Commit Message</td>
                        <td>Code Diff</td>
                        <td></td>
                    </tr>
                    {
                        this.state.commits.map((item, index)=>
                            <tr key ={index}>
                                <td>{item.id}</td>
                                <td>{item.date}</td>
                                <td>{item.commitScore}</td>
                                <td>{item.message}</td>
                                <td><Highlight className="highlighted-text">{item.diffs.length} files changed </Highlight></td>
                                <td><Button className="CommitInfoModal">See More</Button></td>
                            </tr>
                        )}
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default CodeDiffTable;
