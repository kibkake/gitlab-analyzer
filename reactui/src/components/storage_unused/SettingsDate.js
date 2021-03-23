import React, {Component} from 'react'
import {HorizontalBar} from 'react-chartjs-2'
import Button from "react-bootstrap/Button";
import CommitChart from "../Commits/CommitChart";
import DateRangeSettings from "../DateSetting/DateRangeSettings";

function SettingsDate (){





    const [selectedTime, setSelectedTime] = useState('')


        return (
            <div>
                <DateRangeSettings setSelectedTime={setSelectedTime}/>
                <CommitChart selectedTime={selectedTime}/>
            </div>
        )




}

export default SettingsDate
