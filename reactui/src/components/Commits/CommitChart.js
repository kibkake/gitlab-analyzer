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
import SnapshotWidgetComponent from "../Snapshot/SnapshotWidgetComponent";

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
            allCommits: [],
            totalScore: 0.0,
            singleCommitScore: 0.0
        };
        this.handler = this.handler.bind(this)
        this.handler2 = this.handler2.bind(this)
        this.handler3 = this.handler3.bind(this)
        this.addExcludedPoints = this.addExcludedPoints.bind(this)
        this.resetSingleCommitScore = this.resetSingleCommitScore.bind(this)
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

    async addExcludedPoints(score){
        console.log(score)
        var commitScore1 = this.state.singleCommitScore
        console.log(commitScore1)
        commitScore1 += score
        console.log(commitScore1)
        this.setState({singleCommitScore:commitScore1})
    }

    async resetSingleCommitScore(){
        this.setState({singleCommitScore:0.0})
    }

    async componentDidMount() {
        const {parentdata} = this.state;
        await this.getDataFromBackend(parentdata, this.props.startTime,  this.props.endTime )
    }

    async getDataFromBackend (username, startTm, endTm) {
        var str = window.location.pathname;
        var projNum = str.split("/")[2];

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

        var chartArr = [];
        var index = 0;

        for(var arr=[],dt=new Date(moment(startTm)); dt<=new Date(moment(endTm)); dt.setDate(dt.getDate()+1)) {
            chartArr.push(0)
        }

        var totalScore1 = 0.0;

        for(var arr=[],dt=new Date(moment(startTm)); dt<=new Date(moment(endTm)); dt.setDate(dt.getDate()+1)){

            var time = moment(dt).format('L')
            const month = time.substring(0,2)
            const day = time.substring(3,5)
            const year = time.substring(6,10)
            const fullTime = year + '-' + month + '-' + day

            await this.state.commits.map(function(item) {

                var time1 = moment(item.committed_date).format('L')
                const tempMonth = time1.substring(0,2)
                const tempDay = time1.substring(3,5)
                const tempYear = time1.substring(6,10)
                const tempFullTime = tempYear + '-' + tempMonth + '-' + tempDay

                if(fullTime === tempFullTime){
                    totalScore1 += item.commitScore
                    chartArr[index]++
                }
            })
            index++
        }
        await this.setState({data:chartArr, totalScore: totalScore1})
        await this.setState({commits:resp});
        this.applyMultipliers()
    }

    applyMultipliers(){
        var scale = JSON.parse(sessionStorage.getItem('languageScale'));
        var newCommits= [...this.state.commits];
        console.log(newCommits);
        for(const k in newCommits){
            var newCommitsScore=0;
            for(var i in newCommits[k].diffs){
                var fileExtension = newCommits[k].diffs[i].new_path.split(".").pop();
                const extensionIndex = scale.findIndex(scale => scale.extension === fileExtension);
                if(extensionIndex!==-1){
                    var newScore = scale[extensionIndex].multiplier * newCommits[k].diffs[i].diffScore;
                    newCommits[k].diffs[i] = {...newCommits[k].diffs[i], diffScore:newScore};
                    newCommitsScore = newCommitsScore+newScore;
                }else{
                    const defaultIndex = scale.findIndex(scale => scale.name==='Default');
                    var newScore = scale[defaultIndex].multiplier * newCommits[k].diffs[i].diffScore;
                    newCommits[k].diffs[i] = {...newCommits[k].diffs[i], diffScore:newScore};
                    newCommitsScore = newCommitsScore+newScore;
                }
            }
            newCommits[k].commitScore=newCommitsScore;
        }


        this.setState({
            data:newCommits,
        })
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
                        totalScore = {this.state.totalScore}
                        devName = {this.props.devName}
                        startTime = {this.props.startTime}
                        endTime = {this.props.endTime}
                        handler = {this.handler}
                        handler3 = {this.handler3}
                        commits={this.state.commits}
                        resetSingleCommitScore = {this.resetSingleCommitScore}/>
                </div>
            )
        }
        else {
            return (
                <div>
                    <CommitsPerDay
                        devName = {this.props.devName}
                        totalScore = {this.state.totalScore}
                        startTime = {this.state.y_Axis}
                        handler = {this.handler}
                        commits={this.state.commits}
                        resetSingleCommitScore = {this.resetSingleCommitScore}/>
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
                let currentYear = moment().format("YYYY");
                var completeDate;

                if(year3 === currentYear){
                    completeDate = month3 + "-" + day3;
                }else{
                    completeDate = year3 + "-" + month3 + "-" + day3;
                }
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
        const daylist = getDaysArray(new Date(this.props.startTime ),new Date(this.props.endTime));
        console.log(this.state.childVal)
        //height={daylist.length*(500/daylist.length)}
        return (
            <div>

            <div className="box-container" >
                <div
                    style={{ overflow: "scroll", minHeight: "1000px", width: "1000px"}}>
                    <div
                        style={{flexDirection: "row",display: "flex"}}>
                    <Button
                            onClick={(e) => {
                                this.setState({showAllCommit: true, diff:false})
                            }}>
                        <span >show all commits</span>
                    </Button>
                        <SnapshotWidgetComponent/>
                    </div>
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
                                    var yAxis = chart.data.labels[element._index];
                                    {console.log("yAxis",yAxis)}
                                    {yAxis.length < 6 ? yAxis = moment().format("YYYY")
                                        + "-" + yAxis : console.log('not current year')}
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
                                        fontStyle	: 'bold',
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
                        addExcludedPoints = {this.addExcludedPoints}
                        handler2 = {this.handler2}
                        hash = {this.state.childVal}
                        commits={this.state.commits}
                        singleCommitScore={this.state.singleCommitScore}
                        /> : <div> </div>}
                </div>
            </div>
        )
    }
}

export default CommitChart