import "../App.css"
import React,{ Component } from "react";
import CodeDiffTable from "../components/CodeDiffTable";
import Navbar_Developers from "../components/Navbar_Developers";


function MergeRequest(){

    return(
        <div classname='CodeDiff'>
            <Navbar_Developers/>

            <h1 style={{textAlign:'center'}}>List of MR + full diff</h1>
            <h2 style={{textAlign:'center'}}>-list of commits for each MR + code diff</h2>
            <h2 style={{textAlign:'center'}}>-code diff should be shown in the same page</h2>
            <h4 style={{textAlign:'center'}}>-Highlight + part?</h4>
            <CodeDiffTable/>
        </div>
    )
}

export default MergeRequest;