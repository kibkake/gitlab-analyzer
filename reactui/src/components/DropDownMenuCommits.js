import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import SummaryScoreTable from "./SummaryScoreTable";
import StackedBarChart from "./StackedBarChart";
import CommentChart from "./CommentChart";
import Navbar_Developers from "./Navbar_Developers";
import CommitChart from "./CommitChart";


function DropDownMenuCommit ({listOfDevelopers, sentDev}) {

    const devArray = [];
    listOfDevelopers.map(item => {
        devArray.push({label: item, value: item})
    })

    const pathArray = window.location.pathname.split('/');
    const developer = pathArray[4];

    const [selectedValue, setSelectedValue] = useState(
        developer
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
    }

    return (

        <div>
            <Navbar_Developers devName = {selectedValue}/>
            <br>
            </br>
            <Select
                options={devArray}
                defaultValue={{ label: developer, value: developer }}
                onChange={handleChange}/>
            <br>
            </br>
            <CommitChart  devName = {selectedValue}/>

        </div>
    )


}

export default DropDownMenuCommit;