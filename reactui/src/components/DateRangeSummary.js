
function DateRangeSummary(){

    function getInitialStartDate() {

        if(sessionStorage.getItem("startdate") != null){
            return new Date (sessionStorage.getItem("startdate"))
        }
        else if(localStorage.getItem("startdate") != null){
            return new Date (localStorage.getItem("startdate"))
        }
        else {
            return new Date ("2021-01-11")
        }
    }

    function getInitialEndDate() {

        if(sessionStorage.getItem("enddate") != null){
            return new Date (sessionStorage.getItem("startdate"))
        }
        else if(localStorage.getItem("enddate") != null){
            return new Date (localStorage.getItem("startdate"))
        }
        else {
            return new Date ("2021-02-22")
        }
    }

   /* const [selectedStartDate,setSelectedStartDate] = React.useState(
        new Date(sessionStorage.getItem('startdate') + 'T12:00:00')
    )

    const [selectedEndDate,setSelectedEndDate] = React.useState(
        new Date(sessionStorage.getItem('enddate') + 'T12:00:00')
    )*/


    return(
        <>
        </>

    )
}
export default DateRangeSummary;
