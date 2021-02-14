import "../App.css"
import React,{ Component } from "react";
import ProjectService from "../Service/ProjectService";
import * as ReactBootStrap from "react-bootstrap"
import {Table} from 'react-bootstrap'

function CodeDiffTable() {

    // TODO: Do not delete, will be used once the API is set
    // constructor(props) {
    //     super(props);
    //     this.state = {
    //         project:[]
    //     }
    // }
    //
    // componentDidMount() {
    //     ProjectService.getCodeDiff().then((response) => {
    //         this.setState({codeDiff: response.data})
    //     });
    // }

    // Fake data for testing
    const codeDiff = [
        {date: 204, score: 2, commitComments: "initial commit", codeDiff: "Dsdfsdfsdf"},
        {date: 304, score: 0, commitComments: "check out commit", codeDiff: "sdfsdfads"},
    ]

    return (
        <div className="CodeDiffTable">
            <Table striped bordered hover>
                <tbody>
                <tr>
                    <td>Date</td>
                    <td>Score</td>
                    <td>Commit Comments</td>
                    <td>Code Diff</td>
                </tr>
                {
                    codeDiff.map((item, index)=>
                            <tr key={index}>
                                <td>{item.date}</td>
                                <td>{item.score}</td>
                                <td>{item.commitComments}</td>
                                <td>{item.codeDiff}</td>
                            </tr>
                    )}
                </tbody>
            </Table>

        </div>
    );
}

export default CodeDiffTable;
