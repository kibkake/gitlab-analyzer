import "../App.css"
import React,{ Component } from "react";
import CodeDiffTable from "../components/CodeDiffTable";


function CodeDiff(){

    return(
        <div classname='CodeDiff'>
            <CodeDiffTable/>
        </div>
    )
}

export default CodeDiff;