import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import SummaryScoreTable from "./SummaryScoreTable";
import Navbar_Developers from "./Navbar_Developers";
import './DropDownMenu.css';
import SummaryChartRadios from "./RadioButtonSummaryChart";


function DropDownMenuSummary ({listOfDevelopers}) {

    const devArray = [];
    listOfDevelopers.map(item => {devArray.push({label: item, value: item})})

    const pathArray = window.location.pathname.split('/');
    const developer = pathArray[4];
    const currentDeveloper = sessionStorage.getItem("CurrentDeveloper")

    const[selectedValue, setSelectedValue] = useState(null);

    const handleChange = obj => {
        setSelectedValue(obj.label);
        sessionStorage.setItem("CurrentDeveloper", obj.label)
    }

    return (
        <div>
            <div>
                <Navbar_Developers devName = {sessionStorage.getItem("CurrentDeveloper")}/>
                <h1 style={{textAlign: 'center'}}>{sessionStorage.getItem("CurrentDeveloper")} Summary</h1>

                <br>
                </br>
                <div className="DropDownMenu">
                <Select
                    options={devArray}
                    defaultValue={{label: currentDeveloper, value: currentDeveloper}}
                    onChange={handleChange}
                />
                </div>
            </div>
            <div>
                <br>
            </br>
                <br>
                </br>

                <h4 style={{textAlign:'center'}}>Total Scores (add copy button)</h4>
                <SummaryScoreTable devName = {sessionStorage.getItem("CurrentDeveloper")}/>
                <br>
                </br>
                <h4 style={{textAlign:'center'}}>View Data By</h4>
                <SummaryChartRadios devName = {sessionStorage.getItem("CurrentDeveloper")}/>
                <br>
                </br>

            </div>

        </div>
    )
}

export default DropDownMenuSummary;

