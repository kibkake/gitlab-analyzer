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
            var developerName = str.split("/")[4];

            var dateStr1 = new Date("1-11-2021").toLocaleDateString();
            //month/day/2021
            //2/12/2021
            var day1 = dateStr1.split("/")[1];
            var month1 = dateStr1.split("/")[0];
            var year1 = dateStr1.split("/")[2];
            var completeFromDate = month1 + "-" + day1 + "-" + year1

            var dateStr2 = new Date("2-22-2021").toLocaleDateString();
            //month/day/2021
            //2/12/2021
            var day2 = dateStr2.split("/")[1];
            var month2 = dateStr2.split("/")[0];
            var year2 = dateStr2.split("/")[2];
            var completeToDate = month2 + "-" + day2 + "-" + year2
            var arr=[];


//http://localhost:8080/getuserstats/6/arahilin/1-11-2021/2-22-2021
            let url3 = 'http://localhost:8080/getuserstats/6/' + developerName + '/' + completeFromDate + "/" + completeToDate
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

    render() {

        var getDaysArray = function(start, end) {
            for(var arr=[],dt=new Date(start); dt<=end; dt.setDate(dt.getDate()+1)){

                arr.push(new Date(dt).toDateString());
            }
            return arr;
        };

        var d = '12/12/1955 12:00:00 AM';
        d = d.split(' ')[0];

        var comarr = this.state.data;
        const daylist = getDaysArray(new Date("2021-01-11"),new Date("2021-02-22"));

        //console.log(daylist);
        return (
            <div>
                <HorizontalBar

                    data={{labels: daylist,
                        datasets: [
                            {label: 'Number of Commits',
                                data: comarr
                            },
                        ],

                    }}
                    width={500}
                    height={daylist.length*10}


                    options={
                        {onClick: (e, elements) => {
                                e.preventDefault();
                                const chart = elements[0]._chart;
                                const element = chart.getElementAtEvent(e)[0];
                                const dataset = chart.data.datasets[element._datasetIndex];
                                const xLabel = chart.data.labels[element._index];
                                var month = xLabel.toString().split(" ")[1]
                                var day = xLabel.toString().split(" ")[2]
                                var year = xLabel.toString().split(" ")[3]

                                window.location.href=  window.location.pathname + "/commits/" + day + "/" + month + "/" + year;

                            } , maintainAspectRatio:true,
                            scales: {
                                xAxes:[
                                    {
                                        ticks:{
                                            beginAtZero: true,
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