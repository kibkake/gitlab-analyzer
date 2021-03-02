import React,{ Component } from "react";
import ProjectService from "../Service/ProjectService";
import Grid from "@material-ui/core/Grid";
import {DateTimePicker, MuiPickersUtilsProvider} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";

function DateRangeSummary(){

    function getInitialStartDate() {

        if(sessionStorage.getItem("startdate") != null){
            return new Date (sessionStorage.getItem("startdate") + "T12:00:00")
        }
        else if(localStorage.getItem("startdate") != null){
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
            return new Date (localStorage.getItem("enddate") + "T12:00:00")
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
            <h2 style={{marginTop:'40px',marginLeft:'39%',paddingBottom:'10px'}}>Snapshot Date Range</h2>
            <Grid container justify="center">
                <span className="startDate">
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <DateTimePicker
                            variant='inline'
                            format='MM/dd/yyyy h:mm a'
                            margin='normal'
                            id='startDate'
                            label='Start Date'
                            value={selectedStartDate}
                            onChange={handleStartDateChange}
                        />
                    </MuiPickersUtilsProvider>
                </span>

                <span className="endDate">
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <DateTimePicker
                            variant='inline'
                            format='MM/dd/yyyy h:mm a'
                            margin='normal'
                            id='endDate'
                            label='End Date'
                            value={selectedEndDate}
                            onChange={handleEndDateChange}
                        />
                    </MuiPickersUtilsProvider>
                </span>
            </Grid>
        </>

    )
}
export default DateRangeSummary;
