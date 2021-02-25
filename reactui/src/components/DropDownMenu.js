import React, {Component} from 'react'
import {useState} from 'react'
import Select from 'react-select'


function DropDownMenu ({listOfDevelopers, sentDev}) {

    const arr = [];
    listOfDevelopers.map(item => {arr.push({label: item, value: item})})

    const pathArray = window.location.pathname.split('/');
    const developer = pathArray[4];

    const[selectedValue, setSelectedValue] = useState(
        developer
    );
    console.log("list of devs")
    console.log(listOfDevelopers)

    return (
        <div>
            <div>
                <Select
                    options={arr}
                    defaultValue={{ label: developer, value: developer }}
                />
            </div>
        </div>
    )

}

export default DropDownMenu;