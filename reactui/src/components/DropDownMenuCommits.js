import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "./Navbar_Developers";
import CommitChart from "./CommitChart";


function DropDownMenuCommit ({listOfDevelopers, style}) {

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

        <div>
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
            <CommitChart  devName = {sessionStorage.getItem("CurrentDeveloper")}/>

        </div>
    )


}

export default DropDownMenuCommit;