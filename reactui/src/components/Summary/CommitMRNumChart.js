import React, { PureComponent } from 'react';
import {BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, ReferenceLine} from 'recharts';
import axios from "axios";
import * as d3 from "d3-time";
import moment from 'moment'
import './ToolTip.css'
import SummaryChartFunctions from "./SummaryChartFunctions";

//'https://jsfiddle.net/alidingling/90v76x08/']
export default class CommitMRNumChart extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            frequency:[{date: null, numCommits: 0, numMergeRequest: 0}],
            parentdata: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime
        }
    }

    componentDidMount(){
        const {parentdata} = this.state;
        this.getDataFromBackend(parentdata, this.props.startTime,  this.props.endTime )
    }

    async getDataFromBackend (username, startTm, endTm) {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];

        //request ref: http://localhost:8090/api/v1/projects/6/numCommitsMerge/user2/2021-01-01/2021-02-23
        await axios.get("/api/v1/projects/" + id + "/MRsAndCommitScoresPerDay/" + username + '/' +
            startTm + '/' +
            endTm + "/either")
            .then(response => {
                const nums = response.data
                this.setState({frequency : nums, parentdata: username,startTime: startTm,
                    endTime: endTm})
                console.log(this.state.frequency)
            }).catch((error) => {
            console.error(error);
        });

    }

    async componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
        }
    }

    CustomToolTip = ({ active, payload, label }) => {
        if (active && payload && payload.length) {
            const commitVal = Math.abs(Math.round(payload[0].value * 10)/10.0);
            console.log(commitVal)
            const mrVal = Math.abs(Math.round(payload[1].value * 10)/10.0);
            console.log(mrVal)

            return (
                <div className="tooltipBox">
                    <p className="label">Date: {moment(label).format('YYYY-MM-DD')}</p>
                    <p className="label1">{`${'number of MRs'}: ${mrVal}`}</p>
                    <p className="label2">{`${'number of commits'}: ${commitVal}`}</p>
                </div>
            );
        }
        return null;
    };

    render() {
        var output = this.state.frequency.map(function(item) {
            return {
                date: (new Date(item.date)).getTime(), //item.date,
                commitNum: -item.numCommits,
                mergeNum: +item.numMergeRequests
            };
        });
        console.log(output);
        const from = Number(new Date( this.props.startTime));
        const to = Number(new Date(this.props.endTime));

        return (
            <div>
                <ResponsiveContainer width = '94%' height = {680} >
                    <BarChart
                        data={output}
                        stackOffset="sign"
                        margin={{ top: 20, right: 30, left: 20, bottom: 5,}}
                        >
                        <CartesianGrid
                            strokeDasharray="3 1"
                            vertical={false}
                        />
                        <XAxis
                            domain={[
                            d3.timeDay.ceil(from).getTime(),
                            d3.timeDay.ceil(to).getTime()]}
                              tickFormatter = {SummaryChartFunctions.formatDate}
                              name = 'date'
                              dataKey= "date"
                            type = "number"
                            allowDataOverflow={false}
                            tickSize={10}
                            hide={false}
                            angle={0}
                            interval={"preserveStartEnd"}
                            tickCount={SummaryChartFunctions.getTickCount(to, from)}
                            mirror={false}
                        />
                        <ReferenceLine
                            y={0}
                            stroke="#000000"
                            type='category'
                        />
                        <YAxis
                            tickFormatter = {(value) =>  Math.abs(value)}
                            tickSize={10}
                            interval={0}
                            angle={0}
                            allowDecimal={false}
                        />
                        <Tooltip
                            cursor={false}
                            content={this.CustomToolTip}
                        />
                        <Legend />
                        <Bar dataKey="commitNum" stackId="a" fill="red" />
                        <Bar dataKey="mergeNum" stackId="a" fill="blue" />
                    </BarChart>
                </ResponsiveContainer>
            </div>
        );
    }
}