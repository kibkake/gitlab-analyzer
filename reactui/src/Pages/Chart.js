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
        this.setState({developers:JSON.parse(sessionStorage.getItem("Developers"))})
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