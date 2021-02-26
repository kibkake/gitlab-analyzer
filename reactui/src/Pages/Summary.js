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
}

export default Summary;
