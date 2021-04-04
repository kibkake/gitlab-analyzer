import React, {Component} from "react";
import DropDownMenuCommit from "../components/DropDownMenus/DropDownMenuCommits";
import ProjectService from "../Service/ProjectService";
import Navbar_Developers from "../components/NavBars_Menu/Navbar_Developers";
import Select from "react-select";
import CommitChart from "../components/Commits/CommitChart";

class Chart extends Component{

    constructor(props){
        super(props);
        this.state={
        };
    }

    async componentDidMount() {
    }

    render() {

        return (
            <div>
                <Navbar_Developers devName = {window.location.pathname.split("/")[4]
                }/>
                <br>
                </br>
                <CommitChart devName = {window.location.pathname.split("/")[4]}
                             startTime = {sessionStorage.getItem("startdate")}
                             endTime = {sessionStorage.getItem("enddate")}/>
                <br>
                </br>
            </div>

        )
    }
}

export default Chart;