import SummaryScoreTable from "../components/SummaryScoreTable";
import Navbar_Developers from "../components/Navbar_Developers";
import StackedBarChart from "../components/StackedBarChart";
import React, {Component} from "react";
import CommentChart from "../components/CommentChart";
import DropDownMenu from "../components/DropDownMenu";

class Summary extends Component(){


    componentDidMount() {

    }

    render() {

        return (
            <div>
                <Navbar_Developers/>
                <h1 style={{textAlign: 'center'}}>Summary</h1>

                <br>
                </br>

                <DropDownMenu/>
                <br>
                </br>


                <br>
                </br>
                <h4 style={{textAlign: 'center'}}>Total Scores</h4>
                <SummaryScoreTable/>
                <br>
                </br>

                <h4 style={{textAlign: 'center'}}>Score of Commits/Merge Requests</h4>
                <StackedBarChart/>
                <br>
                </br>
                <br>
                </br>
                <h4 style={{textAlign: 'center'}}>Comment Score</h4>
                <CommentChart/>
            </div>

        )
    }
}

export default Summary;
