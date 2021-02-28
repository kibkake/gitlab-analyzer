import SummaryScoreTable from "../components/SummaryScoreTable";
import Navbar_Developers from "../components/Navbar_Developers";
import CommitMRScoreChart from "../components/CommitMRScoreChart";
import React from "react";
import CommentChart from "../components/CommentChart";
import CommitMRChart from "../components/CommitMRChart";

function Summary(){
    return(
        <div>
            <Navbar_Developers/>
            <h1 style={{textAlign:'center'}}>Summary</h1>

            <br>
            </br>
             <h4 style={{textAlign:'center'}}>Total Scores (add copy button)</h4>
             <SummaryScoreTable/>
            <br>
            </br>

            <h4 style={{textAlign:'center'}}>Score of Commits/Merge Requests</h4>
            <h4 style={{textAlign:'center'}}>Add switch to num commits/MR graph</h4>

            <CommitMRScoreChart/>
            <br></br>

            <CommitMRChart/>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Comment Score</h4>
            <CommentChart/>
        </div>

    )
}

export default Summary;
