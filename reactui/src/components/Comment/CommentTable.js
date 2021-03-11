import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import axios from "axios";


class CommentTable extends Component{
    constructor(props) {
        super(props);
        this.state = {
            comments:[],
            parentdata: this.props.devName
        }
    }

    componentDidMount() {
        const {parentdata} = this.state;
        this.getDataFromBackend(parentdata)
    }

    getDataFromBackend (username) {
        const pathArray = window.location.pathname.split('/');
        const id = pathArray[2];
        const developer = pathArray[4];

        //empty request ref: http://localhost:8090/api/v1/projects/6/topTenUserNotes/user2/2021-01-01/2021-05-09
        axios.get("/api/v1/projects/" + id + "/topTenUserNotes/" + username + "/2021-01-01/2021-05-09")
            .then(response => {
                const comments = response.data
                this.setState({comments: comments})
                console.log(this.state.comments);
            }).catch((error) => {
            console.error(error);
        });
    }

    componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName){
            this.setState({parentdata: this.props.devName});
            this.getDataFromBackend(this.props.devName)
        }
    }

    render() {
        const issueNote = this.state.comments.map(function(item) {
            // if (item.issueNote) {
                return {
                    if (item.issueNote)
                    date: item.createdDate,
                    wordCount: item.wordCount,
                    comments: item.body
                };
            // };
        });
        console.log(issueNote);

        const MRNote = this.state.comments.map(function(item) {
            if (!item.issueNote) {
                return {
                    date: item.createdDate,
                    wordCount: item.wordCount,
                    comments: item.body
                }
            }
        })

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
                        issueNote.map(comments =>//(item, index) =>
                            <tr>
                                <td>{comments.date}</td>
                                <td>{comments.wordCount}</td>
                                <td>{comments.comments}</td>
                            </tr>
                        )}
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default CommentTable;
