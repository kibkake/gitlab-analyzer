import SummaryScoreTable from "../components/SummaryScoreTable";
import Navbar_Developers from "../components/Navbar_Developers";
import StackedBarChart from "../components/StackedBarChart";
import React from "react";
import CommentChart from "../components/CommentChart";

function Summary(){
    return(
        <div>
            <Navbar_Developers/>
            <h1 style={{textAlign:'center'}}>Summary</h1>

            the current http request returns error for total commit score
            because the function returns single integer?
            TODO: Additional mapping request required for here, the all score should be sent altogether for commit, MR, word...

             <h4 style={{textAlign:'center'}}>Total scores</h4>
             <SummaryScoreTable/>
            <br>
            </br>

            <h4 style={{textAlign:'center'}}>Code / MR score per day</h4>
            <StackedBarChart/>
            <br>
            </br>

            <h4 style={{textAlign:'center'}}>Comment score per day</h4>
            <CommentChart/>
        </div>

    )
}

export default Summary;
