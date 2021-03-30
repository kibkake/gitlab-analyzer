import "../App.css"
import { MuiPickersUtilsProvider,DateTimePicker } from '@material-ui/pickers';
import React,{ Component } from "react";
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import DateRangeSettingComponent from "../components/DateSetting/DateRangeSettings";
import LanguageScaleTable from "../components/LanguageScaler/LanguageScaleTable";
import SnapshotComponent from "../components/Snapshot/SnapshotComponent";

export default function Settings(){

    return(
        <>
            <h2> Settings </h2>
            <br/>
            <SnapshotComponent/>
            <br/>
            <br/>
            <div className='dateRangeSetting'>
                <DateRangeSettingComponent/>
            </div>

            <br/>
            <br/>
            <br/>
            <div>
                <br/>
                <LanguageScaleTable/>
            </div>
        </>
    )
}