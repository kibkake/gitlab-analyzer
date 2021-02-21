import SummaryScoreTable from "../components/SummaryScoreTable";
import Navbar_Developers from "../components/Navbar_Developers";
import BarChart from "../components/Chart";
import StackedBarChart from "../components/StackedBarChart";
import React from "react";
import CommentChart from "../components/CommentChart";

function Summary(){
    return(
        <div>
            <Navbar_Developers/>
            <h1 style={{textAlign:'center'}}>Summary</h1>

            <br>
            </br>
             <h4 style={{textAlign:'center'}}>Total scores</h4>
             <SummaryScoreTable/>
            <br>
            </br>

            <h4 style={{textAlign:'center'}}>Score of Commits/Merge Requests</h4>
            <StackedBarChart/>
            <br>
            </br>

            <h4 style={{textAlign:'center'}}>Comment score per day</h4>
            <CommentChart/>
        </div>

    )
}

export default Summary;
