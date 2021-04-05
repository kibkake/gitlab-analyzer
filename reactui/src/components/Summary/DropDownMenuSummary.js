import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "../NavBars_Menu/Navbar_Developers";
import '../DropDownMenus/DropDownMenu.css';
import "../Commits/HBox.css"
import ProjectService from "../../Service/ProjectService";
import SummaryScoreTable from "./SummaryScoreTable";
import SummaryChartRadios from "./RadioButtonSummaryChart";




function DropDownMenuSummary ({listOfDevelopers}) {

    const devArray = [];

    const pathArray = window.location.pathname.split('/');

    if(sessionStorage.getItem("CurrentDeveloper") == null){
        getCurDevInfo(pathArray[2], pathArray[4])
    }
    async function getCurDevInfo(repNum, devID) {
        let url2 = '/api/v2/Project/' + repNum + '/Developers/' + devID + 'devInfo'
        var devs = [];
        fetch(url2)
            .then(response => {
                return response.json();
            })
            .then(d => {
                devs = d;
                console.log("Dev info", devs);
            })
        var userData = [];
        userData = await devs.map(({username, id}) => ({username, id}))
        console.log("Maped user Data", userData)
        await sessionStorage.setItem("CurrentDeveloper" + repNum, JSON.stringify(userData));
        console.log("Developer session storage:", sessionStorage.getItem('CurrentDeveloper' + repNum))
    }

    listOfDevelopers.map(item => {devArray.push({label: item.username, value: item.id})})

    const[selectedValue, setSelectedValue] = useState(
        pathArray[4]
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
        sessionStorage.setItem("CurrentDeveloper", JSON.stringify(obj))
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
            sessionStorage.setItem("startdate", "2021-01-20")
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
            sessionStorage.setItem("enddate", "2021-03-01")
            return new Date (sessionStorage.getItem("enddate") + "T12:00:00")
        }
    }

    function changeDateFormat(time) {

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