import React, {useState} from 'react'
import Select from 'react-select'
import Navbar_Developers from "../NavBars_Menu/Navbar_Developers";
import CommentTable from "../Comment/CommentTable";
import './DropDownMenu.css';
import SnapshotWidgetComponent from "../Snapshot/SnapshotWidgetComponent";
import moment from "moment";
import MergeListTable from "../MergeRequest/MergeListTable";
import ProjectService from "../../Service/ProjectService";

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

    function getInitialStartDate() {
        if(sessionStorage.getItem("startdate") != null){
            return new Date (sessionStorage.getItem("startdate") + "T12:00:00")
        }
        else if(localStorage.getItem("startdate") != null){
            sessionStorage.setItem("startdate", localStorage.getItem("startdate"))
            return new Date (localStorage.getItem("startdate") + "T12:00:00")
        }
        else {
            sessionStorage.setItem("startdate", "2021-01-20")
            return new Date (sessionStorage.getItem("startdate") + "T12:00:00")
        }
    }

    function getInitialEndDate() {
        if(sessionStorage.getItem("enddate") != null){
            return new Date (sessionStorage.getItem("enddate") + "T12:00:00")
        }
        else if(localStorage.getItem("enddate") != null){
            sessionStorage.setItem("enddate", localStorage.getItem("enddate"))
            return new Date (localStorage.getItem("enddate") + "T12:00:00")
        }
        else {
            let currentDate = moment().format("YYYY-MM-DD");
            sessionStorage.setItem("enddate", currentDate)
            return new Date (sessionStorage.getItem("enddate") + "T12:00:00")
        }
    }

    function changeDateFormat(time) {
        var startDateArr = (time.toDateString()).split(" ");

        var monthLetter = startDateArr[1];
        var month = ProjectService.convertMonthToNumber(monthLetter);
        var day = startDateArr[2];
        var year = startDateArr[3];
        var completeDate = year + "-" + month + "-" + day;
        return completeDate;
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
            </div>
            <div className="snap-widget">
                <SnapshotWidgetComponent/>
            </div>
            <h1 style={{textAlign:'center'}}>Comment Contribution</h1>
            <br/>
            <CommentTable devName = {selectedValue}
                          startTime = {changeDateFormat(getInitialStartDate())}
                          endTime = {changeDateFormat(getInitialEndDate())}/>
        </div>
    )
}

export default DropDownMenuComments;