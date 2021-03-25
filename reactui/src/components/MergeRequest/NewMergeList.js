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
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import {OverlayTrigger, Popover} from "react-bootstrap";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import ChartCommit from "../Commits/ChartCommit";
import CommitRow from "./CommitList";
import Highlight from "react-highlight";
import HighlightCodeDiffs from "../Commits/HighlightCodeDiffs";
import CommitMRNumChart from "../Summary/CommitMRNumChart";
import styled from "styled-components";
// import { merge } from 'jquery';

export default class NewMergeList  extends PureComponent {
    applyMultipliers() {
        var scale = JSON.parse(sessionStorage.getItem('languageScale'));
        var newMerges = [...this.state.merges];
        for (const k in newMerges) {
            var newMRScore = 0;
            for (var i in newMerges[k].diffs) {
                var fileExtension = newMerges[k].diffs[i].new_path.split(".").pop();
                const extensionIndex = scale.findIndex(scale => scale.extention === fileExtension);
                if (extensionIndex !== -1) {
                    var newScore = scale[extensionIndex].multiplier * newMerges[k].diffs[i].diffScore;
                    newMerges[k].diffs[i] = {...newMerges[k].diffs[i], diffScore: newScore};
                    newMRScore = newMRScore + newScore;
                } else {
                    newMRScore = newMRScore + newMerges[k].diffs[i].diffScore;
                }
            }
            for (var p in newMerges[k].commits) {
                var newCommitScore = 0;
                for (var i in newMerges[k].commits[p].diffs) {
                    var fileExtension = newMerges[k].commits[p].diffs[i].new_path.split(".").pop();
                    const extensionIndex = scale.findIndex(scale => scale.extention === fileExtension);
                    if (extensionIndex !== -1) {
                        var newScore = scale[extensionIndex].multiplier * newMerges[k].commits[p].diffs[i].diffScore;
                        newMerges[k].commits[p].diffs[i] = {...newMerges[k].commits[p].diffs[i], diffScore: newScore};
                        newCommitScore = newCommitScore + newScore;
                    } else {
                        newCommitScore = newCommitScore + newMerges[k].commits[p].diffs[i].diffScore;
                    }

                }
                newMerges[k].commits[p].commitScore = newCommitScore;
            }
            newMerges[k].mrScore = newMRScore;
        }
        this.setState({
            merges: newMerges,
        })
    }


    constructor(props) {
        super(props);
        this.state = {
            merges: [], //{commits:[{}], date: null, title: null, score:0, diffScore:0, mrUrl: null,
           // sum: 0, diffs:[{path:null, diff:null}]}
            parentData: this.props.devName,
            diff: false,
        }
        this.handler = this.handler.bind(this)
        this.handler2 = this.handler2.bind(this)
    }

    async handler(hash) {
        await this.setState({
            childVal: hash,
            diff: true
        })
    }

    async handler2() {
        await this.setState({
            diff: false
        })
    }

    componentDidMount() {
        const {parentData: parentData} = this.state;
        this.getDataFromBackend(parentData)
    }

    getDataFromBackend(username, startTm, endTm) {
        const pathArray = window.location.pathname.split('/');
        const id = pathArray[2];
        var name = username;
        if (sessionStorage.getItem('DeveloperNames' + id) != null && sessionStorage.getItem('Developers' + id) != null) {
            for (var i = 0; i < JSON.parse(sessionStorage.getItem('Developers' + id)).length; i++) {
                if (JSON.stringify(username) === JSON.stringify(JSON.parse(sessionStorage.getItem('Developers' + id))[i])) {
                    name = JSON.parse(sessionStorage.getItem('DeveloperNames' + id))[i]//use name to retrieve data
                }
            }
        }

        const response = axios.get("/api/v1/projects/" + id + "/mergeRequests/" + username + "/2021-01-01/2021-05-09")
            .then(res => {

                this.setState({merges: res.data, parentData: username});
                console.log(this.state.merges)
                // console.log(this.state.merges.diffs)

                this.applyMultipliers();
            }).catch((error) => {
                console.error(error);
            })
    }


    async componentDidUpdate(prevProps) {
        if (this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime) {
            await this.getDataFromBackend(this.props.devName, this.props.startTime, this.props.endTime)
        }
        console.log("update");
    }

    getFullDiffScore(id) {
        this.state.merges.item(function (item) {

        })
    }


    render() {
        const output = this.state.merges.map(function (item) {
            return {
                id: item.id,
                date: item.merged_at,
                title: item.title,
                score: (item.mrScore + item.sumOfCommits),
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
        console.log(output.diffs)
        return (
            <div className="box-container">
                {/*{output.map((row) => (*/}
                    <TableContainer component={Paper} margin-right="300px">
                        <Table aria-label="collapsible table">
                            <TableHead className="tableCell">
                                <TableRow>
                                    <TableCell align="left" className="tableCell"> Date </TableCell>
                                    <TableCell>Merge Title</TableCell>
                                    <TableCell align="right">Merge Score</TableCell>
                                    <TableCell align="right">Sum of Commit Score</TableCell>
                                    <TableCell align="right">Commits </TableCell>
                                    <TableCell align="right">Full Diff</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {output.map((row) => (
                                    <TableRow>
                                        <TableCell component="th" scope="row">
                                            {moment(row.date).format('ll')}
                                        </TableCell>
                                        <TableCell>#{row.id} <a href={row.mrUrl}> {row.title}</a> </TableCell>
                                        <TableCell align="right">{row.score.toFixed(1)}</TableCell>
                                        <TableCell align="right"> {row.sum}</TableCell>

                                        <TableCell align="right">
                                            {/*<IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>*/}
                                            {/*    {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}*/}
                                            {/*</IconButton>*/}
                                        </TableCell>

                                        <TableCell align="right">
                                            <OverlayTrigger trigger="focus" placement="right-start" justifyContent="flex-start"
                                                            display="flex" flexDirection="row" p={1} m={1}
                                                            overlay={<PopOver id="popover-positioned-top" placement="right-start" {...this.props} Diffs={row.diffs}/>}>
                                            <button type="button" className="btn btn-secondary"
                                                    onClick={this.setState({diff: true})}>
                                                View
                                            </button>
                                            </OverlayTrigger>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    {/*<PopOver Diffs={ }*/}
                {/*))}*/}
            </div>
        );
    }
}

const PopOver = (props) => {
    const {Diffs} = props;

    //id="popover-positioned-top" placement='right' class="justify-content-end"
    return (
        <StyledPopover style={{width:'1000px',height:'600px'}}>
            {Diffs.map((item => {
                return(
                    <ul>
                        <Popover.Title as="h3">{item.path}</Popover.Title>
                        <Popover.Content><Highlight className="highlighted-text"> {HighlightCodeDiffs(item.diff)} </Highlight>
                        </Popover.Content>
                    </ul>
                )
            }))}
        </StyledPopover>
    )
}

const StyledPopover = styled(Popover)`
      min-width: 328px;
     background-color: #2F3337;
     && .arrow::after {
       border-right-color: #2F3337;
     }
`
//
// function DiffWindow(Diffs){
//     return (
//         <div>
//         {/*// <Popover id="popover-basic" placement='right' class="justify-content-end" >*/}
//             {Diffs.map((item, index) => {
//                 return(
//                     <ul key={index}>
//                         <h5 className="filename">{item.path}</h5>
//                         <li><Highlight className="highlighted-text">{HighlightCodeDiffs(item.diff)} </Highlight></li>
//
//                         {/*<Popover.Title as="h3">{item.new_path}</Popover.Title>*/}
//                         {/*<Popover.Content><Highlight className="highlighted-text">  </Highlight>*/}
//                         {/*</Popover.Content>*/}
//                     </ul>
//                 )
//             })}
//         </div>
//     )
// }
//
// //
// // 1. separate page here to show full/code diff
// // 2. the full diff can be mapped here since it is at the outmost data layer
// // 3. find some way to receive state data(row expanded or not?) implement some function here,
// // and call the function in the MergeListTableRows
// // 4. the separate component in diff behaves depending on the result of the received state
// // if true, show diff, else empty
// // 5. is mapping applying to all part at the same time? if they are expanded in the same level
// //
// // const useRowStyles = makeStyles({
// //     root: {
// //         '& > *': {
// //             borderBottom: 'unset',
// //             fontSize: '15pt',
// //             backgroundColor: 'lightgrey',
// //
// //         },
// //     },
// //     tableCell: {
// //         '& > *': {
// //             borderBottom: 'unset',
// //             fontSize: '20pt',
// //             fontWeight: 'bold',
// //         },
// //     }
// // });
//
