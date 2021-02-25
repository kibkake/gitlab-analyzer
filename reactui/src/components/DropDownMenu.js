import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import SummaryScoreTable from "./SummaryScoreTable";
import StackedBarChart from "./StackedBarChart";
import CommentChart from "./CommentChart";


function DropDownMenu ({listOfDevelopers, sentDev}) {

    const devArray = [];
    listOfDevelopers.map(item => {devArray.push({label: item, value: item})})

    const pathArray = window.location.pathname.split('/');
    const developer = pathArray[4];

    const[selectedValue, setSelectedValue] = useState(
        developer
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
    }

    return (
        <div>
            <div>
                <Select
                    options={devArray}
                    defaultValue={{ label: developer, value: developer }}
                    onChange={handleChange}
                />
            </div>
            <div>{selectedValue}</div>
            <div>
                <br>
            </br>
                <h4 style={{textAlign: 'center'}}>Total Scores</h4>
                <SummaryScoreTable/>
                <br>
                </br>

                <h4 style={{textAlign: 'center'}}>Score of Commits/Merge Requests</h4>
                <StackedBarChart/>
                <br>
                </br>
                <br>
                </br>
                <h4 style={{textAlign: 'center'}}>Comment Score</h4>
                <CommentChart/>
            </div>

        </div>
    )
}

export default DropDownMenu;