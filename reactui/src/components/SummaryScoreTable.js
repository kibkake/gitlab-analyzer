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
         var pathArray = window.location.pathname.split('/');
         var id = pathArray[2];
         var developer = pathArray[4];

         //request ref: http://localhost:8090/api/v1/projects/6/allTotalScores/user2/2021-01-01/2021-02-23
         axios.get("/api/v1/projects/" + id + "/allTotalScores/"+ developer +"/2021-01-01/2021-02-23")
             .then(response => {
                const scores = response.data
                 // console.log(scores);
                 this.setState({scoreSummary: scores})
                 console.log(this.state.scoreSummary);
             }).catch((error) => {
             console.error(error);
         });
     }

//Fake data until getting the data from backend
// const scoreSummary = [
//    {commitScore:3, scoreMR: 204, wordCountComment: 100},
//    {commitScore:2, scoreMR: 12, wordCountComment: 200},
// ];


   render () {
       // var output = this.state.scoreSummary.map(function(item) {
       //     return {
       //         text: item.created_at,
       //         value: item.wordCount
       //     };
       // });

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
