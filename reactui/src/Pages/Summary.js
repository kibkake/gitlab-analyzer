import SummaryScoreTable from "../components/SummaryScoreTable";
import Navbar_Developers from "../components/Navbar_Developers";
import StackedBarChart from "../components/StackedBarChart";
import React from "react";
import CommentChart from "../components/CommentChart";
import TimeSeriesChart from "../components/TimeSeriesChart";

function Summary(){
    return(
        <div>
            <Navbar_Developers/>
            <h1 style={{textAlign:'center'}}>Summary</h1>

            <br>
            </br>
             <h4 style={{textAlign:'center'}}>Total Scores</h4>
             <SummaryScoreTable/>
            <br>
            </br>

            <h4 style={{textAlign:'center'}}>Score of Commits/Merge Requests</h4>
            <StackedBarChart/>
            <br>
            </br>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Comment Score</h4>
            <CommentChart/>

            {/*<TimeSeriesChart/>*/}
        </div>

    )
}

export default Summary;
