import "../App.css"
import { MuiPickersUtilsProvider,DateTimePicker } from '@material-ui/pickers';
import React,{ Component } from "react";
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';

function Settings(){
    
    const [selectedStartDate,setSelectedStartDate] = React.useState(
        new Date('2021-02-08T12:00:00')
    )
    
    const [selectedEndDate,setSelectedEndDate] = React.useState(
        new Date('2021-02-08T12:00:00')
    )
    const handleStartDateChange= (date) =>{
        setSelectedStartDate(date)
    }
    const handleEndDateChange= (date) =>{
        setSelectedEndDate(date)
    }
    return(   
        <>
        <h2 style={{marginTop:'40px',marginLeft:'43%',paddingBottom:'10px'}}>Snapshot Date Range</h2>
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
export default Settings;