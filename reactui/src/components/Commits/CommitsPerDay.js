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
            date : ""
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
        this.props.commits.map(function(item) {
            var time = moment(item.committed_date).format('L')//02/18/2021
            tempDate = moment(date).format('lll')
            const month = time.substring(0,2)
            const day = time.substring(3,5)
            const year = time.substring(6,10)

            const fulltime = year + '-' + month + '-' + day
            if(fulltime === date){
                listOfDates.push(item)
            }
        })
        await this.setState({data:listOfDates, date: tempDate})
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
                                handler = {this.handler}/>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        );
    }
}

export default CommitsPerDay;