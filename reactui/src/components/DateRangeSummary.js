
function DateRangeSummary(){

    const [selectedStartDate,setSelectedStartDate] = React.useState(
        new Date(sessionStorage.getItem('startdate') + 'T12:00:00')
    )

    const [selectedEndDate,setSelectedEndDate] = React.useState(
        new Date(sessionStorage.getItem('enddate') + 'T12:00:00')
    )


    return(

    )
}
export default DateRangeSummary;
