import React, {Component} from 'react'
import {HorizontalBar} from 'react-chartjs-2'
import Button from "react-bootstrap/Button";
import CommitChart from "./CommitChart";
import Settings from "../Pages/Settings";

function SettingsDate (){





    const [selectedTime, setSelectedTime] = useState('')


        return (
            <div>
                <Settings setSelectedTime={setSelectedTime}/>
                <CommitChart selectedTime={selectedTime}/>
            </div>
        )




}

export default SettingsDate
