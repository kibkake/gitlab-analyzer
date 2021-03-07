import React, {Component} from "react";
import DropDownMenuSummary from "../components/DropDownMenuSummary";


class Summary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            developers: []
        };
    }

    async componentDidMount() {

        this.setState({developers: JSON.parse(sessionStorage.getItem("Developers"))})
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
