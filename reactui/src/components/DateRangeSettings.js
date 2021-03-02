import "../App.css"
import { MuiPickersUtilsProvider,DateTimePicker } from '@material-ui/pickers';
import React,{ Component } from "react";
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import ProjectService from '../Service/ProjectService'

function DateRangeSettings(){

    function getStartDate(){
        if(sessionStorage.getItem("startdate") == null){
            sessionStorage.setItem("startdate", "2021-01-11")
        }
        if(localStorage.getItem("startdate") == null){
            localStorage.setItem("startdate", "2021-01-11")
        }
        return new Date(sessionStorage.getItem("startdate") + "T12:00:00")
    }

    function getEndDate(){
        if(sessionStorage.getItem("enddate") == null){
            sessionStorage.setItem("enddate", "2021-02-22")
        }
        if(localStorage.getItem("enddate") == null){
            localStorage.setItem("enddate", "2021-02-22")
        }
        return new Date(sessionStorage.getItem("enddate") + "T12:00:00")
    }

    const [selectedStartDate,setSelectedStartDate] = React.useState(
        getStartDate()
    )
    
    const [selectedEndDate,setSelectedEndDate] = React.useState(
        getEndDate()
    )
    const handleStartDateChange= (date) =>{
        setSelectedStartDate(date)
        const data = { starttime: selectedStartDate };

        var startDateArr = (selectedStartDate.toDateString()).split(" ");

        var monthLetter = startDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter);
        var day = startDateArr[2];
        var year = startDateArr[3];
        //2015-12-13
        var completeDate = year + "-" + month + "-" + day;
        sessionStorage.setItem("startdate", completeDate);
        console.log(sessionStorage.getItem("startdate"))
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
        console.log(sessionStorage.getItem("enddate"))

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
export default DateRangeSettings;