import React, {Component} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import moment from "moment";
import {Table} from "react-bootstrap";
import TableBody from "@material-ui/core/TableBody";
import Row from "./RowsCodeDiff";
import CommitService from "./CommitService";

export default class ExpandButton extends Component  {

    constructor(props) {
        super(props);
        this.state = {
            render : false
        };
        this.handler2 = this.handler2.bind(this)
    }

    async handler2() {
        this.setState({render: !this.state.render})
    }

    async componentDidUpdate(prevProps){
        if(this.props.hash !== prevProps.hash){
            await this.setState({render: !this.state.render})
        }
    }

    render() {

        if(sessionStorage.getItem("excludedFiles") === null){
            var fileArray = []
            sessionStorage.setItem("excludedFiles",  JSON.stringify(fileArray))
        }

        var excludedScore = CommitService.calculateExcludedScore(this.props.ever, this.props.hash)
        console.log("the excludedScore", excludedScore)

        return(
        <TableContainer style={{overflowX: "scroll", height: "1050px", width: "600px"}}
                        component={Paper}
                        display="flex"
                        flexDirection="row"
                        p={1}
                        m={1}
                        justifyContent="flex-start">
            {this.props.ever.map((item) => (
                <div style={{
                    fontWeight: 'bold',
                    fontSize: '20px',
                    color: 'black',
                    backgroundColor: 'lightgreen',
                    flexDirection: "row",
                    justifyContent: "flex-start",
                    display: "flex"
                }}>
                        <div>
                            {moment(item.committed_date).format('lll').substring(12, 21) + "  |   "}
                        </div>
                        <div style={{
                            color: 'blue',
                        }}>
                            {"Total Commit Score: "}
                            {Math.abs((item.commitScore).toFixed(1))}
                        </div>
                </div>
            ))}
            <div style={{
                fontWeight: 'bold',
                fontSize: '20px',
                color: 'red',
                backgroundColor: 'lightgreen'
            }}>
                {"Excluded Points: "}
                {Math.abs((excludedScore).toFixed(1))}</div>

            <div
                style={{flexDirection: "row", justifyContent: "flex-start", display: "flex"}}>
                <button style={{
                    backgroundColor: 'lightblue',
                    color: 'black',
                    borderRadius: '0%'
                }}
                        type="button"
                        onClick={(e) => {
                            e.preventDefault();
                            this.props.handler()
                        }}>
                    EXPAND ALL
                </button>

                <button style={{
                    backgroundColor: 'red',
                    color: 'black',
                    borderRadius: '0%',
                    marginLeft:"380px"
                }}
                        type="button"
                        onClick={(e) => {
                            e.preventDefault();
                            {console.log(CommitService.checkAllFilesAreIgnored(this.props.ever, this.props.hash))}
                            {CommitService.checkAllFilesAreIgnored(this.props.ever, this.props.hash) ?
                                CommitService.reAddAllFilesOfCommit(this.props.ever, this.props.hash) :
                                CommitService.ignoreAllFilesOfCommit(this.props.ever, this.props.hash)}
                            {this.props.addExcludedPoints()}
                        }}>
                    {"IGNORE ALL"}
                </button>
            </div>

            <Table aria-label="collapsible table">
                <TableBody>
                    {this.props.ever.map((item) =>
                        item.diffs.map((item2, index) => (
                            <Row key={item2.new_path}
                                 row={item2}
                                 handler={this.props.handler}
                                 expanded={this.props.expanded}
                                 addExcludedPoints={this.props.addExcludedPoints}
                                 hash={this.props.hash}/>
                        )))}
                </TableBody>
            </Table>
        </TableContainer>
        )
    }
}

