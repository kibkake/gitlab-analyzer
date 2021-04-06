import React, {Component} from 'react'
import Button from 'react-bootstrap/Button';
import  "./HBox.css"
import {Table} from "react-bootstrap";
import FormCheck from "react-bootstrap/FormCheck";
import "../Projects/ProjectList.css";
import moment from "moment";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import CommitPerDayInfo from "./CommitPerDayInfo";
import '../MergeRequest/MergeListTable.css'


class CommitsPerDay extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            devName: this.props.devName,
            startTime: this.props.startTime,
            date : "",
            totalScore: 0.0
        };
        this.handler = this.handler.bind(this)
    }

    async handler(hash) {
        this.props.handler(hash)
    }

    async componentDidMount(){
        await this.getData(this.props.devName, this.props.startTime)
    }

    async getData(userName, date) {
        var listOfDates = [];
        var tempDate = "";
        var score = 0.0

        this.props.commits.map(function(item) {
            var time = moment(item.committed_date).format('L')//02/18/2021
            tempDate = moment(date).format('lll')
            const month = time.substring(0,2)
            const day = time.substring(3,5)
            const year = time.substring(6,10)

            const fulltime = year + '-' + month + '-' + day
            if(fulltime === date){
                listOfDates.push(item)
                score += item.commitScore
            }
        })
        await this.setState({data:listOfDates, date: tempDate, totalScore: score})
    }

    async componentDidUpdate(prevProps){
        if(this.props.startTime !== prevProps.startTime){
            await this.getData(this.props.devName, this.props.startTime)
        }
    }

    render(){
        var output = []
        output = this.state.data.map(function(item) {
            return {
                id: item.id,
                date: item.committed_date
            };
        });
        console.log(output)

        if(sessionStorage.getItem("excludedFiles") === null){
            var fileArray = []
            sessionStorage.setItem("excludedFiles",  JSON.stringify(fileArray))
        }

        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var excludedScore = 0.0
        this.state.data.map(function (item) {
            item.diffs.map(function (item2) {
                for (var i = 0; i < tempArray.length; i++) {
                    if (tempArray[i] === item.id + "_" + item2.new_path) {
                        excludedScore += item2.diffScore
                    }
                }
            })
        });


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
                    backgroundColor: 'lightgreen'}}>
                    Commits on {this.state.date.substring(0,12)}
                </div>
                <div style={{fontWeight: 'bold',
                    fontSize: '20px',
                    color: 'black',
                    backgroundColor: 'lightgreen'}}>Total iteration score:  {this.props.totalScore.toFixed(1)}</div>
                <div style={{fontWeight: 'bold',
                    fontSize: '20px',
                    color: 'black',
                    backgroundColor: 'lightgreen'}}>
                    Commit Score on this day: {(this.state.totalScore).toFixed(1)}
                </div>
                <div style={{fontWeight: 'bold',
                    fontSize: '20px',
                    color: 'black',
                    backgroundColor: 'lightgreen'}}>
                    Excluded points on this day: {(excludedScore).toFixed(1)}
                </div>
                <Table
                    aria-label="collapsible table" >
                    <TableHead
                        className="tableCell">
                        <TableRow>
                            <TableCell
                                align="left" className="tableCell"
                                style={{fontWeight: 'bold', fontSize: '20px'}}>
                                    Date
                            </TableCell>
                            <TableCell
                                style={{fontWeight: 'bold', fontSize: '20px'}}>
                                    Title
                            </TableCell>
                            <TableCell
                                align="right"
                                style={{fontWeight: 'bold', fontSize: '20px'}}>
                                    Score
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.data.map((item) => (
                            <CommitPerDayInfo
                                key={item.committed_date}
                                commit={item}
                                commits = {this.state.data}
                                handler = {this.handler}
                                resetSingleCommitScore = {this.props.resetSingleCommitScore}/>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        );
    }
}

export default CommitsPerDay;