import React,{ Component } from "react";
import ProjectService from "../Service/ProjectService";
import Grid from "@material-ui/core/Grid";
import {DateTimePicker, MuiPickersUtilsProvider, DatePicker} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
import SummaryScoreTable from "./SummaryScoreTable";
import RadioButtonSummaryChart from "./RadioButtonSummaryChart";
import CommitMRScoreChart from "./CommitMRScoreChart";


function DateRangeSummary({devName}){

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

    const [selectedStartDate,setSelectedStartDate] = React.useState(
        getInitialStartDate()
    )

    const [selectedEndDate,setSelectedEndDate] = React.useState(
        getInitialEndDate()
    )

    function changeDateFormat(time) {

        var startDateArr = (time.toDateString()).split(" ");

        var monthLetter = startDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter);
        var day = startDateArr[2];
        var year = startDateArr[3];
        var completeDate = year + "-" + month + "-" + day;
        return completeDate;
    }

    const handleStartDateChange= (date) =>{
        setSelectedStartDate(date)

        var completeDate = changeDateFormat(date)
        sessionStorage.setItem("startdate", completeDate);
    }
    const handleEndDateChange= (date) =>{
        setSelectedEndDate(date)
        var completeDate = changeDateFormat(date)

        sessionStorage.setItem("enddate", completeDate);
    }

    return(
        <>
            <Grid container justify="center">
                <span className="startDate">
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <DatePicker
                            variant='inline'
                            format='MM/dd/yyyy'
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
                        <DatePicker
                            variant='inline'
                            format='MM/dd/yyyy'
                            margin='normal'
                            id='endDate'
                            label='End Date'
                            value={selectedEndDate}
                            onChange={handleEndDateChange}
                        />
                    </MuiPickersUtilsProvider>
                </span>
            </Grid>
            <br>
            </br>
            <h4 style={{textAlign:'center'}}>Total Scores (add copy button)</h4>
            <SummaryScoreTable devName = {devName}
                               startTime = {changeDateFormat(selectedStartDate)}
                               endTime = {changeDateFormat(selectedEndDate)}
                               />
            <RadioButtonSummaryChart devName = {devName}
                                     startTime = {changeDateFormat(selectedStartDate)}
                                     endTime = {changeDateFormat(selectedEndDate)}/>
            <br>
            </br>

        </>

    )
}
export default DateRangeSummary;
