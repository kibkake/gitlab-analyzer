import "../App.css"
import React,{ Component } from "react";
import DropDownMenuMerges from "../components/DropDownMenuMerges";


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
            <div>
                <DropDownMenuMerges listOfDevelopers = {developersArray}/>

            </div>
        )
    }
}

export default MergeRequest;
