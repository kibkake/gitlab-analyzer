/*
References:
- https://stackoverflow.com/questions/30888197/format-datetime-to-yyyy-mm-dd-hhmmss-in-moment-js
Used this to figure out how to get the current date as a string.
 */

import "../../App.css"
import { MuiPickersUtilsProvider,DateTimePicker, DatePicker } from '@material-ui/pickers';
import React,{ Component } from "react";
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import ProjectService from '../../Service/ProjectService'
import moment from 'moment'

function DateRangeSettings(){

    function getStartDate(){
        if(sessionStorage.getItem("startdate") == null){
            sessionStorage.setItem("startdate", "2021-01-20"+"T12:00:00")
        }
        if(localStorage.getItem("startdate") == null){
            localStorage.setItem("startdate", "2021-01-20"+"T12:00:00")
        }
        return new Date(sessionStorage.getItem("startdate"))
    }

    function getEndDate(){
        let currentDate = moment().format("YYYY-MM-DDTHH:mm:ss");
        console.log(currentDate)
        if(sessionStorage.getItem("enddate") == null){
            sessionStorage.setItem("enddate", currentDate)
        }
        if(localStorage.getItem("enddate") == null){
            localStorage.setItem("enddate", currentDate)
        }
        return new Date(sessionStorage.getItem("enddate"))
    }

    function sendDateToBackEnd(date){
        fetch("/api/v1/setstartdate", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(date),
        })
    }

    const [selectedStartDate,setSelectedStartDate] = React.useState(
        getStartDate()
    )
    
    const [selectedEndDate,setSelectedEndDate] = React.useState(
        getEndDate()
    )

    function formatDate(date){
        console.log(date)
        var startDateArr = (date.toString()).split(" ");
        console.log(startDateArr)
        var monthLetter = startDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter);
        var day = startDateArr[2];
        var year = startDateArr[3];
        var time = startDateArr[4];
        
        return year + "-" + month + "-" + day+'T'+time;

    }
    const handleStartDateChange= (date) =>{
        setSelectedStartDate(date)
        const data = { starttime: selectedStartDate };
        var completeDate = formatDate(date)
        sessionStorage.setItem("startdate", completeDate);
        localStorage.setItem("startdate", completeDate)
        console.log(sessionStorage.getItem("startdate"))
        console.log(localStorage.getItem("startdate"))
    }
    const handleEndDateChange= (date) =>{
        setSelectedEndDate(date)
        const data = { endtime: selectedEndDate };
        var completeDate = formatDate(date)

        sessionStorage.setItem("enddate", completeDate);
        localStorage.setItem("enddate", completeDate)

    }
    return(   
        <>
        <h2 style={{textAlign:'center'}}>Snapshot Date Range</h2>
        <Grid container justify="center">
            <span className="startDate">   
                <MuiPickersUtilsProvider utils={DateFnsUtils}>   
                        <DateTimePicker
                            variant='inline' 
                            format='MM/dd/yyyy hh:mm a'
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
                        format='MM/dd/yyyy hh:mm a'
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
export default DateRangeSettings;