import "../App.css"
import React,{ Component } from "react";
import DropDownMenuMerge from "../components/DropDownMenuMerge";

const styles={
    app: {
        marginLeft: '400px',
        marginRight: '400px',
    }
}


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
                <DropDownMenuMerge style={styles.app} listOfDevelopers = {developersArray}/>

            </div>
        )
    }
}

export default MergeRequest;
