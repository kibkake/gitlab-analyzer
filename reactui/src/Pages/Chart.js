import React, {Component} from "react";
import DropDownMenuCommit from "../components/DropDownMenus/DropDownMenuCommits";
import ProjectService from "../Service/ProjectService";

class Chart extends Component{

    constructor(props){
        super(props);
        this.state={
            developers: []
        };
    }

    async componentDidMount() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];


        if(sessionStorage.getItem("DevelopersNames" + repNum) == null) {
            await ProjectService.getListOfDevs(repNum)
        }
        await this.setState({developers:JSON.parse(sessionStorage.getItem("Developers" + repNum))})
    }


    render() {

        var strDevelopers = JSON.stringify(this.state.developers);
        var developersArray = JSON.parse(strDevelopers)
        return (

            <header classname='Rest'>
                <DropDownMenuCommit listOfDevelopers ={developersArray}/>

            </header>

        )
    }
}

export default Chart;