import BarChart from "../components/Chart";
import SummaryScoreTable from "../components/SummaryScoreTable";

function Summary(){
    return(
        <div classname='Summary'>
            <h1 style={{textAlign:'center'}}>Summary</h1>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Code / Comment score per day</h4>
            <BarChart/>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Total score for each part (should change to some other display option)</h4>

            <SummaryScoreTable/>
        </div>

    )
}

export default Summary;
