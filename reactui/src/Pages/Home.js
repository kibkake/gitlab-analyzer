import "../App.css"
import React,{ Component } from "react";
import ProfileComponent from "../components/ProfileComponent";
import HomeInfo from "../components/HomeInfo";
import DateRangeSettingComponent from "../components/DateRangeSettings";

function Home(){

    return(   
        <>
            <div>
                <HomeInfo/>
            </div>

            <div className='dateRangeSetting'>
                <DateRangeSettingComponent/>
            </div>

        </>
    )

}

export default Home;