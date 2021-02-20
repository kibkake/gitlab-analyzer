import React, {Component} from 'react'
import {HorizontalBar} from 'react-chartjs-2'
import Button from "react-bootstrap/Button";

class CommitChart extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        };
    }

    async componentDidMount() {

            var str = window.location.pathname;
            var projNum = str.split("/")[2];
            var developerName = str.split("/")[4];

            var dateStr1 = new Date("1-11-2021").toLocaleDateString();
            //month/day/2021
            //2/12/2021
            var day1temp = dateStr1.split("/")[1];
            var month1temp = dateStr1.split("/")[0];
            var year1 = dateStr1.split("/")[2];
            var day1;
            var month1;
            if(day1temp < 10){
                day1 = '0' + day1temp;
            }else{
                day1 = day1temp;
            }
            if(month1temp < 10){
                month1 = '0' + month1temp;
            }else{
                month1 = month1temp;
            }

            var completeFromDate = year1 + "-" + month1 + "-" + day1;

            var dateStr2 = new Date("2-22-2021").toLocaleDateString();
            //month/day/2021
            //2/12/2021
            var day2temp = dateStr2.split("/")[1];
            var month2temp = dateStr2.split("/")[0];
            var year2 = dateStr2.split("/")[2];
            var day2;
            var month2;
            if(day2temp < 10){
                day2 = '0' + day2temp;
            }else{
                day2 = day2temp;
            }
            if(month2temp < 10){
                month2 = '0' + month2temp;
            }else{
                month2 = month2temp;
            }

            var completeToDate = year2 + "-" + month2 + "-" + day2;
            var arr=[];

//http://localhost:8090/getuserstats/6/arahilin/1-11-2021/2-22-2021
            let url3 = ' http://localhost:8090/api/v1/projects/' + projNum + '/Commitsarray/' + developerName + '/' +
                completeFromDate + "/" + completeToDate
            const result = await fetch(url3, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
            const json = await result.json();
            var data = JSON.stringify(json);
            var DataArray = JSON.parse(data);
            await DataArray.map(item => {
                arr.push(item)
            })
        console.log(arr)

       this.setState({data: arr})
    }
//1/17/2021
    render() {
        var greenArr = [];
        var blackArr = [];
        var getDaysArray = function(start, end) {
            for(var arr=[],dt=new Date(start); dt<=end; dt.setDate(dt.getDate()+1)){

                var day3temp = new Date(dt).toLocaleDateString().split("/")[1];
                var month3temp = new Date(dt).toLocaleDateString().split("/")[0];
                var year3 = new Date(dt).toLocaleDateString().split("/")[2];
                var day3;
                var month3;
                if(day3temp < 10){
                    day3 = '0' + day3temp;
                }else{
                    day3 = day3temp;
                }
                if(month3temp < 10){
                    month3 = '0' + month3temp;
                }else{
                    month3 = month3temp;
                }

                var completeDate = year3 + "-" + month3 + "-" + day3;
                arr.push(completeDate);
                greenArr.push('rgba(123, 239, 178, 1)')
                blackArr.push('rgba(0, 0, 0, 0.5)')
            }
            return arr;
        };

        var d = '12/12/1955 12:00:00 AM';
        d = d.split(' ')[0];

        var comarr = this.state.data;
        const daylist = getDaysArray(new Date("01-11-2021"),new Date("02-22-2021"));

        //console.log(daylist);

        return (
            <div>
                <HorizontalBar

                    data={{labels: daylist,
                        datasets: [
                            {label: 'Number of Commits',
                                data: comarr, backgroundColor: greenArr,borderWidth: 2,  borderColor:blackArr,
                            },
                        ],

                    }}
                    width={500}
                    height={daylist.length*10}


                    options={
                        {onClick: (e, Event) => {
                                if (Event[0] != null) {
                                    e.preventDefault();
                                    const chart = Event[0]._chart;
                                    const element = chart.getElementAtEvent(e)[0];
                                    const yAxis = chart.data.labels[element._index];
                                    var month = yAxis.toString().split(" ")[1]
                                    var day = yAxis.toString().split(" ")[2]
                                    var year = yAxis.toString().split(" ")[3]
                                    window.location.href = window.location.pathname + "/" + yAxis;
                                }

                            } , maintainAspectRatio:true,
                            scales: {
                                xAxes:[
                                    {
                                        ticks:{
                                            beginAtZero: true,
                                            stepSize: 1,
                                        },
                                    }
                                ]
                            }
                        }}
                />
            </div>
        )
    }
}

export default CommitChart