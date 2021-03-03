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

    const pathArray = window.location.pathname.split('/');

    const currentDeveloper = sessionStorage.getItem("CurrentDeveloper")
    const [selectedValue, setSelectedValue] = useState(
        pathArray[4]
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
        sessionStorage.setItem("CurrentDeveloper", obj.label);
    }

    return (

        <div>
            <Navbar_Developers devName = {selectedValue}/>
            <br>
            </br>
            <div className="DropDownMenu">

            <Select
                options={devArray}
                defaultValue={{ label: selectedValue, value: selectedValue }}
                onChange={handleChange}/>
            </div>
            <CommitChart devName = {selectedValue}
                         startTime = {sessionStorage.getItem("startdate")}
                         endTime = {sessionStorage.getItem("enddate")}/>
            <br>
            </br>
        </div>
    )


}

export default DropDownMenuCommit;

/*    <div className='dateRangeSetting'>
                <DateRangeCommits/>
            </div>
*/