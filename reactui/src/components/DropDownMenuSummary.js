import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import SummaryScoreTable from "./SummaryScoreTable";
import CommentChart from "./CommentChart";
import Navbar_Developers from "./Navbar_Developers";
import './DropDownMenu.css';
import CommitMRScoreChart from "./CommitMRScoreChart";
import SummaryChartRadios from "./RadioButtonSummaryChart";
import CommitMRNumChart from "./CommitMRNumChart";
import SwitchButtonChart from "./SwitchButtonChart";


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
                <h4 style={{textAlign:'center'}}>Total Scores (add copy button)</h4>
                <SummaryScoreTable devName = {sessionStorage.getItem("CurrentDeveloper")}/>
                <br>
                </br>

                <SwitchButtonChart/>

                <br>
                </br>

                <h4 style={{textAlign: 'center'}}>Comment Score</h4>
                <CommentChart devName = {sessionStorage.getItem("CurrentDeveloper")}/>
            </div>

        </div>
    )
}

export default DropDownMenuSummary;

