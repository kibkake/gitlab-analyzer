import "../App.css"
import React,{ Component } from "react";
import CodeDiffTable from "../components/CodeDiffTable";
import ChartCommit from "../components/ChartCommit";
import CodeDiffRadios from "../components/RadioButtonCodeDiff";


function Commits(){

    return(

        <div classname='Commits'>
            <h4 style={{textAlign:'center'}}>
                # of Commits and Merge Requests per day
            </h4>

            <ChartCommit/>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Code Diff For Each Commit/Merge</h4>
            <h5 style={{textAlign:'center'}}>(Highlight lines after '+')</h5>
            <br></br>
            <div style={{display: 'flex',  justifyContent:'center', alignItems:'center'}}>
                <CodeDiffRadios/>
            </div>
            <CodeDiffTable/>
        </div>


    )

}

export default Commits;