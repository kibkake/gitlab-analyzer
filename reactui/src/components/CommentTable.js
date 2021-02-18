import "../App.css"
import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import axios from "axios";
import * as comments from "react-bootstrap/ElementChildren";

class CommentTable extends Component{

    // TODO: Do not delete, will be used once the API is set
    constructor(props) {
        super(props);
        this.state = {
            comments:[]
        }
    }

    componentDidMount() {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];
        var developer = pathArray[4];
        axios.get("http://localhost:8080/api/v1/projects/" + id + "/topTenUserNotes/"+developer +"/2021-01-01/2021-02-15")
            .then(response => {
                const comments = response.data
                this.setState({comments})
            });
        // ProjectService.getCommentInfo().then((response) => {
        //     this.setState({comments: response.data})
        // });
    }

    // Fake data for testing
    // const comments = [
    //     {date: 204, wordCount: 20, comments: "very good", author: true},
    //     {date: 22, wordCount: 50, comments: "very bad", author: false },
    //     {date: 210, wordCount: 100, comments: "not bad", author: false }
    // ]

    render() {
        return (
            <div className="Comments">
                <Table striped bordered hover>
                    <tbody>
                    <tr>
                        <td>Date/Time</td>
                        <td>Word Count</td>
                        <td>Comments</td>
                        <td>For myself?</td>
                    </tr>
                    {
                        this.state.comments.map(comments =>//(item, index) =>
                                <tr>
                                    <td>{comments.createdDate}</td>
                                    <td>{comments.wordCount}</td>
                                    <td>{comments.body}</td>
                                    <td>{comments.author.name}</td>
                                </tr>
                        )}
                    </tbody>
                </Table>

            </div>
        );
    }
}

export default CommentTable;
