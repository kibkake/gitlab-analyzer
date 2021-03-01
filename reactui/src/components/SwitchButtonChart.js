import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import CommitMRScoreChart from "./CommitMRScoreChart";
import CommitMRNumChart from "./CommitMRNumChart";
import CustomizedRadios from "./RadioButtonComment";


function Chart(props){
    if (props.value) {
        return (<div>
            <h4 style={{textAlign:'center'}}>Score of Commits/Merge</h4>
            <CommitMRScoreChart devName = {sessionStorage.getItem("CurrentDeveloper")}/>
            </div>);
    }
    return (<div>
        <h4 style={{textAlign:'center'}}>Number of Commits/Merge</h4>
        <CommitMRNumChart devName = {sessionStorage.getItem("CurrentDeveloper")}/>
    </div>);
}

export default class SwitchButtonChart extends Component {
    constructor(props) {
        super(props);
        this.state = {value: true}
    }

    handleClick = () => {
       this.setState({
           value : !this.state.value
       });
    }

    render() {
        return (
            <div>
                <div style={{display: 'flex',  justifyContent:'center', alignItems:'center'}}>
                    <button onClick={this.handleClick}>View Other Chart</button>
                </div>
                <br>
                </br>
                <Chart value = {this.state.value}/>
            </div>
        );
    }
}