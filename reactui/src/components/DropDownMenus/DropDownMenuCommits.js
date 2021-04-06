import React, {Component, useEffect, useRef} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "../NavBars_Menu/Navbar_Developers";
import CommitChart from "../Commits/CommitChart";
import './DropDownMenu.css';


function DropDownMenuCommit ({listOfDevelopers}) {

    const devArray = [];
    listOfDevelopers.map(item => {
        devArray.push({label: item, value: item})
    })

    const pathArray = window.location.pathname.split('/');
    var currDev = JSON.parse(sessionStorage.getItem('CurrentDeveloper')).username
    const [selectedValue, setSelectedValue] = useState(
        currDev
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
        sessionStorage.setItem("CurrentDeveloper", obj);
    }

    return (

        <div  style={{ overflow: "scroll"}}>
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