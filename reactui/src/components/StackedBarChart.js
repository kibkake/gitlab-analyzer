import React, { PureComponent } from 'react';
import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import ProjectService from "../Service/ProjectService";
import axios from "axios";

//'https://jsfiddle.net/alidingling/90v76x08/']
export default class StackedBarChart extends PureComponent {

    constructor() {
        super();
        this.state = {
            codeScore:[]
        }
    }

    componentDidMount(){
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];
        var developer = pathArray[4];

        //request ref: http://localhost:8090/api/v1/projects/6/MRsAndCommitScoresPerDay/user2/2021-01-01/2021-02-10
        axios.get("/api/v1/projects/" +id+ "/MRsAndCommitScoresPerDay/"+developer+"/2021-01-01/2021-02-10")
            .then(response => {
                const score = response.data
                console.log(score);
                this.setState({codeScore: score})
                console.log(this.state.codeScore);
            }).catch((error) => {
                    console.error(error);
            });

    }
//        ProjectService.getCodeScore(this.id, this.developer).then((response) => {
//            this.setState({date: response.data.date, code: response.data.commitScore, comment: 0
//        });

    render() {
        return (
            <div>
                {/*<ResponsiveContainer width = '95%' height = {500} >*/}
            <BarChart
                width={1500}
                height={300}

                data={this.state.codeScore}
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" type="date"/>
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="commitScore" stackId="a" fill="#8884d8" />
                <Bar dataKey="mergeRequestScore" stackId="a" fill="#82ca9d" />
            </BarChart>
                {/*</ResponsiveContainer>*/}
            </div>
        );
    }
}
