import React, {Component} from "react";
import DropDownMenuSummary from "../components/DropDownMenuSummary";
import ProjectService from "../Service/ProjectService"

class Summary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            developers: []
        };
    }

    async componentDidMount(){

        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var listOfDevs;

        if(sessionStorage.getItem("Developers" + repNum) == null) {
            listOfDevs = ProjectService.getListOfDevs(repNum)
        }else{
            listOfDevs = sessionStorage.getItem("Developers" + repNum)
        }

        if(sessionStorage.getItem("DeveloperNames" + repNum) == null) {
             sessionStorage.setItem("DeveloperNames" + repNum, sessionStorage.getItem("Developers" + repNum))
        }

        await this.setState({developers:JSON.parse(listOfDevs)})

        console.log("state.developers", this.state.developers)
        console.log("Developer",sessionStorage.getItem('Developers' + repNum))
        console.log("DeveloperNames",sessionStorage.getItem('DeveloperNames' + repNum))
    }

    render() {
        var strDevelopers = JSON.stringify(this.state.developers);
        var developersArray = JSON.parse(strDevelopers);

        return (
            <div >
                <DropDownMenuSummary listOfDevelopers={developersArray}/>
                <br>
                </br>
            </div>
        )
    }
}

export default Summary
