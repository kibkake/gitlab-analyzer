import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import axios from "axios";
import moment from "moment";

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

        //request ref: http://localhost:8090/api/v1/projects/6/topTenUserNotes/user2/2021-01-01/2021-05-09
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
        var Data = this.state.comments.map(function (item) {
            return {
                date: moment(item.created_at).format('ll'),
                wordCount: item.wordCount,
                comments: item.body,
                onIssue: item.issueNote
            };})

        let issueBoolean = {true: "Issue", false: "Code Review"};
        let comments = Data.map(({date, wordCount, comments, onIssue})=>
            ({date, wordCount, comments, issueOrReview: issueBoolean[onIssue]}));
        console.log(comments);

        return (
            <Table striped bordered hover style ={{margin:'auto', width:'90%'}}>
                    <tr>
                        <th>Date</th>
                        <th>Word Count</th>
                        <th>Comments</th>
                        <th>On Issue/Code Review</th>
                    </tr>
                <tbody>
                    {comments.map(comments =>//(item, index) =>
                        <tr>
                            <td>{moment(comments.date).format('LLL')}</td>
                            <td>{comments.wordCount}</td>
                            <td>{comments.comments}</td>
                            <td>{comments.issueOrReview}</td>
                        </tr>
                    )}
                </tbody>
            </Table>

        );
    }
}

export default CommentTable;
