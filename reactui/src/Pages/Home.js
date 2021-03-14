import "../App.css"
import React,{ Component } from "react";
import ProfileComponent from "../components/ProfileComponent";
import HomeInfo from "../components/HomeInfo";
import DateRangeSettingComponent from "../components/DateRangeSettings";
import LanguageScaleTable from "../components/LanguageScaleTable";


function Home(){

    return(   
        <>
            <div>
                <HomeInfo/>
            </div>

            <div className='dateRangeSetting'>
                <DateRangeSettingComponent/>
            </div>

            <div>
                <LanguageScaleTable/>
            </div>

        </>
    )

}

export default Home;