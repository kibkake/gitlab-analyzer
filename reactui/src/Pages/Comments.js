import CommentTable from "../components/CommentTable";
import RadioButtons from "../components/RadioButton";
import CustomizedRadios from "../components/RadioButton";
import '../App.css';
import Navbar_Developers from "../components/Navbar_Developers";
import React from "react";

function Comments() {
    return (
        <div>
            <Navbar_Developers/>
            <h1 style={{textAlign:'center'}}>Comment Contribution</h1>
            <br></br>
            <div style={{display: 'flex',  justifyContent:'center', alignItems:'center'}}>
            <CustomizedRadios/>
            </div>
            <h4 style={{textAlign:'center'}}>display only top 10 comments by word Count or something </h4>
            <CommentTable/>
        </div>
    )
}

export default Comments