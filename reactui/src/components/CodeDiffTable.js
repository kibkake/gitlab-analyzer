import "../App.css"
import React, { Component } from "react";
import {Button, Table} from 'react-bootstrap'
import Highlight from 'react-highlight'
import axios from "axios";
import Popup from "./Popup";
import {useState,useEffect} from "react";

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
        {mergeRequestId:3, date: 204, score: 2, commitMsg: "initial commit", codeDiff: "@@ -5,7 +5,10 @@ public class Main {\n         // f1-1a\n         //f1-1a\n \n+        //f1-2a\n+\n+\n+        //f1-2a\n \n-        //f1-1a\n     }\n }\n"},
        {mergeRequestId:8, date: 304, score: 0, commitMsg: "check out commit", codeDiff:[{diff:"@@ -1,5 +1,9 @@\n public class NewClass {\n \n+    //f1-2a\n+    //f1-2a\n     //f1-1a\n     //f1-1a\n+    //f1-2a\n+    //f1-2a\n }\n"},{diff:"testing\n"}]},
    ];


    const [commits,getCommits]=useState([]);
    const [buttonPopup, setButtonPopup] = useState(false);
    useEffect(()=>{
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var name = str.split("/")[4];
        axios.get("http://localhost:8090/api/v1/projects/"+repNum+"/Commits/"+name+"/2021-01-01/2021-05-09")
        .then(response=>{
            getCommits(response.data)
        });
    });
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
                        commits.map((item, index)=>
                            <tr key ={index}>
                                <td>{item.id}</td>
                                <td>{item.date}</td>
                                <td>{item.commitScore}</td>
                                <td>{item.message}</td>
                                <td>{item.diffs.length} files changed</td>
                                <td>                      
                                    <button onClick={()=> setButtonPopup(true)}> Difference in code</button>
                                    <Popup trigger={buttonPopup} setTrigger = {setButtonPopup} >
                                        <Highlight className="highlighted-text">{item.diffs}</Highlight>
                                    </Popup>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </Table>
            </div>
        );
    }
    export default CodeDiffTable;


    // return (
    //     <div className="CodeDiffTable">
    //         <Table striped bordered hover>
    //             <tbody>
    //             <tr>
    //                 <td>MR ID</td>
    //                 <td>Date</td>
    //                 <td>Score</td>
    //                 <td>Commit Message</td>
    //                 <td>Code Diff</td>
    //             </tr>
    //             {
    //                 codeDiff.map((item, index)=>
    //                     <tr key ={index}>
    //                         <td>{item.mergeRequestId}</td>
    //                         <td>{item.date}</td>
    //                         <td>{item.score}</td>
    //                         <td>{item.commitMsg}</td>
    //                         <button onClick={()=> setButtonPopup(true)}> Difference in code</button>
    //                         <Popup trigger={buttonPopup} setTrigger = {setButtonPopup} >
    //                             <Highlight className="highlighted-text">{item.codeDiff}</Highlight>
    //                         </Popup>
    //                     </tr>
    //                 )}
    //             </tbody>
    //         </Table>
    //     </div>
    // );


