import React, {Component} from "react";
import DropDownMenuSummary from "../components/DropDownMenuSummary";


class Summary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            developers: []
        };
    }

    async getListOfDevs(){
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        let url2 = '/api/v1/getusernames/' + repNum
        const result = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        var resp;
        resp = result.json();
        var listOfDevelopers = await resp;

        await sessionStorage.setItem("Developers" + repNum, JSON.stringify(listOfDevelopers));
    }

    async componentDidMount(){

        var str = window.location.pathname;
        var repNum = str.split("/")[2];

        if(sessionStorage.getItem("Developers" + repNum) == null) {
             await this.getListOfDevs()
        }
        if(sessionStorage.getItem("DeveloperNames" + repNum) == null) {
             sessionStorage.setItem("DeveloperNames" + repNum, sessionStorage.getItem("Developers" + repNum))
        }

        await this.setState({developers:JSON.parse(sessionStorage.getItem("Developers" + repNum))})

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
