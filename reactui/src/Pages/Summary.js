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

    static getDerivedStateFromProps(nextProps, prevState){

        var str = window.location.pathname;
        var repNum = str.split("/")[2];

        if(sessionStorage.getItem("Developers" + repNum) == null) {
             this.getListOfDevs()
        }
        if(sessionStorage.getItem("DeveloperNames" + repNum) == null) {
             sessionStorage.setItem("DeveloperNames" + repNum, sessionStorage.getItem("Developers" + repNum))
        }
        console.log("Developer",sessionStorage.getItem('Developers' + repNum))
        console.log("DeveloperNames",sessionStorage.getItem('DeveloperNames' + repNum))
        return{
            developers: JSON.parse(sessionStorage.getItem("Developers" + repNum))
        }
    }



    fi() {
        console.log("state.developers", this.state.developers)
    }

    render() {
        var strDevelopers = JSON.stringify(this.state.developers);
        var developersArray = JSON.parse(strDevelopers);

        return (
            <div >
                {this.fi()}
                <DropDownMenuSummary listOfDevelopers={developersArray}/>
                <br>
                </br>
            </div>
        )
    }
}

export default Summary
