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
        let url2 = '/getprojectmembers/' + repNum
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
        await  sessionStorage.setItem("Developers", JSON.stringify(listOfDevelopers));
    }

    async componentDidMount() {

        if(sessionStorage.getItem("Developers") == null) {
           await this.getListOfDevs()
        }
        await this.setState({developers: JSON.parse(sessionStorage.getItem("Developers"))})
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
