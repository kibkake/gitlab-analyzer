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
            parentdata: this.props.devName
        }
    }

    componentDidMount(){
        const {parentdata} = this.state;
        this.getDataFromBackend(parentdata)
    }

    getDataFromBackend (username) {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];

        //request ref: http://localhost:8090/api/v1/projects/6/MRsAndCommitScoresPerDay/user2/2021-01-01/2021-02-23
        axios.get("/api/v1/projects/" + id + "/MRsAndCommitScoresPerDay/" + username + "/2021-01-01/2021-02-28")
            .then(response => {
                const nums = response.data
                this.setState({frequency : nums})
                console.log(this.state.frequency)
            }).catch((error) => {
            console.error(error);
        });

    }

    componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName){
            this.setState({parentdata: this.props.devName});
            this.getDataFromBackend(this.props.devName)
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
        const from = Number(new Date('2021-01-15'));
        const to = Number(new Date('2021-02-28'));

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
                                   d3.timeDay.floor(from).getTime(),
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