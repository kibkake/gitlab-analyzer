import "../App.css"
import ChartCommit from "../components/ChartCommit";
import FullDiffTable from "../components/storage_unused/FullDiffTable";
import Navbar_Developers from "../components/NavBars_Menu/Navbar_Developers";


function Commits(){

    return (
        <div classname='Commits'>
            <Navbar_Developers/>
            <h1 style={{textAlign:'center'}}>Code Contribution</h1>
            <br></br>
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