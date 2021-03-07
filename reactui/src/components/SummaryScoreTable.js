import {Table} from "react-bootstrap";
import React, {Component} from "react";
import axios from "axios";
/*
function copyText(htmlElement) {
    if(!htmlElement){
        return;
    }

    let elementText = htmlElement.innerText;
    let inputElement = document.createElement('input');
    inputElement.setAttribute('value',elementText);
    document.body.appendChild(inputElement);
    inputElement.select();



        document.execCommand('copy');
}
document.querySelector('#copy-btn').onclick =
    function(){
        copyText(document.querySelector('#copy1'));
    }
*/
const scoreSummary = [
    {commitScore:3, scoreMR: 204, wordCountComment: 100},
    {commitScore:2, scoreMR: 12, wordCountComment: 200},
];

class SummaryScoreTable extends Component{

     constructor(props) {
         super(props);
         this.state = {
             scoreSummary:[],
             parentdata: this.props.devName
         }
     }

    componentDidMount() {
        const {parentdata} = this.state;
        this.getDataFromBackend(parentdata)
    }

    getDataFromBackend (username) {

        const pathArray = window.location.pathname.split('/');
        const id = pathArray[2];

        //request ref: http://localhost:8090/api/v1/projects/6/allTotalScores/user2/2021-01-01/2021-02-23
        axios.get("/api/v1/projects/" + id + "/allTotalScores/"+ username +"/2021-01-01/2021-02-23")
            .then(response => {
                const scores = response.data
                this.setState({scoreSummary: scores})
                console.log(this.state.scoreSummary);
            }).catch((error) => {
            console.error(error);
        });
    }


    componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName){
            this.setState({parentdata: this.props.devName});
            this.getDataFromBackend(this.props.devName)
        }
    }
    //


    render () {
        const {parentdata} = this.state;
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
