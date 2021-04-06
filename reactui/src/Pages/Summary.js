import React, {Component} from "react";
import DropDownMenuSummary from "../components/Summary/DropDownMenuSummary";
import ProjectService from "../Service/ProjectService"

class Summary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            developers: [],
            currDev: []                     ,
                        data: [],

        };
    }

    async componentDidMount(){

        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var devId = str.split("/")[4];

        if(sessionStorage.getItem("Developers" + repNum) == null) {
            console.log("no developer objects in session storage")
            await ProjectService.storeDevelopers(repNum)
        }
        this.setState({ developers: sessionStorage.getItem("Developers" + repNum)})

        if(sessionStorage.getItem("DeveloperNames" + repNum) == null) {
            console.log("no developer usernames in session storage")
           await sessionStorage.setItem("DeveloperNames" + repNum, sessionStorage.getItem("Developers" + repNum))
        }

        await this.setState({developers:JSON.parse(sessionStorage.getItem("Developers" + repNum))})

        console.log("state.developers", this.state.developers)
        console.log("Developer",sessionStorage.getItem('Developers' + repNum))
        console.log("DeveloperNames",sessionStorage.getItem('DeveloperNames' + repNum))
        await this.getCurDevInfo(repNum, devId)
    }

    async getCurDevInfo(repNum, devID) {
        let url2 = '/api/v2/Project/' + repNum + '/Developers/' + devID + '/devInfo'
        await fetch(url2)
            .then(response => {
                return response.json();
            })
            .then(d => {
                this.setState({ currDev: d });
                console.log("curr dev info", this.state.currDev);
            })
        await sessionStorage.setItem("CurrentDeveloper", JSON.stringify(this.state.currDev))
        console.log("curr dev in session storage",  sessionStorage.getItem("CurrentDeveloper"))
    }

    static getDerivedStateFromProps() {
        console.log("Running the Summary component")
        return null
    }

    shouldComponentUpdate() {
        return true
    }

    render() {
        var strDevelopers = JSON.stringify(this.state.developers);
        console.log("str devs from state", strDevelopers)
        var developersArray = JSON.parse(strDevelopers);
        console.log(" array devs from state", developersArray)


        return (
            <div >
                <DropDownMenuSummary listOfDevelopers={developersArray.map(dev => dev)}
                />
                <br>
                </br>
            </div>
        )
    }
}

export default Summary
