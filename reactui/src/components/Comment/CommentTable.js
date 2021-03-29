import React, { Component } from "react";
import {Table} from 'react-bootstrap'
import axios from "axios";
import moment from "moment";
import "./CommentTable.css";

class CommentTable extends Component{
    constructor(props) {
        super(props);
        this.state = {
            all_comments:[],
            comments_on_devs_code:[],
            comments:[], // Will equal either all_comments or comments_on_devs_code
            issue:true,
            code_rev:true,
            parentdata: this.props.devName,
            devs_code_btn_name:"Dev's Code"
        }
        this.enableAll=this.enableAll.bind(this);
        this.enableIssue=this.enableIssue.bind(this);
        this.enableCodeRev=this.enableCodeRev.bind(this);
        this.filterByDevCode=this.filterByDevCode.bind(this);
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
                this.setState({all_comments: all_comments})
                console.log(this.state.all_comments);
            }).catch((error) => {
            console.error(error);
        });

        await axios.get("/api/v1/projects/" + id + "/allUserNotes/" + username + "/true" + "/2021-01-01/2021-05-09")
            .then(response => {
                const comments_on_devs_code = response.data
                this.setState({comments_on_devs_code: comments_on_devs_code})
                console.log(this.state.comments_on_devs_code);
            }).catch((error) => {
            console.error(error);
        });

        if (this.state.devs_code_btn_name === "Dev's Code") {
            this.setState({comments:this.state.all_comments});
        }
        else {
            this.setState({comments:this.state.comments_on_devs_code});
        }
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
            await this.setState({devs_code_btn_name:"All Code", comments:this.state.comments_on_devs_code});
        }
        else {
            await this.setState({devs_code_btn_name:"Dev's Code", comments:this.state.all_comments});
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
