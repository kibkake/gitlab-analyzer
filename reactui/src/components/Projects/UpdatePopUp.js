import React from "react";
import './Popup.css'
import {CircularProgress} from "@material-ui/core";

export default function UpdatePopup(props) {
    return(props.trigger) ? (
        <div className="popup">
            <div className="popup-inner">
                <CircularProgress> </CircularProgress>
                <br></br>
                <h4>Updating...</h4>
                {/*<button className="close-btn" onClick={() => props.setTrigger(false)}>CANCEL</button>*/}
            </div>
        </div>
    ):"";
}