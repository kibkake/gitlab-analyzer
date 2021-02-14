import "../App.css"
import React,{ Component } from "react";
import ProjectService from "../Service/ProjectService";
import * as ReactBootStrap from "react-bootstrap"

const CommentTable = () => {

    // TODO: Do not delete, will be used once the API is set
    // constructor(props) {
    //     super(props);
    //     this.state = {
    //         project:[]
    //     }
    // }
    //
    // componentDidMount() {
    //     ProjectService.getAll().then((response) => {
    //         this.setState({project: response.data})
    //     });
    // }


    // Fake data for testing
    const comments = [
        {date: 204, wordCount: 20, comments: "very good", author: true},
        {date: 22, wordCount: 50, comments: "very bad", author: false }
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
            <ReactBootStrap.Table striped bordered hover>
            <thread>
                <tr>
                    <th>Date</th>
                    <th>Word Count</th>
                    <th>Comments</th>
                    <th>For myself?</th>
                </tr>
            </thread>
            <tbody>
                {comments.map(renderComment)}
            </tbody>
            </ReactBootStrap.Table>
        </div>
    )
}

export default CommentTable;
