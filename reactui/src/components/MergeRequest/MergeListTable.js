import React, {PureComponent} from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import axios from "axios";
import Row from "./MergeListTableRows";
import './MergeListTable.css'
import moment from "moment";


export default class MergeListTable  extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            merges:[],
            parentData: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime
        }
    }

    applyMultipliers(){
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
            for(var p in newMerges[k].commits){
                var newCommitScore=0;
                for(var i in newMerges[k].commits[p].diffs){
                    var fileExtension = newMerges[k].commits[p].diffs[i].new_path.split(".").pop();
                    const extensionIndex = scale.findIndex(scale => scale.extention === fileExtension);
                    if(extensionIndex!==-1){
                        var newScore = scale[extensionIndex].multiplier * newMerges[k].commits[p].diffs[i].diffScore;
                        newMerges[k].commits[p].diffs[i] = {...newMerges[k].commits[p].diffs[i], diffScore:newScore};
                        newCommitScore = newCommitScore+newScore;
                    }else{
                        newCommitScore = newCommitScore+newMerges[k].commits[p].diffs[i].diffScore;
                    }

                }
                newMerges[k].commits[p].commitScore=newCommitScore;
            }
            newMerges[k].mrScore=newMRScore;
        }
        this.setState({
            merges:newMerges,
        })
    }

    componentDidMount(){
        const {parentData: parentData} = this.state;
        this.getDataFromBackend(parentData, this.props.startTime,  this.props.endTime)
    }

    async getDataFromBackend (username, startTm, endTm) {
        const pathArray = window.location.pathname.split('/');
        const id = pathArray[2];
        var name = username;
        if(sessionStorage.getItem('DeveloperNames' + id) != null && sessionStorage.getItem('Developers' + id) != null) {
            for (var i = 0; i < JSON.parse(sessionStorage.getItem('Developers' + id)).length; i++) {
                if (JSON.stringify(username) === JSON.stringify(JSON.parse(sessionStorage.getItem('Developers' + id))[i])) {
                    name = JSON.parse(sessionStorage.getItem('DeveloperNames' + id))[i]//use name to retrieve data
                }
            }
        }

        const response = axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + "/2021-01-01/2021-05-09")
            .then(res => {
                this.setState({merges : res.data, parentData: username});
                this.applyMultipliers();
                console.log(res.data);

            }).catch((error) => {
                console.error(error);})
    }

    async componentDidUpdate(prevProps){
        if (this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime, this.props.endTime )
        }
    }

    render () {
        const output = this.state.merges.map(function(item) {
            let currentYear = new Date().getFullYear();
            let dateString= moment(item.merged_at).format('lll').replace(currentYear,"")
            return {
                id: item.id,
                date: dateString,
                title: item.title,
                score: (item.mrScore + item.sumOfCommits).toFixed(1),
                mrUrl: item.web_url,
                sum: item.sumOfCommits,
                diffs: item.diffs.map(function (diffs) {
                    return {
                        path: diffs.new_path,
                        diff: diffs.diff,
                        diffScore: diffs.diffScore
                    };
                }),
                commits: item.commits.map(function (commit) {
                    let currentYear = new Date().getFullYear();
                    let dateString= moment(commit.committed_date).format('lll').replace(currentYear,"")
                    return {
                        commitDate: dateString,
                        message: commit.message,
                        score: commit.commitScore.toFixed(1),
                        author: commit.committer_name,
                        diffs: commit.diffs.map(function (diffs) {
                            return {
                                path: diffs.new_path,
                                diff: diffs.diff,
                                diffScore: diffs.diffScore
                            };
                        })
                    };
                })
            };
        });

        return (
            <div className="box-container" style={{"width": "1000px"}} aria-setsize={500} onCompositionStart={200}>
                <div class ="box">
            <TableContainer component={Paper} display="flex" flexDirection="row" p={1} m={1} justifyContent="flex-start">
                <Table aria-label="collapsible table" >
                    <TableHead className="tableCell">
                        <TableRow>
                            <TableCell align="left"> Date </TableCell>
                            <TableCell>Merge Title</TableCell>
                            <TableCell align="right">Merge Score</TableCell>
                            <TableCell align="right">&Sigma; Commit Score</TableCell>
                            <TableCell align="right">Commits & Diffs</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {output.map((merge) => (
                            <Row key={merge.date} row={merge}/>
                        ))}
                    </TableBody>

                </Table>
            </TableContainer>
                </div>
            </div>
        );
    }
}
