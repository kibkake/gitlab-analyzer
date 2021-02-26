import CommitChart from "../components/CommitChart";
import Navbar_Developers from "../components/Navbar_Developers";
import React, {Component} from "react";
import DropDownMenuCommit from "../components/DropDownMenuCommits";

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
        let url2 = '/getprojectmembers/' + repNum
        await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then((result)=> {
            result.json().then((resp) => {
                this.setState({developers:resp})
            })
        })
    }


    render() {

        var strDevelopers = JSON.stringify(this.state.developers);
        var developersArray = JSON.parse(strDevelopers)
        return (

            <header classname='Rest'>
                <DropDownMenuCommit listOfDevelopers = {developersArray}/>
            </header>

        )
    }
}

export default Chart;