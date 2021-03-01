import React, {Component, useEffect, useRef} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "./Navbar_Developers";
import CommitChart from "./CommitChart";
import './DropDownMenu.css';
import DateRangeCommits from "./DateRangeCommits";


function DropDownMenuCommit ({listOfDevelopers}) {

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
            <div className="DropDownMenu">

            <Select
                options={devArray}
                defaultValue={{ label: currentDeveloper, value: currentDeveloper }}
                onChange={handleChange}/>
            </div>

            <div className='dateRangeSetting'>
                <DateRangeCommits/>
            </div>
            <br>
            </br>
        </div>
    )


}

export default DropDownMenuCommit;