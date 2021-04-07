import React, {Component} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import moment from "moment";
import {Table} from "react-bootstrap";
import TableBody from "@material-ui/core/TableBody";
import Row from "./RowsCodeDiff";
import TableCell from "@material-ui/core/TableCell";
import FormCheck from "react-bootstrap/FormCheck";

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

    checkAllFilesAreIgnored(){
        console.log(this.props.ever)

        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var tempHash = this.props.hash
        var allFilesExcluded = true

        this.props.ever.map(function (item) {
            item.diffs.map(function (item2) {
                var isExcluded = false;
                for (var i = 0; i < tempArray.length; i++) {
                    if (tempArray[i] === tempHash + "_" + item2.new_path) {
                        isExcluded = true
                    }
                }
                if(!isExcluded){
                    allFilesExcluded = false
                }
            })
        });

        return allFilesExcluded
    }

    ignoreAllFilesOfCommit(){
        console.log(this.props.ever)

        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var tempHash = this.props.hash

        this.props.ever.map(function (item) {
            item.diffs.map(function (item2) {
                var isExcluded = false;
                for (var i = 0; i < tempArray.length; i++) {
                    if (tempArray[i] === tempHash + "_" + item2.new_path) {
                        isExcluded = true
                    }
                }
                if(!isExcluded){
                    tempArray.push(tempHash + "_" + item2.new_path)
                }
            })
        });

        sessionStorage.setItem("excludedFiles",JSON.stringify( tempArray))
        this.props.addExcludedPoints()
    }

    reAddAllFilesOfCommit(){
        console.log(this.props.ever)

        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var tempHash = this.props.hash

        this.props.ever.map(function (item) {
            item.diffs.map(function (item2) {
                var isExcluded = false;
                var index = -1
                for (var i = 0; i < tempArray.length; i++) {
                    if (tempArray[i] === tempHash + "_" + item2.new_path) {
                        isExcluded = true
                        index = i
                    }
                }
                if(isExcluded && index != -1){
                    tempArray.splice(index, 1);
                }
            })
        });

        sessionStorage.setItem("excludedFiles",JSON.stringify( tempArray))
        this.props.addExcludedPoints()

    }

    render() {

        if(sessionStorage.getItem("excludedFiles") === null){
            var fileArray = []
            sessionStorage.setItem("excludedFiles",  JSON.stringify(fileArray))
        }

        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var tempHash = this.props.hash
        if(this.props.hash != null) {
            var excludedScore = 0.0
            this.props.ever.map(function (item) {
                item.diffs.map(function (item2) {
                    for (var i = 0; i < tempArray.length; i++) {
                        if (tempArray[i] === tempHash + "_" + item2.new_path) {
                            excludedScore += item2.diffScore
                        }
                    }
                })
            });
        }

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
                    backgroundColor: 'lightgreen'
                }}>
                    {moment(item.committed_date).format('lll').substring(12, 21)}
                    {"  |   Total Commit Score: "}
                    {Math.abs((item.commitScore).toFixed(1))}</div>
            ))}
            <div style={{
                fontWeight: 'bold',
                fontSize: '20px',
                color: 'black',
                backgroundColor: 'lightgreen'
            }}>
                {"Excluded Points: "}
                {Math.abs((excludedScore).toFixed(1))}</div>

            <div style={{flexDirection: "row", justifyContent: "flex-start", display: "flex"}}>
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
                            {console.log(this.checkAllFilesAreIgnored())}
                            {this.checkAllFilesAreIgnored() ?  this.reAddAllFilesOfCommit() : this.ignoreAllFilesOfCommit()}
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

