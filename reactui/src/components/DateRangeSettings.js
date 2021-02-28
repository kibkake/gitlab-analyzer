import "../App.css"
import { MuiPickersUtilsProvider,DateTimePicker } from '@material-ui/pickers';
import React,{ Component } from "react";
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';

function DateRangeSettings(){
    
    const [selectedStartDate,setSelectedStartDate] = React.useState(
        new Date('2021-02-08T12:00:00')
    )
    
    const [selectedEndDate,setSelectedEndDate] = React.useState(
        new Date('2021-02-08T12:00:00')
    )
    const handleStartDateChange= (date) =>{
        setSelectedStartDate(date)
        const data = { starttime: selectedStartDate };
        const result = fetch("http://localhost:8080/api/v1/setstartdate", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data),
        })
        sessionStorage.setItem("startdate", selectedStartDate);
        var startDate = sessionStorage.getItem("startdate");
        var startDateArr = startDate.split(" ");
        console.log(startDateArr[2])
    }
    const handleEndDateChange= (date) =>{
        setSelectedEndDate(date)
        const data2 = { endtime: selectedEndDate };
        const result = fetch("http://localhost:8080/api/v1/setenddate", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data2),
        })
        sessionStorage.setItem("enddate", selectedStartDate)
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