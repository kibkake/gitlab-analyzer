/*
References:

- https://www.youtube.com/watch?v=MWD-iKzR2c8
For how to sort an array of objects in Javascript.

- https://stackoverflow.com/questions/14781153/how-to-compare-two-string-dates-in-javascript
fuyi's answer showed that two strings representing dates (in the necessary format)
can be directly compared to find which date is earlier/later.
 */

import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import axios from "axios";
import moment from "moment";
import "./CommentTable.css";
import {sort} from "d3-array";

class CommentTable extends Component{
    constructor(props) {
        super(props);
        this.state = {
            all_comments:[],
            backup_all_comments:[], // Version that remains unsorted.
                                    // Will keep the order given in getDataFromBackend().
            comments_on_devs_code:[],
            backup_comments_on_devs_code:[], // Version that remains unsorted.
            comments:[], // Will equal one of the above arrays, and is used for the table.
            issue:true,
            code_rev:true,
            parentdata: this.props.devName,
            devs_code_btn_name:"Dev's Code"
        }
        this.enableAll=this.enableAll.bind(this);
        this.enableIssue=this.enableIssue.bind(this);
        this.enableCodeRev=this.enableCodeRev.bind(this);
        this.filterByDevCode=this.filterByDevCode.bind(this);
        this.sortByWordCount=this.sortByWordCount.bind(this);
        this.sortByDate=this.sortByDate.bind(this);
        this.unsortArrays=this.unsortArrays.bind(this);
    }

    componentDidMount() {
        const {parentdata} = this.state;
        this.getDataFromBackend(parentdata)
    }

    async getDataFromBackend (username) {
        const pathArray = window.location.pathname.split('/');
        const id = pathArray[2];
        const developer = pathArray[4];
        let shouldFilter = "false";

        await axios.get("/api/v1/projects/" + id + "/allUserNotes/" + username + "/false" + "/2021-01-01/2021-05-09")
            .then(response => {
                const all_comments = response.data
                const backup_all_comments = all_comments.slice();
                this.setState({all_comments: all_comments});
                this.setState({backup_all_comments: backup_all_comments});
                console.log(this.state.all_comments);
                console.log(this.state.backup_all_comments);
            }).catch((error) => {
            console.error(error);
        });

        await axios.get("/api/v1/projects/" + id + "/allUserNotes/" + username + "/true" + "/2021-01-01/2021-05-09")
            .then(response => {
                const comments_on_devs_code = response.data
                const backup_comments_on_devs_code = comments_on_devs_code.slice();
                this.setState({comments_on_devs_code: comments_on_devs_code});
                this.setState({backup_comments_on_devs_code: backup_comments_on_devs_code});
                console.log(this.state.comments_on_devs_code);
                console.log(this.state.backup_comments_on_devs_code);
            }).catch((error) => {
            console.error(error);
        });

        if (this.state.devs_code_btn_name === "Dev's Code") {
            await this.setState({comments:this.state.all_comments.slice()});
        }
        else {
            await this.setState({comments:this.state.comments_on_devs_code.slice()});
        }
        console.log(this.state.comments);
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

    async filterByDevCode(e) {
        e.preventDefault();
        if (this.state.devs_code_btn_name === "Dev's Code") {
            await this.setState({devs_code_btn_name:"All Code", comments:this.state.comments_on_devs_code.slice()});
        }
        else {
            await this.setState({devs_code_btn_name:"Dev's Code", comments:this.state.all_comments.slice()});
        }
    }

    async unsortArrays(e) {
        e.preventDefault();
        console.log(this.state.backup_comments_on_devs_code);
        console.log(this.state.backup_all_comments);
        await this.setState({all_comments:this.state.backup_all_comments.slice(),
            comments_on_devs_code:this.state.backup_comments_on_devs_code.slice()});
        if (this.state.devs_code_btn_name === "Dev's Code") {
            // This means that currently, the comments table is showing comments
            // for all code. So, set it equal to the backup of all_comments:
            await this.setState({comments:this.state.backup_all_comments.slice()});
        }
        else {
            await this.setState({comments:this.state.backup_comments_on_devs_code.slice()});
        }
    }

    async sortByWordCount(e) {
        e.preventDefault();
        let comments_sorted = this.state.comments;
        comments_sorted.sort((a,b) => a.wordCount - b.wordCount);

        let all_comments_sorted = this.state.all_comments;
        all_comments_sorted.sort((a,b) => a.wordCount - b.wordCount);

        let comments_on_devs_code_sorted = this.state.comments_on_devs_code;
        comments_on_devs_code_sorted.sort((a,b) => a.wordCount - b.wordCount);

        await this.setState({comments:comments_sorted, all_comments:all_comments_sorted,
            comments_on_devs_code:comments_on_devs_code_sorted});
    }

    async sortByDate(e) {
        e.preventDefault();
        let comments_sorted = this.state.comments;
        comments_sorted.sort((a,b) => a.created_at > b.created_at ? 1 : -1);

        let all_comments_sorted = this.state.all_comments;
        all_comments_sorted.sort((a,b) => a.created_at > b.created_at ? 1 : -1);

        let comments_on_devs_code_sorted = this.state.comments_on_devs_code;
        comments_on_devs_code_sorted.sort((a,b) => a.created_at > b.created_at ? 1 : -1);

        await this.setState({comments:comments_sorted, all_comments:all_comments_sorted,
            comments_on_devs_code:comments_on_devs_code_sorted});
    }

    render() {
        var Data = this.state.comments.map(function (item) {
            let currentYear = new Date().getFullYear();
            let dateString= moment(item.created_at).format('lll').replace(currentYear,"")

            return {
                date: dateString,
                wordCount: item.wordCount,
                comments: item.body,
                onIssue: item.issueNote
            };
        })

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
                    <button className="filter" onClick={this.filterByDevCode}> {this.state.devs_code_btn_name} </button>
                    <button className="filter" onClick={this.sortByWordCount}> Sort By Word Count </button>
                    <button className="filter" onClick={this.sortByDate}> Sort By Date </button>
                    <button className="filter" onClick={this.unsortArrays}> Unsort Table </button>
                </div>
                <br/>
                <Table striped bordered hover className="comments-table">
                    <thead>
                        <tr>
                            <th className="comments-table-date">Date</th>
                            <th className="comments-table-wc">Word Count</th>
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
                    </thead>
                    <tbody>
                        {this.state.issue===true &&
                            comments.map(comments => {  //(item, index) =>
                                    return comments.onIssue ?
                                        <tr>
                                            <td>{comments.date}</td>
                                            <td className="comments-table-wc">{comments.wordCount}</td>
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
                                            <td>{comments.date}</td>
                                            <td className="comments-table-wc">{comments.wordCount}</td>
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
