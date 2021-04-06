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
                {props.ever.map((item) => (
                    <div style={{fontWeight: 'bold',
                        fontSize: '20px',
                        color: 'black',
                        backgroundColor: 'lightgreen'}}>
                        {moment(item.committed_date).format('lll').substring(12,21)}
                        {"  |   Total Commit Score: "}
                        {item.commitScore}</div>
                ))}
                <button style={{
                    backgroundColor: 'lightblue',
                    color: 'black',
                    borderRadius: '0%'}}
                    type="button"
                    onClick={(e) => {
                        e.preventDefault();
                        props.handler()
                    }}>
                    EXPAND ALL
                </button>

                <Table aria-label="collapsible table" >
                    <TableBody>
                        {props.ever.map((item) =>

                            item.diffs.map((item2, index) => (
                                <Row key={item2.new_path} row={item2}
                                handler = {props.handler}
                                expanded = {props.expanded}/>
                            )))}
                    </TableBody>
                </Table>
        </TableContainer>
    );
}


