import React, {Component} from 'react'
import {HorizontalBar} from 'react-chartjs-2'
import Button from "react-bootstrap/Button";
import axios from "axios";
import "./HBox.css"
import CommitsPerDay from "./CommitsPerDay";
import SingleCommitDiff from "./SingleCommitDiffs"

class CommitChart extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            parentdata: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime,
            y_Axis : "1970-12-12",
            childVal : "non",
            diff: false,
        };
        this.handler = this.handler.bind(this)
        this.handler2 = this.handler2.bind(this)

    }

    handler(hash) {
        this.setState({
            childVal: hash,
            diff : true
        })
    }

    handler2() {
        this.setState({
            diff: false
        })
    }

    async componentDidMount() {
        const {parentdata} = this.state;
        await this.getDataFromBackend(parentdata, this.props.startTime,  this.props.endTime )
    }

    async getDataFromBackend (username, startTm, endTm) {

        var str = window.location.pathname;
        var projNum = str.split("/")[2];
        var arr=[];

        let url3 = '/api/v1/projects/' + projNum + '/Commitsarray/' + username + '/' +
            startTm + "/" + endTm
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
        this.setState({data: arr})
    }

    componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName){
            this.setState({parentdata: this.props.devName});
            this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
        }
        if(this.props.startTime !== prevProps.startTime){
            this.setState({startTime: this.props.startTime});
            this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )

        }
        if(this.props.endTime !== prevProps.endTime){
            this.setState({endTime: this.props.endTime});
            this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime)
        }
    }

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

        var comarr = this.state.data;
        const daylist = getDaysArray(new Date(this.props.startTime + "T12:00:00"),new Date(this.props.endTime+ "T12:00:00"));
        var location = window.location.pathname.split("/")
        console.log(this.state.childVal)

        return (
            <div className="box-container" >
                {console.log("are we looking at diff?", this.state.diff)}
                <div className="horizontalBar">
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
                                    this.setState({y_Axis:yAxis, diff : false})
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
                {(this.state.diff !== false) ? <SingleCommitDiff handler2 = {this.handler2} hash = {this.state.childVal}/> : <CommitsPerDay devName = {this.props.devName} startTime = {this.state.y_Axis} handler = {this.handler} />}
            </div>
        )
    }
}

export default CommitChart

/*to swtich to a different page
   window.location.href =  '/' + location[1] +'/' + location[2] +
   '/' + location[3] + '/' + this.props.devName + '/commits/' +
   yAxis
 */