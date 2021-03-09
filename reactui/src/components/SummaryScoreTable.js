import {Table} from "react-bootstrap";
import React, {Component} from "react";
import axios from "axios";

const scoreSummary = [
    {commitScore:3, scoreMR: 204, wordCountComment: 100},
    {commitScore:2, scoreMR: 12, wordCountComment: 200},
];

class SummaryScoreTable extends Component{

     constructor(props) {
         super(props);
         this.state = {
             scoreSummary:[],
             parentdata: this.props.devName,
             startTime: this.props.startTime,
             endTime: this.props.endTime
         }
     }

     async componentDidMount() {
        const {parentdata} = this.state;
        await this.getDataFromBackend(parentdata, this.props.startTime,  this.props.endTime)
     }


     async getDataFromBackend (username, startTm, endTm) {

        const pathArray = window.location.pathname.split('/');
        const id = pathArray[2];
        var name = username;
        if(sessionStorage.getItem('DeveloperNames' + id) != null && sessionStorage.getItem('Developers' + id) != null) {
            for (var i = 0; i < JSON.parse(sessionStorage.getItem('Developers' + id)).length; i++) {
                if (JSON.stringify(username) === JSON.stringify(JSON.parse(sessionStorage.getItem('Developers' + id))[i])) {
                    name = JSON.parse(sessionStorage.getItem('DeveloperNames' + id))[i]//use name to retrieve data
                    console.log("summary score table for: ", name)
                }
            }
        }

        //request ref: http://localhost:8090/api/v1/projects/6/allTotalScores/user2/2021-01-01/2021-02-23
        const response = await axios.get("/api/v1/projects/" + id + "/allTotalScores/"+ username +"/" +
            startTm + "/"
            + endTm)

        const scores = await response.data
        await this.setState({scoreSummary: scores, parentdata: username,startTime: startTm,
            endTime: endTm})
     }



    async componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName || this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime)
        }
    }


    render () {
        const {parentdata} = this.state;
        // copy button
        let toCopy = "Commits: " + this.state.scoreSummary.totalCommitScore + " Merge Requests: " + this.state.scoreSummary.totalMergeRequestScore + " Word count of comments: " + this.state.scoreSummary.totalCommentWordCount;
        return (
            <div className="container">
                <Table striped bordered hover>
                        <tr>
                            <th>Commit</th>
                            <th>Merge Request</th>
                            <th>Word Count of Comments</th>
                            <th>Copy Fields</th>
                        </tr>
                    <tbody>
                        <tr>
                            <td>{this.state.scoreSummary.totalCommitScore}</td>
                            <td>{this.state.scoreSummary.totalMergeRequestScore}</td>
                            <td>{this.state.scoreSummary.totalCommentWordCount}</td>
                            <td><button onClick={()=>navigator.clipboard.writeText(toCopy)}> Copy Fields</button></td>
                        </tr>
                    </tbody>
                </Table>
            </div>
        );
   }
}

export default SummaryScoreTable;
