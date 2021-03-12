import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "./NavBars_Menu/Navbar_Developers";
import './DropDownMenu.css';
import MergeListTable from "./MergeRequest/MergeListTable";

function DropDownMenuMerge ({listOfDevelopers}) {

    const devArray = [];
    listOfDevelopers.map(item => {
        devArray.push({label: item, value: item})
    })

    const pathArray = window.location.pathname.split('/');

    const [selectedValue, setSelectedValue] = useState(
        pathArray[4]
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
        sessionStorage.setItem("CurrentDeveloper", obj.label);
    }

    return (

        <div classname='CodeDiff'>
            <Navbar_Developers devName = {sessionStorage.getItem("CurrentDeveloper")}/>
            <br>
            </br>
            <div className="DropDownMenu">

            <Select
                options={devArray}
                defaultValue={{ label: selectedValue, value: selectedValue }}
                onChange={handleChange}/>
            </div>
            <br>
            </br>
            <h1 style={{textAlign: 'center'}}>List of MR + full diff</h1>
            <h2 style={{textAlign: 'center'}}>-list of commits for each MR + code diff</h2>
            <h2 style={{textAlign: 'center'}}>-code diff should be shown in the same page</h2>
            <h4 style={{textAlign: 'center'}}>-Highlight + part?</h4>

            <MergeListTable  devName = {selectedValue}/>
        </div>
    )


}

export default DropDownMenuMerge;