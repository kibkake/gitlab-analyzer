import React,{ Component } from "react";
import ProjectService from "../Service/ProjectService";

function DateRangeSummary(){

    function getInitialStartDate() {

        if(sessionStorage.getItem("startdate") != null){
            return new Date (sessionStorage.getItem("startdate"))
        }
        else if(localStorage.getItem("startdate") != null){
            return new Date (localStorage.getItem("startdate"))
        }
        else {
            sessionStorage.setItem("startdate", "2021-01-11")
            return new Date (sessionStorage.getItem("startdate") + "T12:00:00")
        }
    }

    function getInitialEndDate() {

        if(sessionStorage.getItem("enddate") != null){
            return new Date (sessionStorage.getItem("startdate"))
        }
        else if(localStorage.getItem("enddate") != null){
            return new Date (localStorage.getItem("startdate"))
        }
        else {
            sessionStorage.setItem("enddate", "2021-02-22")
            return new Date (sessionStorage.getItem("enddate") + "T12:00:00")
        }
    }

    const [selectedStartDate,setSelectedStartDate] = React.useState(
        getInitialStartDate()
    )

    const [selectedEndDate,setSelectedEndDate] = React.useState(
        getInitialEndDate()
    )

    const handleStartDateChange= (date) =>{
        setSelectedStartDate(date)
        const data = { starttime: selectedStartDate };
        var startDateArr = (selectedStartDate.toDateString()).split(" ");

        var monthLetter = startDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter);
        var day = startDateArr[2];
        var year = startDateArr[3];
        var completeDate = year + "-" + month + "-" + day;
        sessionStorage.setItem("startdate", completeDate);
    }
    const handleEndDateChange= (date) =>{
        setSelectedEndDate(date)
        const data2 = { endtime: selectedEndDate };
        var endDateArr = (selectedEndDate.toDateString()).split(" ");

        var monthLetter = endDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter)
        var day = endDateArr[2];
        var year = endDateArr[3];

        var completeDate = year + "-" + month + "-" + day;
        sessionStorage.setItem("enddate", completeDate);
    }

    return(
        <>
        </>

    )
}
export default DateRangeSummary;
