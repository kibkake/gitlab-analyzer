import "../App.css"
import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import axios from "axios";
import * as comments from "react-bootstrap/ElementChildren";
import ProjectService from "../Service/ProjectService";

class CommentTable extends Component{

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

        //request ref: http://localhost:8090/api/v1/projects/6/topTenUserNotes/user2/2021-01-01/2021-02-15
        axios.get("http://localhost:8090/api/v1/projects/" + id + "/topTenUserNotes/"+ developer +"/2021-01-01/2021-02-15")
            .then(response => {
                const comments = response.data
                this.setState({comments})
            });
        // ProjectService.getCommentInfo(id, developer, '2021-01-01','2021-02-15').then((response) => {
        //     this.setState({comments: response.data})
        // });
    }


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
