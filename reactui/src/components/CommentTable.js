import "../App.css"
import React,{ Component } from "react";
import ProjectService from "../Service/ProjectService";


class CommentTable extends React.Component {
    constructor(props) {
        super(props) //since we are extending class Table so we have to use super in order to override Component class constructor
        this.state = { //state is by default an object
            comments: [
                { id:1, date: 204, wordNum: 20, Comments: "very good", forOther: true, forOwner: false },
                { id:2, date: 22, wordNum: 50, Comments: "very bad", forOther: false, forOwner: true },
            ]
        }
    }

    renderTableHeader() {
        let header = Object.keys(this.state.comments[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    renderTableData() {
        return this.state.comments.map((comment, index) => {
            const { id, date, wordNum, Comments, forOther, forOwner } = comment //destructuring
            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{date}</td>
                    <td>{wordNum}</td>
                    <td>{Comments}</td>
                    <td>{forOther}</td>
                    <td>{forOwner}</td>
                </tr>
            )
        })
    }

    // Do not delete, will be used once the API is set
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


    render() {
        return (
            <div>
                <h1 id='title'>Comments on Issue/Code review</h1>
                <table id='comment'>
                    <tbody>
                    {this.renderTableHeader()}
                    {this.renderTableData()}
                    </tbody>
                </table>
            </div>
        )
    }

    //
    // render () {
    //     return (
    //         <div>
    //             <h1 className='text-center'>Comments on Issue/Code Review</h1>
    //             <table className = "table table-striped">
    //                 <tbody>
    //                 <tr>
    //                     <td>Date</td>
    //                     <td>Word Count</td>
    //                     <td>Comments</td>
    //                     <td>For Owners</td>
    //                     <td>For Others</td>
    //                 </tr>
    //                 {this.renderTableData()}
    //                 </tbody>
    //             </table>
    //         </div>
    //     )
    // }
}

export default CommentTable;
