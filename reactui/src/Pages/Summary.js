import BarChart from "../components/Chart";
import SummaryScoreTable from "../components/SummaryScoreTable";

function Summary(){
    return(
        <div classname='Summary'>
            <h1 style={{textAlign:'center'}}>Summary</h1>
            <h4 style={{textAlign:'center'}}>code score/graph in the duration, comment wordCount/per day </h4>
            <SummaryScoreTable/>
            <BarChart/>
        </div>

    )


}

export default Summary;
