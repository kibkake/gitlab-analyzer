import "../App.css"
import React,{ Component } from "react";
import DropDownMenuMerge from "../components/DropDownMenuMerge";
import ProjectService from "../Service/ProjectService";


class MergeRequest extends Component{

    constructor(props){
        super(props);
        this.state={
            developers: []
        };
    }

    async componentDidMount() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];

        if(sessionStorage.getItem("Developers" + repNum) == null) {
            await ProjectService.getListOfDevs(repNum)
        }
        await this.setState({developers:JSON.parse(sessionStorage.getItem("Developers" + repNum))})
    }

    render() {

        var strDevelopers = JSON.stringify(this.state.developers);
        var developersArray = JSON.parse(strDevelopers)

        return(
            <div>
                <DropDownMenuMerge listOfDevelopers = {developersArray}/>

            </div>
        )
    }
}

export default MergeRequest;
