/*
References:

- https://www.youtube.com/watch?v=MWD-iKzR2c8
For how to sort an array of objects in Javascript.

- https://stackoverflow.com/questions/14781153/how-to-compare-two-string-dates-in-javascript
fuyi's answer showed that two strings representing dates (in the necessary format)
can be directly compared to find which date is earlier/later.

- https://stackoverflow.com/questions/31712808/how-to-force-javascript-to-deep-copy-a-string
For deep copying a string. The method given in the accepted answer was previously used
in the sortByCommentMessage function, but it turns out deep copying isn't needed here
(so that commit has been amended).

- https://stackoverflow.com/questions/5684144/how-to-completely-remove-borders-from-html-table
Used Stephen's answer to remove lines in a table. Made .removeBorder in
CommentTable.css and used it in this file.

 */

import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import axios from "axios";
import moment from "moment";
import "./CommentTable.css";
import {sort} from "d3-array";
import {FaSort} from "react-icons/fa";

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
            devs_code_btn_name:"Just the comments on own code in code reviews",
            // For the following variables, 0 means unsorted, 1 means sorted in ascending
            // order, and 2 means sorted in descending order.
            sorted_by_date_state:0,
            sorted_by_comment_msg_state:0
        }
        this.enableAll=this.enableAll.bind(this);
        this.enableIssue=this.enableIssue.bind(this);
        this.enableCodeRev=this.enableCodeRev.bind(this);
        this.filterByDevCode=this.filterByDevCode.bind(this);
        this.sortByWordCount=this.sortByWordCount.bind(this);
        this.sortByDate=this.sortByDate.bind(this);
        this.unsortArrays=this.unsortArrays.bind(this);
        this.sortByCommentMessage=this.sortByCommentMessage.bind(this);
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
                this.setState({all_comments: all_comments, backup_all_comments: backup_all_comments});
                console.log(this.state.all_comments);
                console.log(this.state.backup_all_comments);
            }).catch((error) => {
            console.error(error);
        });

        await axios.get("/api/v1/projects/" + id + "/allUserNotes/" + username + "/true" + "/2021-01-01/2021-05-09")
            .then(response => {
                const comments_on_devs_code = response.data
                const backup_comments_on_devs_code = comments_on_devs_code.slice();
                this.setState({comments_on_devs_code: comments_on_devs_code,
                    backup_comments_on_devs_code: backup_comments_on_devs_code});
                console.log(this.state.comments_on_devs_code);
                console.log(this.state.backup_comments_on_devs_code);
            }).catch((error) => {
            console.error(error);
        });

        if (this.state.devs_code_btn_name === "Just the comments on own code in code reviews") {
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
        if (this.state.devs_code_btn_name === "Just the comments on own code in code reviews") {
            await this.setState({devs_code_btn_name:"                  All code review comments                  ", comments:this.state.comments_on_devs_code.slice()});
        }
        else {
            await this.setState({devs_code_btn_name:"Just the comments on own code in code reviews", comments:this.state.all_comments.slice()});
        }
    }

    async unsortArrays(e) {
        e.preventDefault();
        console.log(this.state.backup_comments_on_devs_code);
        console.log(this.state.backup_all_comments);
        await this.setState({all_comments:this.state.backup_all_comments.slice(),
            comments_on_devs_code:this.state.backup_comments_on_devs_code.slice()});
        if (this.state.devs_code_btn_name === "Just the comments on own code in code reviews") {
            // This means that currently, the comments table is showing comments
            // for all code. So, set it equal to the backup of all_comments:
            await this.setState({comments:this.state.backup_all_comments.slice()});
        }
        else {
            await this.setState({comments:this.state.backup_comments_on_devs_code.slice()});
        }
        await this.setState({sorted_by_comment_msg_state:0, sorted_by_date_state:0});
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
            comments_on_devs_code:comments_on_devs_code_sorted, sorted_by_comment_msg_state:0,
            sorted_by_date_state:0});
    }

    async sortByDate(e) {
        e.preventDefault();
        let sorted_by_date_state = this.state.sorted_by_date_state;
        if (sorted_by_date_state === 2) {
            await this.unsortArrays(e);
        }
        else {
            let order_decider = 1;
            if (sorted_by_date_state === 1) {
                order_decider = -1;
            }
            let comments_sorted = this.state.comments;
            comments_sorted.sort((a,b) => a.created_at > b.created_at ? order_decider : -1 * order_decider);

            let all_comments_sorted = this.state.all_comments;
            all_comments_sorted.sort((a,b) => a.created_at > b.created_at ? order_decider : -1 * order_decider);

            let comments_on_devs_code_sorted = this.state.comments_on_devs_code;
            comments_on_devs_code_sorted.sort((a,b) => a.created_at > b.created_at ? order_decider : -1 * order_decider);

            await this.setState({comments:comments_sorted, all_comments:all_comments_sorted,
                comments_on_devs_code:comments_on_devs_code_sorted,
                sorted_by_comment_msg_state:0, sorted_by_date_state:sorted_by_date_state + 1});
        }
    }

    async sortByCommentMessage(e) {
        e.preventDefault();
        // Uppercase and lowercase letters will be treated the same for ordering.
        let sorted_by_comment_msg_state = this.state.sorted_by_comment_msg_state;

        if (sorted_by_comment_msg_state === 2) {
            // Go to state 0 (i.e., unsort):
            await this.unsortArrays(e);
        }
        else {
            // Will either be sorting in ascending or descending order. Check which it is
            // right now.
            let order_decider = 1;
            if (sorted_by_comment_msg_state === 1) {
                // In ascending order, so want to sort in descending order:
                order_decider = -1;
            }
            let comments_sorted = this.state.comments;
            comments_sorted.sort((a,b) => a.body.toLowerCase() > b.body.toLowerCase() ? order_decider : -1 * order_decider);
            // Note that a.body and b.body will not be modified by toLowerCase(),
            // as the method creates a new object. So making a deep copy is not necessary.

            let all_comments_sorted = this.state.all_comments;
            all_comments_sorted.sort((a,b) => a.body.toLowerCase() > b.body.toLowerCase() ? order_decider : -1 * order_decider);

            let comments_on_devs_code_sorted = this.state.comments_on_devs_code;
            comments_on_devs_code_sorted.sort((a,b) => a.body.toLowerCase() > b.body.toLowerCase() ? order_decider : -1 * order_decider);

            await this.setState({comments:comments_sorted, all_comments:all_comments_sorted,
                comments_on_devs_code:comments_on_devs_code_sorted, sorted_by_comment_msg_state:sorted_by_comment_msg_state + 1,
                sorted_by_date_state:0});
        }
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
                <table>
                    <tr>
                        <td className="removeBorder">
                            <h4 className="sort-header-margin">Sort</h4>
                        </td>
                        <td className="removeBorder">
                            <h4 className="filters-header-margin">Filter</h4>
                        </td>
                    </tr>
                    <tr>
                        <td className="removeBorder">
                            <button className="filter sort-by-date-margin" onClick={this.sortByDate}>            Sort by date            </button>
                        </td>
                        <td className="removeBorder">
                            <button className="filter unsort-margin" onClick={this.unsortArrays}>              Unsort table             </button>
                        </td>
                        <td className="removeBorder">
                            <button className="filter comments-on-code-reviews-margin" onClick={this.enableCodeRev}>          Only comments on code reviews          </button>
                        </td>
                        <td className="removeBorder">
                            <button className="filter comments-on-issues-margin" onClick={this.enableIssue}>                    Only comments on issues                   </button>
                        </td>
                    </tr>
                    <tr>
                        <td className="removeBorder">
                            <button className="filter sort-by-message-margin" onClick={this.sortByCommentMessage}>Sort by comment message</button>
                        </td>
                        <td className="removeBorder">
                            <button className="filter sort-by-word-count-margin" onClick={this.sortByWordCount}>        Sort by word count        </button>
                        </td>
                        <td className="removeBorder">
                            <button className="filter comments-on-both-margin" onClick={this.enableAll}> Comments on both issues and code reviews </button>
                        </td>
                        <td className="removeBorder">
                            <button className="filter comments-on-dev-code-margin" onClick={this.filterByDevCode}> {this.state.devs_code_btn_name} </button>
                        </td>
                    </tr>
                </table>
                <br/>
                <Table striped bordered hover className="comments-table">
                    <thead>
                        <tr>
                            {this.state.sorted_by_date_state === 0 &&
                                <th className="comments-table-date">Date <button className="filter sort-by-date-margin button-colour-when-unsorted" onClick={this.sortByDate}><FaSort/></button></th>
                            }
                            {this.state.sorted_by_date_state === 1 &&
                                <th className="comments-table-date">Date <button className="filter sort-by-date-margin button-colour-when-sorted-ascending" onClick={this.sortByDate}><FaSort/></button></th>
                            }
                            {this.state.sorted_by_date_state === 2 &&
                                <th className="comments-table-date">Date <button className="filter sort-by-date-margin button-colour-when-sorted-descending" onClick={this.sortByDate}><FaSort/></button></th>
                            }

                            <th className="comments-table-wc">Word Count <button className="filter sort-by-word-count-margin" onClick={this.sortByWordCount}><FaSort/></button></th>

                            {this.state.sorted_by_comment_msg_state === 0 &&
                                <th>Comments <button className="filter sort-by-message-margin button-colour-when-unsorted" onClick={this.sortByCommentMessage}><FaSort/></button></th>
                            }
                            {this.state.sorted_by_comment_msg_state === 1 &&
                                <th>Comments <button className="filter sort-by-message-margin button-colour-when-sorted-ascending" onClick={this.sortByCommentMessage}><FaSort/></button></th>
                            }
                            {this.state.sorted_by_comment_msg_state === 2 &&
                                <th>Comments <button className="filter sort-by-message-margin button-colour-when-sorted-descending" onClick={this.sortByCommentMessage}><FaSort/></button></th>
                            }

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
