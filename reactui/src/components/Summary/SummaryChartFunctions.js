import moment from "moment";
import React from "react";


class SummaryChartFunctions{

    getTickCount(to, from){
        const diff = (to-from)/1000000000
        if((Math.round((diff*10)/10)) < 1){
            return 3
        }
        else if((Math.round((diff*10)/10)) < 10){
            return 15
        }
        else {
            return 20
        }
    }
    //less ticks
    getTickCount2(to, from){
        const diff = (to-from)/1000000000
        if((Math.round((diff*10)/10)) < 1){
            return 3
        }
        else if((Math.round((diff*10)/10)) < 10){
            return 10
        }
        else {
            return 15
        }
    }

    getIntervalNumber(to, from){
        const diff = (to-from)/1000000000
        if((Math.round((diff*10)/10))*2 < 5){
            return 1
        }
        else {
            return ((Math.round((diff * 10) / 10))*5)
        }
    }

    formatDateWithYear = (unixTime) =>{
        return moment(unixTime).format('YYYY-MM-DD')
    }

    formatDate = (unixTime) =>{
        if(moment(unixTime).format('YYYY') === '2021'){
            return moment(unixTime).format('MM-DD')
        }
        else {
            return moment(unixTime).format('YYYY-MM-DD')
        }
    }
}

export default new SummaryChartFunctions();