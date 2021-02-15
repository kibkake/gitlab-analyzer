import "../App.css"
import React,{ Component } from "react";
import ChartCommit from "../components/ChartCommit";
import FullDiffTable from "../components/FullDiffTable";


function Commits(){

    return(

        <div classname='Commits'>
            <h4 style={{textAlign:'center'}}>
                # of Commits and Merge Requests per day
            </h4>

            <ChartCommit/>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Code Diff For Each Merge</h4>
            <h5 style={{textAlign:'center'}}>(Highlight lines after '+')</h5>
            <br></br>
            <FullDiffTable/>
        </div>


    )

}

export default Commits;