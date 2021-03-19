import "../App.css"
import { MuiPickersUtilsProvider,DateTimePicker, DatePicker } from '@material-ui/pickers';
import React,{ Component } from "react";
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import ProjectService from '../Service/ProjectService'

function DateRangeSettings(){

    function getStartDate(){
        if(sessionStorage.getItem("startdate") == null){
            sessionStorage.setItem("startdate", "2021-01-20")
        }
        if(localStorage.getItem("startdate") == null){
            localStorage.setItem("startdate", "2021-01-20")
        }
        return new Date(sessionStorage.getItem("startdate") + "T12:00:00")
    }

    function getEndDate(){
        if(sessionStorage.getItem("enddate") == null){
            sessionStorage.setItem("enddate", "2021-03-01")
        }
        if(localStorage.getItem("enddate") == null){
            localStorage.setItem("enddate", "2021-03-01")
        }
        return new Date(sessionStorage.getItem("enddate") + "T12:00:00")
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
        var startDateArr = (date.toDateString()).split(" ");

        var monthLetter = startDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter);
        var day = startDateArr[2];
        var year = startDateArr[3];

        return year + "-" + month + "-" + day;

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
        console.log(sessionStorage.getItem("enddate"))
        console.log(localStorage.getItem("enddate"))

    }
    return(   
        <>
        <h2 style={{textAlign:'center'}}>Snapshot Date Range</h2>
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
        </>
    )
}
export default DateRangeSettings;