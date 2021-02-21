import React, { PureComponent } from 'react';
import {BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer} from 'recharts';
import ProjectService from "../Service/ProjectService";
import axios from "axios";
import * as d3 from "d3-time";
import moment from 'moment'
import { extent as d3Extent, max as d3Max } from 'd3-array';
import { scaleLinear as d3ScaleLinear, scaleTime as d3ScaleTime} from 'd3-scale';
import { format as d3Format } from 'd3-format';

//'https://jsfiddle.net/alidingling/90v76x08/']
export default class StackedBarChart extends PureComponent {

    constructor() {
        super();
        this.state = {
            codeScore:[{date: null, commitScore: 0, mergeRequestScore: 0}]
        }
    }

    componentDidMount(){
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];
        var developer = pathArray[4];

        //request ref: http://localhost:8090/api/v1/projects/6/MRsAndCommitScoresPerDay/user2/2021-01-01/2021-02-23
        axios.get("/api/v1/projects/" + id + "/MRsAndCommitScoresPerDay/" + developer + "/2021-01-01/2021-02-23")
            .then(response => {
                const score = response.data
                console.log(score)
                this.setState({codeScore : score})
                console.log(this.state.codeScore)
            }).catch((error) => {
                    console.error(error);
            });

    }
//        ProjectService.getCodeScore(this.id, this.developer).then((response) => {
//            this.setState({date: response.data.date, code: response.data.commitScore, comment: 0
//        });

    render() {
        var output = this.state.codeScore.map(function(item) {
            return {
                date: Number(new Date(item.date)),
                commitScore: item.commitScore,
                mergeScore: item.mergeRequestScore
            };
        });
        console.log(output);
        const from = Number(new Date('2021-01-01'));
        const to = Number(new Date('2021-02-23'));

        //
        // const domain = d3Extent (output.date, d=>new Date(d.start));
        // const tScale = d3ScaleTime().domain(domain).range([0, 1]);
        // const tickFormat = tScale.tickFormat();

        return (
            <div>
                <ResponsiveContainer width = '85%' height = {500} >
                    <BarChart
                        data={output}
                        margin={{
                            top: 20,
                            right: 30,
                            left: 20,
                            bottom: 5,
                        }}
                    >
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey= "date"
                               type ="number"
                               // scale="day"
                            // domain = {['auto', 'auto']}
                               name = 'date'
                               tickFormatter = {(unixTime) => moment(unixTime).format('YYYY-MM-DD')}
                               domain={[
                                   d3.timeDay.floor(from).getTime(),
                                   d3.timeDay.ceil(to).getTime()
                               ]}
                            />
                        <YAxis />
                        <Tooltip />
                        <Legend />
                        <Bar dataKey="commitScore" stackId="a" fill="orange" />
                        <Bar dataKey="mergeRequestScore" stackId="a" fill="#82ca9d" />
                    </BarChart>
                </ResponsiveContainer>
            </div>
        );
    }
}
