import React, {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "./Navbar_Developers";
import CustomizedRadios from "./RadioButton";
import CommentTable from "./CommentTable";

function DropDownMenuComments ({listOfDevelopers, style}) {

    const devArray = [];
    listOfDevelopers.map(item => {
        devArray.push({label: item, value: item})
    })

    const currentDeveloper = sessionStorage.getItem("CurrentDeveloper")
    const [selectedValue, setSelectedValue] = useState(
        null
    );

    const handleChange = obj => {
        setSelectedValue(obj.label);
        sessionStorage.setItem("CurrentDeveloper", obj.label);
    }

    return (

        <div>
            <Navbar_Developers devName = {sessionStorage.getItem("CurrentDeveloper")}/>
            <br>
            </br>
            <div style={style}>

            <Select
                options={devArray}
                defaultValue={{ label: currentDeveloper, value: currentDeveloper }}
                onChange={handleChange}/>
            </div>
            <br>
            </br>
            <h1 style={{textAlign:'center'}}>Comment Contribution</h1>
            <br></br>
            <div style={{display: 'flex',  justifyContent:'center', alignItems:'center'}}>
                <CustomizedRadios/>
            </div>
            <br></br>
            <h4 style={{textAlign:'center'}}>Top 10 comments </h4>
            <CommentTable devName = {sessionStorage.getItem("CurrentDeveloper")}/>

        </div>
    )
}

export default DropDownMenuComments;