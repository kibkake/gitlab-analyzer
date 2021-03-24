import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import axios from "axios";
import moment from "moment";
import "./CommentTable.css";

class CommentTable extends Component{
    constructor(props) {
        super(props);
        this.state = {
            comments:[],
            issue:true,
            code_rev:true,
            parentdata: this.props.devName
        }
        this.enableAll=this.enableAll.bind(this);
        this.enableIssue=this.enableIssue.bind(this);
        this.enableCodeRev=this.enableCodeRev.bind(this);
    }

    componentDidMount() {
        const {parentdata} = this.state;
        this.getDataFromBackend(parentdata)
    }

    async getDataFromBackend (username) {
        const pathArray = window.location.pathname.split('/');
        const id = pathArray[2];
        const developer = pathArray[4];

        //request ref: http://localhost:8090/api/v1/projects/6/topTenUserNotes/user2/2021-01-01/2021-05-09
        await axios.get("/api/v1/projects/" + id + "/allUserNotes/" + username + "/2021-01-01/2021-05-09")
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

    async enableAll(e){
        e.preventDefault();
        await this.setState({issue:true,code_rev:true});
    }

    async enableIssue(e){
        e.preventDefault();
        await this.setState({issue:true,code_rev:false});
    }

    async enableCodeRev(e){
        e.preventDefault();
        await this.setState({issue:false,code_rev:true});
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
            ({date, wordCount, comments, issueOrReview: issueBoolean[onIssue],onIssue}));
        console.log(comments);
        console.log(this.state.issue);

        return (
            <>
                <h4 className="comments-filter">   Filters</h4>
                <div className="comments-filter">
                    <button className="filter" onClick={this.enableAll}> All </button>
                    <button className="filter" onClick={this.enableIssue}> Issue </button>
                    <button className="filter" onClick={this.enableCodeRev}> Code Review </button>
                </div>
                <br/>
                <Table striped bordered hover className="comments-table">
                        <tr>
                            <th>Date</th>
                            <th>Word Count</th>
                            <th>Comments</th>
                            {this.state.issue === true && this.state.code_rev === true &&
                                <th>On Issue/Code Review</th>
                            }
                            {this.state.issue === true && this.state.code_rev === false &&
                                <th>On Issue</th>
                            }
                            {this.state.issue === false && this.state.code_rev === true &&
                                <th>On Code Review</th>
                            }
                        </tr>
                    <tbody>
                        {this.state.issue===true &&
                            comments.map(comments => {  //(item, index) =>
                                    return comments.onIssue ?
                                        <tr>
                                            <td>{moment(comments.date).format('LLL')}</td>
                                            <td>&emsp;{comments.wordCount}</td>
                                            <td>{comments.comments}</td>
                                            <td>{comments.issueOrReview}</td>
                                        </tr>
                                        : <div/>
                                }
                            )
                        }
                        {this.state.code_rev===true &&
                            comments.map(comments => {  //(item, index) =>
                                    return comments.onIssue?<div/>:
                                        <tr>
                                            <td>{moment(comments.date).format('LLL')}</td>
                                            <td>&emsp;{comments.wordCount}</td>
                                            <td>{comments.comments}</td>
                                            <td>{comments.issueOrReview}</td>
                                        </tr>
                                }
                            )
                        }
                    </tbody>
                </Table>
                <div className="vspace"/>
            </>
        );
    }
}

export default CommentTable;
