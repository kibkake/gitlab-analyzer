import BarChart from "../components/Chart";
import SummaryScoreTable from "../components/SummaryScoreTable";
import Navbar_Developers from "../components/Navbar_Developers";
import React from "react";
import StackedBarChart from "../components/StackedBarChart";
import SingleBarChart from "../components/SingleBarChart";

function Summary(){
    return(
        <div classname='Summary'>
            <Navbar_Developers/>
            <h1 style={{textAlign:'center'}}>Summary</h1>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Code / Comment score per day</h4>
//            <StackedBarChart/>
            <SingleBarChart/>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Total score for each part (should change to some other display option)</h4>

            <SummaryScoreTable/>
        </div>

    )
}

export default Summary;
