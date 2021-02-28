import CommentTable from "../components/CommentTable";
import RadioButtons from "../components/RadioButtonComment";
import CustomizedRadios from "../components/RadioButtonComment";
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
            <br></br>
            <h4 style={{textAlign:'center'}}>Top 10 comments </h4>
            <CommentTable/>
        </div>
    )
}

export default Comments