import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import axios from "axios";
import ProjectService from "../Service/ProjectService";

class CommentTable extends Component{

    constructor(props) {
        super(props);
        this.state = {
            comments:[]
        }
    }

    componentDidMount() {
        const pathArray = window.location.pathname.split('/');
        const id = pathArray[2];
        const developer = pathArray[4];

        //empty request ref: http://localhost:8090/api/v1/projects/6/topTenUserNotes/user2/2021-01-01/2021-02-23
        axios.get("/api/v1/projects/" + id + "/topTenUserNotes/" + developer + "/2021-01-01/2021-02-23")
            .then(response => {
                const comments = response.data
                this.setState({comments: comments})
                console.log(this.state.comments);
            }).catch((error) => {
            console.error(error);
        });
        // ProjectService.getCommentInfo(id, developer, '2021-01-01','2021-02-15').then((response) => {
        //     this.setState({comments: response.data})
        // });
    }


    render() {
        return (
            <div className="container">
                <Table striped bordered hover>
                    <tr>
                        <td>Date/Time</td>
                        <td>Word Count</td>
                        <td>Comments</td>
                    </tr>
                    <tbody>
                    {
                        this.state.comments.map(comments =>//(item, index) =>
                            <tr>
                                <td>{comments.createdDate}</td>
                                <td>{comments.wordCount}</td>
                                <td>{comments.body}</td>
                            </tr>
                        )}
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default CommentTable;
