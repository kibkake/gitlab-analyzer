import {Table} from "react-bootstrap";
import React, {Component} from "react";
import axios from "axios";

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

         const response = await axios.get("/api/v1/projects/" + id + "/allTotalScores/"+ username +"/" +
             startTm + "/"
             + endTm + "/either")

         const scores = await response.data
         await this.setState({scoreSummary: scores, parentdata: username,startTime: startTm,
             endTime: endTm})

         const MRresponse = await axios.get("/api/v2/Project/" + id + "/Developers/"+ username +"/mergeRequestsAndCommits")
        .then(MRres => {
            this.setState({merges : MRres.data});
            this.applyMultipliersMR();
        }).catch((error) => {
            console.error(error);})
        const commitResponse = await axios.get("/api/v2/Project/" + id + "/Developers/"+ username +"/commits")
        .then(res=>{
            this.setState({commits : res.data});
            this.applyMultipliersCommits();
        }).catch((error) => {
            console.error(error);})
                
    }

    applyMultipliersMR(){
        var scale = JSON.parse(sessionStorage.getItem('languageScale'));
        var newMerges = [...this.state.merges];
        for(let k in newMerges){
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
        const totalMRScore = newMerges.map(item => item.mrScore).reduce((prev, next) => prev + next, 0);
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
        const totalComScore = newCommits.map(item => item.commitScore).reduce((prev, next) => prev + next, 0);
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
    // was done this way to round them since was taking too much time to get from database to directly round them using toFixed
        let totalCommitSc = this.state.scoreSummary.totalCommitScore;
        let totalMergeRequestSc = this.state.scoreSummary.totalMergeRequestScore;
        let totalCommentWordCt = this.state.scoreSummary.totalCommentWordCount;
        // copy button
        let toCopy = "Commits: " + (Math.round(totalCommitSc * 10) / 10) + " Merge Requests: " + (Math.round(totalMergeRequestSc * 10) / 10) + " Word count of comments: " + Math.round(totalCommentWordCt * 10) / 10;

        // non-https copy method from Stack Overflow
        // https://stackoverflow.com/questions/51805395/navigator-clipboard-is-undefined
        function copyToClipboard(textToCopy) {
            // text area method
            let textArea = document.createElement("textarea");
            textArea.value = textToCopy;
            // make the textarea out of viewport
            textArea.style.position = "fixed";
            textArea.style.left = "-999999px";
            textArea.style.top = "-999999px";
            document.body.appendChild(textArea);
            textArea.focus();
            textArea.select();
            return new Promise((res, rej) => {
                // here the magic happens
                document.execCommand('copy') ? res() : rej();
                textArea.remove();
            });
        }

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
                            <td>{Math.round(totalCommitSc * 10) / 10}</td>
                            <td>{Math.round(totalMergeRequestSc * 10) / 10}</td>
                            <td>{Math.round(totalCommentWordCt * 10) / 10}</td>
                            <td><button onClick={()=>copyToClipboard(toCopy)}> Copy Fields</button></td>
                        </tr>
                    </tbody>
                </Table>
            </div>
        );
   }
}

export default SummaryScoreTable;
