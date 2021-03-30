import React, {Component} from 'react'
import {HorizontalBar} from 'react-chartjs-2'
import CommitsPerDay from "./CommitsPerDay";
import SingleCommitDiff from "./CommitDiff"
import "./HBox.css"
import AllCommits from "./AllCommits";
import Button from "react-bootstrap/Button";
import Spinner from 'react-bootstrap/Spinner'
import moment from "moment";
import img from './ChartBackground.jpg'
import img2 from './LoadingBackground.jpg'

class CommitChart extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            commits: [],
            parentdata: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime,
            y_Axis : "1970-12-12",
            childVal : "non",
            diff: false,
            showAllCommit:true,
            allCommits: []
        };
        this.handler = this.handler.bind(this)
        this.handler2 = this.handler2.bind(this)
        this.handler3 = this.handler3.bind(this)
    }

    async handler(hash) {
        await this.setState({
            childVal: hash,
            diff : true,
        })
    }

    async handler2() {
        await this.setState({
            diff: false
        })
    }

    async handler3(commits) {
        await this.setState({
            allCommits: commits
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


        let url2 = '/api/v1/projects/' + projNum + '/Commits/' + username + '/' + startTm + "/" + endTm  + "/either"
        const result2 = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        const resp = await result2.json();
        await this.setState({commits:resp})
    }

    showComponents() {
        if(this.state.showAllCommit === true){
            if(this.state.commits.length === 0){
                return (
                    <div>
                    <br></br>

                <text style={{fontWeight: 'bold', fontSize: '20px', color: 'blue'}}>
                    Loading all commits....</text>
                        <Spinner
                            animation="border" variant="primary"
                        size="la"/>
                        </div>
                )
            }
            return (
                <div>
                    <AllCommits
                        devName = {this.props.devName}
                        startTime = {this.props.startTime}
                        endTime = {this.props.endTime}
                        handler = {this.handler}
                        handler3 = {this.handler3}
                        commits={this.state.commits} />
                </div>
            )
        }
        else {
            return (
                <div>
                    <CommitsPerDay
                        devName = {this.props.devName}
                        startTime = {this.state.y_Axis}
                        commits = {this.state.allCommits}
                        handler = {this.handler}
                        commits={this.state.commits} />
                </div>
            )
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

        if(this.state.data.length === 0){
            return (
                <div style={{ overflow: "scroll", minHeight: "1000px", width: "1000px", backgroundImage: `url()`}}>
                    <br></br>
                    <div>
                        {"      "}
                    </div>

                    <text style={{fontWeight: 'bold', fontSize: '20px', color: 'blue'}}>
                        Loading commits chart....</text>
                    <Spinner
                        animation="border" variant="primary"
                        size="la"/>
                </div>
            )
        }

        var comarr = this.state.data;
        const daylist = getDaysArray(new Date(this.props.startTime + "T12:00:00"),new Date(this.props.endTime+ "T12:00:00"));
        console.log(this.state.childVal)
        //height={daylist.length*(500/daylist.length)}
        return (
            <div className="box-container" >
                <div style={{ overflow: "scroll", minHeight: "1000px", width: "1000px"}}>
                    <Button
                            onClick={(e) => {
                                this.setState({showAllCommit: true, diff:false})
                            }}>
                        <span >show all commits</span>
                    </Button>
                <div className="horizontalBar" style={{backgroundImage: `url(${img})`,
                    backgroundSize: "cover"
                }}>
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
                                    this.setState({y_Axis:yAxis, diff : false, showAllCommit: false})
                                }

                            } , maintainAspectRatio:true,
                        scales: {
                                xAxes:[
                                    {
                                        ticks:{
                                            beginAtZero: true,
                                            stepSize: 2,
                                            fontColor: "blue",
                                            fontStyle	: 'bold'
                                        },  gridLines : {
                                            zeroLineColor:"black",
                                        }
                                    }
                                ],
                                yAxes: [{
                                    ticks : {
                                        fontColor: "blue",
                                        fontStyle	: 'bold'
                                    },gridLines : {
                                    }
                                }]
                            }
                        }}
                />
                    </div>
                </div>
                    {this.showComponents()}
                    {(this.state.diff !== false) ? <SingleCommitDiff
                        handler2 = {this.handler2}
                        hash = {this.state.childVal}
                        commits={this.state.commits}/> : <div> </div>}
                </div>
        )
    }
}

export default CommitChart