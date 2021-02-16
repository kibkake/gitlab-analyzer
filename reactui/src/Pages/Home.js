import "../App.css"
import React,{ Component } from "react";
import LoginComponent from "../components/LoginComponent"

function Home(){

    return(   
        
        <div>
            <h1 style={{textAlign:'center'}}>
                Welcome to Pluto Gitlab analyzer
            </h1>
            <LoginComponent/>
        </div>


    )

}

export default Home;