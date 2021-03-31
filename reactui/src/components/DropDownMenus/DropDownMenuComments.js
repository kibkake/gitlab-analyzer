import React, {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "../NavBars_Menu/Navbar_Developers";
import CommentTable from "../Comment/CommentTable";
import './DropDownMenu.css';
import SnapshotWidgetComponent from "../Snapshot/SnapshotWidgetComponent";

function DropDownMenuComments ({listOfDevelopers}) {

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

        <div>
            <Navbar_Developers devName = {selectedValue}/>
            <br/>
            <div className="DropDownMenu">

                <Select
                    options={devArray}
                    defaultValue={{ label: selectedValue, value: selectedValue }}
                    onChange={handleChange}/>
                <SnapshotWidgetComponent/>
            </div>
            <br/>
            <h1 style={{textAlign:'center'}}>Comment Contribution</h1>
            <br/>
            <CommentTable devName = {selectedValue}/>

        </div>
    )
}

export default DropDownMenuComments;