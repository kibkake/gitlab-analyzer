import React, {Component} from 'react'
import {HorizontalBar} from 'react-chartjs-2'
import CommitsPerDay from "./CommitsPerDay";
import SingleCommitDiff from "../Developers/SingleCommitDiffs"
import "./HBox.css"
import CommitMergeRequest from "./CommitMergeRequest";
import ProjectService from "../../Service/ProjectService";


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

    async handler(hash) {
        await this.setState({
            childVal: hash,
            diff : true
        })
    }

    async handler2() {
        await this.setState({
            diff: false
        })
    }

    async componentDidMount() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var devId = str.split("/")[4];
        const {parentdata} = this.state;
        if(sessionStorage.getItem("CurrentDeveloper") == null) {
            await ProjectService.getCurDevInfo(repNum, devId)

        }
        await this.getDataFromBackend(parentdata, this.props.startTime,  this.props.endTime )
    }

    async getDataFromBackend (username, startTm, endTm) {

        var str = window.location.pathname;
        var projNum = str.split("/")[2];
        var arr=[];

        let url3 = '/api/v1/projects/' + projNum + '/Commitsarray/' + username + '/' +
            startTm + "/" + endTm + "/either"
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
        await this.setState({data: arr})
    }

    async componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
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
        console.log(this.state.childVal)

        return (
            <div className="box-container" >
                <div className="horizontalBar" style={{ overflow: "scroll", height: "1050px", width: "1000px"}}>
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
                {(this.state.diff !== false) ? <SingleCommitDiff  handler2 = {this.handler2} hash = {this.state.childVal}/> : <CommitsPerDay devName = {this.props.devName} startTime = {this.state.y_Axis} handler = {this.handler} />}
                {(this.state.diff !== false) ? <CommitMergeRequest hash = {this.state.childVal}/>: <div> </div>}
            </div>
        )
    }
}

export default CommitChart