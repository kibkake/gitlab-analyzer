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
             merges:[],
             commits:[],
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
            + endTm + "/either")

        const scores = await response.data
        await this.setState({scoreSummary: scores, parentdata: username,startTime: startTm,
            endTime: endTm})

        const MRresponse = await axios.get("/api/v1/projects/" + id + "/mergeRequests/"+ username +"/" +startTm + "/" + endTm)
        .then(MRres => {
            this.setState({merges : MRres.data});
            this.applyMultipliersMR();
        }).catch((error) => {
            console.error(error);})
        
        const commitResponse = await axios.get("/api/v1/projects/" + id + "/Commits/"+ username +"/" +startTm + "/"+ endTm + "/either")
        .then(res=>{
            this.setState({commits : res.data});
            this.applyMultipliersCommits();
        }).catch((error) => {
            console.error(error);})

            console.log("merges");
            console.log(this.state.merges);
            console.log("commits");
            console.log(this.state.commits);
                
    }

    applyMultipliersMR(){
        var scale = JSON.parse(sessionStorage.getItem('languageScale'));
        var newMerges = [...this.state.merges];
        for(const k in newMerges){
            var newMRScore=0;
            for(var i in newMerges[k].diffs){
                var fileExtension = newMerges[k].diffs[i].new_path.split(".").pop();
                const extensionIndex = scale.findIndex(scale => scale.extention === fileExtension);
                if(extensionIndex!==-1){
                    var newScore = scale[extensionIndex].multiplier * newMerges[k].diffs[i].diffScore;
                    newMerges[k].diffs[i] = {...newMerges[k].diffs[i], diffScore:newScore};
                    newMRScore = newMRScore+newScore;
                }else{
                    newMRScore = newMRScore+newMerges[k].diffs[i].diffScore;
                }
            }
            newMerges[k].mrScore=newMRScore;
        }
        const totalMRScore = newMerges.map(item => item.mrScore).reduce((prev, next) => prev + next);
        var newscoreSummary ={...this.state.scoreSummary};
        newscoreSummary={...newscoreSummary,totalMergeRequestScore:totalMRScore};
        this.setState({
            merges:newMerges,
            scoreSummary:newscoreSummary,
        })
    }
    
    applyMultipliersCommits(){
        var scale = JSON.parse(sessionStorage.getItem('languageScale'));
        var newCommits = [...this.state.commits];
        for(const k in newCommits){
            var newCommitsScore=0;
            for(var i in newCommits[k].diffs){
                var fileExtension = newCommits[k].diffs[i].new_path.split(".").pop();
                const extensionIndex = scale.findIndex(scale => scale.extention === fileExtension);
                if(extensionIndex!==-1){
                    var newScore = scale[extensionIndex].multiplier * newCommits[k].diffs[i].diffScore;
                    newCommits[k].diffs[i] = {...newCommits[k].diffs[i], diffScore:newScore};
                    newCommitsScore = newCommitsScore+newScore;
                }else{
                    newCommitsScore = newCommitsScore+newCommits[k].diffs[i].diffScore;
                }
            }
            newCommits[k].commitScore=newCommitsScore;
        }
        const totalComScore = newCommits.map(item => item.commitScore).reduce((prev, next) => prev + next);
        var newscoreSummary ={...this.state.scoreSummary};
        newscoreSummary={...newscoreSummary,totalCommitScore:totalComScore};
        this.setState({
            commits:newCommits,
            scoreSummary:newscoreSummary,
        })
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
