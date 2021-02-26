import SummaryScoreTable from "../components/SummaryScoreTable";
import Navbar_Developers from "../components/Navbar_Developers";
import StackedBarChart from "../components/StackedBarChart";
import React, {Component} from "react";
import CommentChart from "../components/CommentChart";
import DropDownMenu from "../components/DropDownMenu";

class Summary extends Component{
    constructor(props){
        super(props);
        this.state={
            developers: []
        };
    }

    async componentDidMount() {

        this.setState({developers:JSON.parse(sessionStorage.getItem("Developers"))})
    }

    render() {

        var strDevelopers = JSON.stringify(this.state.developers);
        var developersArray = JSON.parse(strDevelopers);

        return (
            <div>
                <DropDownMenu listOfDevelopers = {developersArray} />
                <br>
                </br>
            </div>
        )
    }
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

            <StackedBarChart/>
            <br>
            </br>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Comment Score</h4>
            <CommentChart/>
        </div>

    )
}

export default Summary;
