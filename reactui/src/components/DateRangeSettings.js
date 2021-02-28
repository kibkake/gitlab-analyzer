import "../App.css"
import { MuiPickersUtilsProvider,DateTimePicker } from '@material-ui/pickers';
import React,{ Component } from "react";
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import ProjectService from '../Service/ProjectService'

function DateRangeSettings(){
    
    const [selectedStartDate,setSelectedStartDate] = React.useState(
        new Date('2021-01-01T12:00:00')
    )
    
    const [selectedEndDate,setSelectedEndDate] = React.useState(
        new Date('2021-02-27T12:00:00')
    )
    const handleStartDateChange= (date) =>{
        setSelectedStartDate(date)
        const data = { starttime: selectedStartDate };
        /*const result = fetch("http://localhost:8080/api/v1/setstartdate", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data),
        })*/
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
        /*const result = fetch("http://localhost:8080/api/v1/setenddate", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data2),
        })*/

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