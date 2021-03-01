import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import CommitMRScoreChart from "./CommitMRScoreChart";
import CommitMRNumChart from "./CommitMRNumChart";
import CustomizedRadios from "./RadioButtonComment";
import CommentChart from "./CommentChart";
import "./RadioSummaryCharts.css"

function Chart(props){
    if (props.value == "score") {
        return (<div>
            <h4 style={{textAlign:'center'}}>Score of Commits/Merge Per Day</h4>
            <CommitMRScoreChart devName = {sessionStorage.getItem("CurrentDeveloper")}/>
        </div>);
    }
    else if (props.value == "number") {
        return (<div>
            <h4 style={{textAlign: 'center'}}>Number of Commits/Merge Per Day</h4>
            <CommitMRNumChart devName={sessionStorage.getItem("CurrentDeveloper")}/>
        </div>);
    }
    if (props.value == "words") {
        return (<div>
            <h4 style={{textAlign: 'center'}}>Number of Words Per Day</h4>
            <CommentChart devName={sessionStorage.getItem("CurrentDeveloper")}/>
        </div>);
    }
}

//[https://stackoverflow.com/questions/27784212/how-to-use-radio-buttons-in-reactjs]
export default class RadioSummaryCharts extends Component {

    constructor(props) {
        super(props);
        this.state = {value: "score"};
        this.handleRadioChange = this.handleRadioChange.bind(this);
    }

    handleRadioChange(event) {
        // set the new value of checked radion button to state using setState function which is async funtion
        this.setState({
            value: event.target.value
        });
    }


    render() {
        return (
            <div>
                <h4 style={{textAlign:'center'}}>View A Chart By</h4>
                    <div check>
                        <input
                            type="radio"
                            value="score" // this is te value which will be picked up after radio button change
                            checked={this.state.value === "score"} // when this is true it show the male radio button in checked
                            onChange={this.handleRadioChange} // whenever it changes from checked to uncheck or via-versa it goes to the handleRadioChange function
                        />
                         Score of commits/merge
                    </div>
                    <div check>
                        <input
                            type="radio"
                            value="number"
                            la
                            aria-label="Number of commits/merge"
                            checked={this.state.value === "number"}
                            onChange={this.handleRadioChange}
                        />
                        <span style={{ marginLeft: "5px" }}> Number of commits/merge </span>
                    </div>

                    <div check>
                        <input
                            type="radio"
                            value="words"
                            checked={this.state.value === "words"}
                            onChange={this.handleRadioChange}
                        />
                        <span style={{ marginLeft: "5px" }}> Words of comments </span>
                    </div>

                <br>
                </br>
                <Chart value = {this.state.value}/>
            </div>
        );
    }
}