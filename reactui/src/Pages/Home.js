import "../App.css"
import React,{ Component } from "react";
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

            <br></br>
            <br></br>
            <br></br>
            <div>
                <br></br>
                <LanguageScaleTable/>
            </div>

        </>
    )

}

export default Home;