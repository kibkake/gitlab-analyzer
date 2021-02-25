import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'


function DropDownMenu ({listOfDevelopers}) {


    const[selectedValue, setSelectedValue] = useState(
        null
    );
    console.log("list of devs")
    console.log(listOfDevelopers)

    return (
        <div>
            <div className="Footer">
                <Select
                />
            </div>
        </div>
    )

}

export default DropDownMenu;