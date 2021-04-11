import React from "react";
import './Popup.css'
import {CircularProgress} from "@material-ui/core";
// import Progress from "./CircleUpdating";

export default function UpdatePopup(props) {
    return(props.trigger) ? (
        <div className="popup">
            <div className="popup-inner">
                <button className="close-btn" onClick={() => props.triggerUpdatePopup(false)}>CANCEL</button>
                <CircularProgress></CircularProgress>
            </div>
        </div>
    ):"";
}