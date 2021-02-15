import "../App.css"
import React,{ Component } from "react";
import ProjectService from "../Service/ProjectService";
import CommentTable from "../components/CommentTable";

function Comments(){

    return(
        <div className='Comments'>
            <h1 style={{textAlign:'center'}}>Comment For Each Note</h1>
             <CommentTable/>
        </div>
    )
}
export default Comments;
