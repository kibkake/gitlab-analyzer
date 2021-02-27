import React, {Component} from "react";
import DropDownMenuSummary from "../components/DropDownMenuSummary";

const styles={
    app: {
        marginLeft: '400px',
        marginRight: '400px',
        color: 'black',
        borderColor:'green',
        padding: '2px 2px',
        border: '5px solid transparent',
        borderTopColor:'blue',
        borderRightColor: 'blue',
        borderLeftColor: 'blue',
        borderBottomColor: 'blue',
    }
}


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
                <DropDownMenuSummary style={styles.app} listOfDevelopers={developersArray}/>
                <br>
                </br>
            </div>
        )
    }
}

export default Summary
