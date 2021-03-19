import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "./NavBars_Menu/Navbar_Developers";
import './DropDownMenu.css';
import DateRangeSummary from './DateRangeSummary'
import "./HBox.css"



function DropDownMenuSummary ({listOfDevelopers}) {

    const devArray = [];

    const pathArray = window.location.pathname.split('/');

    if(sessionStorage.getItem("CurrentDeveloper") == null){
        sessionStorage.setItem("CurrentDeveloper", pathArray[4])
    }

    listOfDevelopers.map(item => {devArray.push({label: item, value: item})})

    const[selectedValue, setSelectedValue] = useState(
        pathArray[4]
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
        sessionStorage.setItem("CurrentDeveloper", obj.label)
    }

    return (
        <div>
            <Navbar_Developers devName = {selectedValue}/>

            <div style="DevMenu">
                <div className="DropDownMenu">
                <Select
                    options={devArray}
                    defaultValue={{label: selectedValue, value: selectedValue}}
                    onChange={handleChange}
                />
                </div>

                <h1 style={{textAlign: 'center'}}>{selectedValue} Summary</h1>

            </div>

            <br>
            </br>
            <div>
                <DateRangeSummary devName = {selectedValue}/>

            </div>

        </div>
    )
}

export default DropDownMenuSummary;

