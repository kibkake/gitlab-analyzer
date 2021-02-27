import React, {Component} from "react";
import DropDownMenu from "../components/DropDownMenu";

const styles={
    app: {
        marginLeft: '400px',
        marginRight: '400px',
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
                <DropDownMenu style={styles.app} listOfDevelopers={developersArray}/>
                <br>
                </br>
            </div>
        )
    }
}

export default Summary
