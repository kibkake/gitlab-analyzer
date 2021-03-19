import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "./NavBars_Menu/Navbar_Developers";
import './DropDownMenu.css';
import DateRangeSummary from './DateRangeSummary'
import "./HBox.css"
import ProjectService from "../Service/ProjectService";



function DropDownMenuSummary ({listOfDevelopers}) {

    const devArray = [];

    const pathArray = window.location.pathname.split('/');

    if(sessionStorage.getItem("CurrentDeveloper") == null){
        sessionStorage.setItem("CurrentDeveloper", pathArray[4])
    }

    listOfDevelopers.map(item => {devArray.push({label: item, value: item})})

    const[selectedValue, setSelectedValue] = useState(
        pathArray[4]
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
        sessionStorage.setItem("CurrentDeveloper", obj.label)
    }

    function getInitialStartDate() {

        if(sessionStorage.getItem("startdate") != null){
            return new Date (sessionStorage.getItem("startdate") + "T12:00:00")
        }
        else if(localStorage.getItem("startdate") != null){
            sessionStorage.setItem("startdate", localStorage.getItem("startdate"))
            return new Date (localStorage.getItem("startdate") + "T12:00:00")
        }
        else {
            sessionStorage.setItem("startdate", "2021-01-11")
            return new Date (sessionStorage.getItem("startdate") + "T12:00:00")
        }
    }

    function getInitialEndDate() {

        if(sessionStorage.getItem("enddate") != null){
            return new Date (sessionStorage.getItem("enddate") + "T12:00:00")
        }
        else if(localStorage.getItem("enddate") != null){
            sessionStorage.setItem("enddate", localStorage.getItem("enddate"))
            return new Date (localStorage.getItem("enddate") + "T12:00:00")
        }
        else {
            sessionStorage.setItem("enddate", "2021-02-22")
            return new Date (sessionStorage.getItem("enddate") + "T12:00:00")
        }
    }

    function changeDateFormat(time) {

        console.log("changing format", time)

        var startDateArr = (time.toDateString()).split(" ");

        var monthLetter = startDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter);
        var day = startDateArr[2];
        var year = startDateArr[3];
        var completeDate = year + "-" + month + "-" + day;
        return completeDate;
    }

    return (
        <div>
            <Navbar_Developers devName = {selectedValue}/>

            <div>
                <div className="DropDownMenu">
                <Select
                    options={devArray}
                    defaultValue={{label: selectedValue, value: selectedValue}}
                    onChange={handleChange}
                />
                </div>

                <h1 style={{textAlign: 'center'}}>{selectedValue} Summary</h1>

            </div>

            <br>
            </br>
            <div>
                <DateRangeSummary devName = {selectedValue}/>

            </div>

        </div>
    )
}

export default DropDownMenuSummary;

