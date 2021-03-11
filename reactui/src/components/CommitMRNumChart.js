import React, { PureComponent } from 'react';
import {BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer} from 'recharts';
import axios from "axios";
import * as d3 from "d3-time";
import moment from 'moment'
import ProjectService from "../Service/ProjectService";

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
//        ProjectService.getCodeScore(this.id, this.developer).then((response) => {
//            this.setState({date: response.data.date, code: response.data.commitScore, comment: 0
//        });

    render() {
        var output = this.state.frequency.map(function(item) {
            return {
                date: (new Date(item.date)).getTime(), //item.date,
                commitNum: item.numCommits,
                mergeNum: item.numMergeRequests
            };
        });
        console.log(output);
        const from = Number(new Date( this.props.startTime));
        const to = Number(new Date(this.props.endTime));

        return (
            <div>
                <ResponsiveContainer width = '100%' height = {500} >
                    <BarChart
                        data={output}
                        margin={{ top: 20, right: 30, left: 20, bottom: 5,}}
                    >
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey= "date"
                               type ="number"
                               name = 'date'
                               domain={[
                                   d3.timeDay.ceil(from).getTime(),
                                   d3.timeDay.ceil(to).getTime()
                               ]}
                               tickFormatter = {(unixTime) => moment(unixTime).format('YYYY-MM-DD')}
                        />
                        <YAxis />
                        <Tooltip />
                        <Legend />
                        <Bar dataKey="commitNum" stackId="a" fill="orange" />
                        <Bar dataKey="mergeNum" stackId="a" fill="#82ca9d" />
                    </BarChart>
                </ResponsiveContainer>
            </div>
        );
    }
}