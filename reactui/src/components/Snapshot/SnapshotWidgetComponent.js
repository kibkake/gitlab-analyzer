import React, {Component, useState} from 'react';
import {BiSave, FcCheckmark} from "react-icons/all";
import SnapshotService from "../../Service/SnapshotService";

export default function SnapshotWidgetComponent(){

    const [saved, setSaved] = useState(false);

    const saveHandler = async () => {
        await SnapshotService.saveSnapshot(
            sessionStorage.getItem('user'),                 // current user
            sessionStorage.getItem('startdate'),            // chosen snapshot date
            sessionStorage.getItem('enddate'),              // "                  "
            window.location.pathname.split("/")[2],             // current repository
            sessionStorage.getItem('CurrentDeveloper'),    // current developer
            window.location.pathname.split("/")[5]             // current page
        );
        await setSaved(true);
        await delay(2000);
        await setSaved(false);
    }

    /*
     *   utility function for delay from SO:
     *
     *   https://stackoverflow.com/questions/14226803/wait-5-seconds-before-executing-next-line
     */
    const delay = ms => new Promise(res => setTimeout(res, ms));

    return(
        <>
            <button className="login snapshot-widget" onClick={()=> saveHandler()}> Save Snapshot <BiSave/></button>{saved && <h4 className="saved-icon"> Saved <FcCheckmark/> &emsp;</h4>}
        </>
    )
}