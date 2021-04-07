import "../App.css"
import React,{ Component } from "react";
import HomeInfo from "../components/HomeInfo";
import DateRangeSettingComponent from "../components/DateSetting/DateRangeSettings";
import LanguageScaleTable from "../components/LanguageScaler/LanguageScaleTable";


function Home(){

    return(   
        <>
            <div>
                <HomeInfo/>
            </div>

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

export default Home;