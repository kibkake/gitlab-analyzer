import React, {Component} from 'react'
import  "./HBox.css"
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import {Table} from "react-bootstrap";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import CommitInfo from "./CommitInfo";
import moment from "moment";
import CommitService from "./CommitService";

class AllCommits extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            devName: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime
        };
        this.handler = this.handler.bind(this)
    }

    async handler(hash) {
        this.props.handler(hash)
    }

    render(){

        var output = this.state.data.map(function(item) {
            return {
                id: item.id,
                date: item.committed_date
            };
        });
        console.log(output)

        CommitService.initializeStorageForExcludedFiles()
        var excludedScore = CommitService.calculateExcludedScoreWithoutHashProvided(this.props.commits)

        return(
            <TableContainer
                style={{ overflowX: "scroll" , height: "1050px", width: "500px"}}
                component={Paper}
                display="flex"
                flexDirection="row"
                p={1}
                m={1}
                justifyContent="flex-start">
                <div style={{fontWeight: 'bold',
                    fontSize: '20px',
                    color: 'black',
                    backgroundColor: 'lightgreen'}}> All Commits {" "}
                    {moment(this.props.startTime).format('lll').substring(0,12)} -
                    {" " + moment(this.props.endTime).format('lll').substring(0,12)}</div>
                <div style={{fontWeight: 'bold',
                    fontSize: '20px',
                    color: 'blue',
                    backgroundColor: 'lightgreen'}}> Total iteration Score:
                    {" " + this.props.totalScore.toFixed(1)}</div>
                <div style={{fontWeight: 'bold',
                    fontSize: '20px',
                    color: 'blue',
                    backgroundColor: 'lightgreen'}}> Included Score:
                    {" " + (this.props.totalScore - excludedScore).toFixed(1)}</div>
                <div style={{fontWeight: 'bold',
                    fontSize: '20px',
                    color: 'red',
                    backgroundColor: 'lightgreen'}}> Total Excluded Points:
                    {" " + excludedScore.toFixed(1)}</div>
                <Table >
                    <TableHead className="tableCell">
                        <TableRow>
                            <TableCell
                                align="left" className="tableCell"
                                style={{fontWeight: 'bold', fontSize: '20px'}}>
                                Date
                            </TableCell>
                            <TableCell
                                style={{fontWeight: 'bold', fontSize: '20px'} }>
                                Title
                            </TableCell>
                            <TableCell align="right"
                                       style={{fontWeight: 'bold', fontSize: '20px'}}>
                                Score
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.props.commits.map((item) => (
                            <CommitInfo key={item.committed_date}
                                commit={item}
                                handler = {this.handler}
                                resetSingleCommitScore = {this.props.resetSingleCommitScore}
                                commits = {this.props.commits}/>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        );
    }
}

export default AllCommits;