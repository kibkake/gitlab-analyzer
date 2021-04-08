import React, {PureComponent} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import axios from "axios";
import Row from "./MergeListTableRows";
import PopOver from "./MergeListTableRows";

import './MergeListTable.css'
import moment from "moment";
import CommitList from "./CommitList";
import Highlight from "react-highlight";
import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";
import SingleCommitDiff from "../Commits/CommitDiff";
import async from "asynckit";
// import { merge } from 'jquery';

export default class MergeListTable  extends PureComponent {

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

    constructor(props) {
        super(props);
        this.state = {
            merges:[],
            parentData: this.props.devName,
            diffShow: false,
        }
        this.handler = this.handler.bind(this)
        this.handler2 = this.handler2.bind(this)
    }

    componentDidMount(){
        const {parentData: parentData} = this.state;
        this.getDataFromBackend(parentData)
    }

    getDataFromBackend (username, startTm, endTm) {
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
            }).catch((error) => {
                console.error(error);})
    }

    async componentDidUpdate(prevProps){
        if (this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
        }
        console.log("update");
    }

    getFullDiffScore (id) {
        this.state.merges.item(function(item) {

        })
    }


    async handler() {
        await this.setState({
            diffShow: true
        })
    }

    async handler2() {
        await this.setState({
            diffShow: false
        })
    }


    render () {
        const output = this.state.merges.map(function(item) {
            let currentYear = new Date().getFullYear();
            let dateString= moment(item.merged_at).format('lll').replace(currentYear,"")
            return {
                id: item.id,
                date: dateString,
                title: item.title,
                score: item.mrScore,
                diffScore: 0,
                mrUrl: item.web_url,
                sum: item.sumOfCommits,
                diffs: item.diffs.map(function (diffs) {
                    return {
                        path: diffs.new_path,
                        diff: diffs.diff,
                    };
                }),
                commits: item.commits.map(function (commit) {
                    return {
                        commitDate: commit.date,
                        message: commit.message,
                        score: commit.commitScore,
                        author: commit.committer_name,
                        commitDiffs: commit.diffs.map(function (diffs) {
                            return {
                                path: diffs.new_path,
                                diff: diffs.diff
                            };
                        })
                    };
                })
            };
        });
        // this.setState({merges: output})
        console.log(output);

        return (
            <div className="box-container" aria-setsize={500} onCompositionStart={200}>
                <div class ="box">
            <TableContainer component={Paper} display="flex" flexDirection="row" p={1} m={1} justifyContent="flex-start">
                <Table aria-label="collapsible table" >
                    <TableHead className="tableCell">
                        <TableRow>
                            <TableCell align="left" className="tableCell"> Date </TableCell>
                            <TableCell>Merge Title</TableCell>
                            <TableCell align="right">Merge Score</TableCell>
                            <TableCell align="right">&Sigma; Commit Score</TableCell>
                            <TableCell align="right">Commits & Diffs</TableCell>
                            {/*<TableCell align="right">Full Diff</TableCell>*/}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {output.map((merge) => (
                            <Row key={merge.date} row={merge}/>
                        ))}
                    </TableBody>

                </Table>
            </TableContainer>
            {/*{(this.state.diffShow == false) ? <DiffWindow*/}
            {/*    Diffs={output.diffs}*/}
            {/*    handler2 = {this.handler}/> : <div> </div>}*/}
                </div>
            </div>
        );
    }
}


function DiffWindow(props) {
    const {Diffs} = props;

    return (
        <div>
            {/*// <Popover id="popover-basic" placement='right' class="justify-content-end" >*/}
            {Diffs.map((item, index) => {
                return(
                    <ul key={index}>
                        <h5 className="filename">{item.path}</h5>
                        <li><Highlight className="highlighted-text">{HighlightCodeDiffs(item.diff)} </Highlight></li>
                        {/*<Popover.Title as="h3">{item.new_path}</Popover.Title>*/}
                        {/*<Popover.Content><Highlight className="highlighted-text">  </Highlight>*/}
                        {/*</Popover.Content>*/}
                    </ul>
                )
            })}
        </div>
    )
}


const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
            fontSize: '15pt',
            backgroundColor: 'lightgrey',

        },
    },
    tableCell: {
        '& > *': {
            borderBottom: 'unset',
            fontSize: '20pt',
            fontWeight: 'bold',
        },
    }
});
