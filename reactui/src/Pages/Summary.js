import SummaryScoreTable from "../components/SummaryScoreTable";
import Navbar_Developers from "../components/Navbar_Developers";
import StackedBarChart from "../components/StackedBarChart";
import React from "react";

function Summary(){
    return(
        <div classname='Summary'>
            <Navbar_Developers/>
            <h1 style={{textAlign:'center'}}>Summary</h1>

             //TODO: Additional mapping request required for here, the all score should be sent altogether
             //for commit, MR, word...
             <h4 style={{textAlign:'center'}}>Total scores</h4>
             <SummaryScoreTable/>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Code / MR score per day</h4>
            <StackedBarChart/>
            <br>
            </br>

            <h4 style={{textAlign:'center'}}>Comment score per day</h4>
            //TODO: chart for comment score will be placed here, but for now there's no mapping function for that
        </div>

    )
}

export default Summary;
