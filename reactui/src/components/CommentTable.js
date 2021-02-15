import "../App.css"
import React,{ Component } from "react";
import {Table} from 'react-bootstrap'

function CommentTable() {

    // TODO: Do not delete, will be used once the API is set
    // constructor(props) {
    //     super(props);
    //     this.state = {
    //         project:[]
    //     }
    // }
    //
    // componentDidMount() {
    //     ProjectService.getCommentInfo().then((response) => {
    //         this.setState({comments: response.data})
    //     });
    // }


    // Fake data for testing
    const comments = [
        {date: 204, wordCount: 20, comments: "very good", author: true},
        {date: 22, wordCount: 50, comments: "very bad", author: false },
        {date: 210, wordCount: 100, comments: "not bad", author: false }
    ]

    const renderComment = (comment) => {
        return (
            <tr>
                <td>{comment.date}</td>
                <td>{comment.wordCount}</td>
                <td>{comment.comments}</td>
                <td>{comment.author}</td>
            </tr>
        )
    }

    return (
        <div className="Comments">
            <Table striped bordered hover>
                <tbody>
                <tr>
                    <td>Date</td>
                    <td>Word Count</td>
                    <td>Comments</td>
                    <td>For myself?</td>
                </tr>
                {
                    comments.map((item, index)=>
                        item.author === false?
                    <tr key={index}>
                        <td>{item.date}</td>
                        <td>{item.wordCount}</td>
                        <td>{item.comments}</td>
                        <td>{item.author}</td>
                    </tr>:null
                    )}
                </tbody>
            </Table>

        </div>
    );
}

export default CommentTable;
