import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "../NavBars_Menu/Navbar_Developers";
import '../DropDownMenus/DropDownMenu.css';
import "../Commits/HBox.css"
import ProjectService from "../../Service/ProjectService";
import SummaryScoreTable from "./SummaryScoreTable";
import SummaryChartRadios from "./RadioButtonSummaryChart";
import moment from 'moment'
import SnapshotWidgetComponent from "../Snapshot/SnapshotWidgetComponent";

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
            return new Date (sessionStorage.getItem("startdate"))
        }
        else if(localStorage.getItem("startdate") != null){
            sessionStorage.setItem("startdate", localStorage.getItem("startdate"))
            return new Date (localStorage.getItem("startdate"))
        }
        else {
            sessionStorage.setItem("startdate", "2021-01-20")
            return new Date (sessionStorage.getItem("startdate"))
        }
    }

    function getInitialEndDate() {

        if(sessionStorage.getItem("enddate") != null){
            return new Date (sessionStorage.getItem("enddate"))
        }
        else if(localStorage.getItem("enddate") != null){
            sessionStorage.setItem("enddate", localStorage.getItem("enddate"))
            return new Date (localStorage.getItem("enddate"))
        }
        else {
            let currentDate = moment().format("YYYY-MM-DD");
            sessionStorage.setItem("enddate", currentDate)
            return new Date (sessionStorage.getItem("enddate"))
        }
    }

    function changeDateFormat(time) {

        var startDateArr = (time.toString()).split(" ");
        var monthLetter = startDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter);
        var day = startDateArr[2];
        var year = startDateArr[3];
        var Time = startDateArr[4];
        console.log(Time)
        var completeDate = year + "-" + month + "-" + day+"T"+Time;
        return completeDate;
    }

    return (

        <div>
            <Navbar_Developers devName = {selectedValue}/>

            <div className="box-container">
                <h3>Developer: </h3>
            <div className="DropDownMenu">
                    <Select
                        options={devArray}
                        defaultValue={{label: selectedValue, value: selectedValue}}
                        onChange={handleChange}
                    />
            </div>
            </div>
            <div>
                <SnapshotWidgetComponent/>
            </div>
                <h4 style={{textAlign:'center'}}>Total Scores For {selectedValue}  </h4>
                <SummaryScoreTable devName = {selectedValue}
                                   startTime = {changeDateFormat(getInitialStartDate())}
                                   endTime = {changeDateFormat(getInitialEndDate())}
                />
                <div>
                <SummaryChartRadios devName = {selectedValue}
                                    startTime = {changeDateFormat(getInitialStartDate())}
                                    endTime = {changeDateFormat(getInitialEndDate())}/>

            </div>
        </div>
    )
}

export default DropDownMenuSummary;