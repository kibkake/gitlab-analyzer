import "../App.css"
import { MuiPickersUtilsProvider,DateTimePicker } from '@material-ui/pickers';
import React, {Component, useEffect, useRef, useState} from "react";
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import ProjectService from '../Service/ProjectService'
import CommitChart from "./CommitChart";

function DateRangeCommits({devName}){

    const [selectedStartDate,setSelectedStartDate] = React.useState(
        new Date(sessionStorage.getItem('startdate') + 'T12:00:00')
    )

    const [selectedEndDate,setSelectedEndDate] = React.useState(
        new Date(sessionStorage.getItem('enddate') + 'T12:00:00')
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

    function changeDateFormat(time) {

        var startDateArr = (time.toDateString()).split(" ");

        var monthLetter = startDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter);
        var day = startDateArr[2];
        var year = startDateArr[3];
        var completeDate = year + "-" + month + "-" + day;
        return completeDate;
    }

    const mounted = useRef();
    useEffect(()=> {
        if (!mounted.current) {
            mounted.current = true;
        }
        //else {
        //}
    })

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
            <CommitChart devName = {devName}
                                startTime = {changeDateFormat(selectedStartDate)}
                                endTime = {changeDateFormat(selectedEndDate)}/>
        </>
    )
}
export default DateRangeCommits;
