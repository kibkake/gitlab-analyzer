import CommitChart from "../components/CommitChart";
import Navbar_Developers from "../components/Navbar_Developers";
import React, {Component} from "react";
import DropDownMenuCommit from "../components/DropDownMenuCommits";
import DropDownMenuComments from "../components/DropDownMenuComments";

const styles={
    app: {
        marginLeft: '400px',
        marginRight: '400px',
    }
}

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
                <DropDownMenuCommit style={styles.app} listOfDevelopers = {developersArray}/>
            </header>

        )
    }
}

export default Chart;