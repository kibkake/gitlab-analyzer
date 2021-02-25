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
            <div>
                <Navbar_Developers/>
                <h1 style={{textAlign: 'center'}}>Summary</h1>

                <br>
                </br>

                <DropDownMenu DropDownMenu  listOfDevelopers = {developersArray} />
                <br>
                </br>



            </div>

        )
    }
}

export default Summary;
