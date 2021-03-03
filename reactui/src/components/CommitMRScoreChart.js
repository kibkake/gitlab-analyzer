import React, { PureComponent } from 'react';
import {BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer} from 'recharts';
import axios from "axios";
import * as d3 from "d3-time";
import moment from 'moment'
import ProjectService from "../Service/ProjectService";

//'https://jsfiddle.net/alidingling/90v76x08/']
export default class CommitMRScoreChart extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            codeScore:[{date: null, commitScore: 0, mergeRequestScore: 0}],
            parentdata: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime
        }
    }

    async componentDidMount(){
        const {parentdata} = this.state;
        await this.getDataFromBackend(parentdata, this.props.startTime,  this.props.endTime )
    }

    async getDataFromBackend (username, startTm, endTm) {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];

        await axios.get("/api/v1/projects/" + id + "/MRsAndCommitScoresPerDay/" + username + '/' +
            startTm + '/' +
            endTm)
            .then(response => {
                const score = response.data
                this.setState({codeScore : score})
                console.log(this.state.codeScore)
            }).catch((error) => {
            console.error(error);
        });
    }

    async componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName){
            await this.setState({parentdata: this.props.devName});
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
        }
        if(this.props.startTime !== prevProps.startTime){
            await this.setState({startTime: this.props.startTime});
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
        }
        if(this.props.endTime !== prevProps.endTime){
            await this.setState({endTime: this.props.endTime});
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime)
        }
    }

//        ProjectService.getCodeScore(this.id, this.developer).then((response) => {
//            this.setState({date: response.data.date, code: response.data.commitScore, comment: 0
//        });

    render() {
        var output = this.state.codeScore.map(function(item) {
            return {
                date: (new Date(item.date)).getTime(), //item.date,
                commitScore: item.commitScore,
                mergeScore: item.mergeRequestScore
            };
        });
        console.log(output);


        const from = Number(new Date(this.props.startTime));
        const to = Number(new Date(this.props.endTime));
//ceil
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
                        <Bar dataKey="commitScore" stackId="a" fill="orange" />
                        <Bar dataKey="mergeScore" stackId="a" fill="#82ca9d" />
                    </BarChart>
                </ResponsiveContainer>
            </div>
        );
    }
}