import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "./Navbar_Developers";
import CommitChart from "./CommitChart";
import CodeDiffTable from "./CodeDiffTable";


function DropDownMenuMerge ({listOfDevelopers, style}) {

    const devArray = [];
    listOfDevelopers.map(item => {
        devArray.push({label: item, value: item})
    })

    const currentDeveloper = sessionStorage.getItem("CurrentDeveloper")
    const [selectedValue, setSelectedValue] = useState(
        null
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
        sessionStorage.setItem("CurrentDeveloper", obj.label);
    }

    return (

        <div classname='CodeDiff'>
            <Navbar_Developers devName = {sessionStorage.getItem("CurrentDeveloper")}/>
            <br>
            </br>
            <div style={style}>

            <Select
                options={devArray}
                defaultValue={{ label: currentDeveloper, value: currentDeveloper }}
                onChange={handleChange}/>
            </div>
            <br>
            </br>
            <h1 style={{textAlign: 'center'}}>List of MR + full diff</h1>
            <h2 style={{textAlign: 'center'}}>-list of commits for each MR + code diff</h2>
            <h2 style={{textAlign: 'center'}}>-code diff should be shown in the same page</h2>
            <h4 style={{textAlign: 'center'}}>-Highlight + part?</h4>

            <CodeDiffTable  devName = {sessionStorage.getItem("CurrentDeveloper")}/>

        </div>
    )


}

export default DropDownMenuMerge;