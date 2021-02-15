import "../App.css"
import React,{ Component } from "react";
import CodeDiffTable from "../components/CodeDiffTable";


function CodeDiff(){

    return(
        <div classname='CodeDiff'>
            <h1 style={{textAlign:'center'}}>Code Diff For Each Commit Merged</h1>
            <h4 style={{textAlign:'center'}}>Highlight + part?</h4>
            <CodeDiffTable/>
        </div>
    )
}

export default CodeDiff;