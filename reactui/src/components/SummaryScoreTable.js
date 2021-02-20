// class CodeDiffTable extends Component{
import {Table} from "react-bootstrap";
import React, {Component} from "react";
import axios from "axios";


class SummaryScoreTable extends Component{
     constructor(props) {
         super(props);
         this.state = {
             scoreSummary:[]
         }
     }

     componentDidMount() {
         const path1 = window.location.pathname;
         const id = path1.split("/")[2];
         const developer = path1.split("/")[4];

         //request ref: http://localhost:8090/api/v1/projects/Repo/totalCommitScore/Developers/01-01-2021/02-15-2021
         //the current http request returns error for total commit score
         //TODO: This mapping request can't be used for here, the all score should be sent altogether
         //for commit, MR, word...
         axios.get("/api/v1/projects/" + id + "/totalCommitScore/"+ developer +"/01-01-2021/02-15-2021")
             .then(response => {
                const scoreSummary = response.data
                this.setState({scoreSummary})
             })
     }

//Fake data until getting the data from backend
// const scoreSummary = [
//    {commitScore:3, scoreMR: 204, wordCountComment: 100},
//    {commitScore:2, scoreMR: 12, wordCountComment: 200},
// ];


   render () {
    return (
        <div className="CodeDiffTable">
            <Table striped bordered hover>
                <tbody>
                <tr>
                    <td>Commit</td>
                    <td>Merge Request</td>
                    {/*<td>Word Count for Comments</td>*/}
                </tr>
                {
                    this.state.scoreSummary.map((item, index)=>
                        <tr key ={index}>
                            <td>{item.commitScore}</td>
                            <td>{item.scoreMR}</td>
                            </tr>
                    )}
                </tbody>
            </Table>

        </div>
    );
   }
}

export default SummaryScoreTable;
