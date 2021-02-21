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
             scoreSummary:[]
         }
     }

     componentDidMount() {
         const pathArray = window.location.pathname.split('/');
         const id = pathArray[2];
         const developer = pathArray[4];

         //request ref: http://localhost:8090/api/v1/projects/6/allTotalScores/user2/2021-01-01/2021-02-23
         axios.get("/api/v1/projects/" + id + "/allTotalScores/"+ developer +"/2021-01-01/2021-02-23")
             .then(response => {
                const scores = response.data
                 this.setState({scoreSummary: scores})
                 console.log(this.state.scoreSummary);
             }).catch((error) => {
             console.error(error);
         });
     }


   render () {
        return (
            <div className="container">
                <Table striped bordered hover>
                        <tr>
                            <th>Commit</th>
                            <th>Merge Request</th>
                            <th>Word Count of Comments</th>
                        </tr>
                    <tbody>
                        <tr>
                            <td>{this.state.scoreSummary.totalCommitScore}</td>
                            <td>{this.state.scoreSummary.totalMergeRequestScore}</td>
                            <td>{this.state.scoreSummary.totalCommentWordCount}</td>
                        </tr>
                    </tbody>
                </Table>
            </div>
        );
   }
}

export default SummaryScoreTable;
