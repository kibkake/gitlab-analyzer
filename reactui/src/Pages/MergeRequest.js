import "../App.css"
import React,{ Component } from "react";
import DropDownMenuMerge from "../components/DropDownMenuMerge";


class MergeRequest extends Component{

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

        return(
            <div classname='CodeDiff'>
                <DropDownMenuMerge listOfDevelopers = {developersArray}/>

            </div>
        )
    }
}

export default MergeRequest;
