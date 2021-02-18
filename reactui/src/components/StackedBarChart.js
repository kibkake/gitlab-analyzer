import React, { PureComponent } from 'react';
import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import ProjectService from "../Service/ProjectService";

export default class StackedBarChart extends PureComponent {
    // static jsfiddleUrl = 'https://jsfiddle.net/alidingling/90v76x08/';
//    constructor(props) {
//        super(props);
//        this.
//    }

    state = {
        date: null, code:0, comment:0 };


    componentDidMount() {
        var path1 = window.location.pathname;
        var id = path1.split("/")[1];
        var developer = path1.split("/")[3];

        axios.get("http://localhost:8080/api/v1/projects/allCommits/"+repNum+"/"+name)
                .then(response=>{
                    const commits = response.data
                    this.setState({commits})
                })
            }



//        ProjectService.getCodeScore(this.id, this.developer).then((response) => {
//            this.setState({date: response.data.date, code: response.data.commitScore, comment: 0
//        });
    }

    render() {
        return (
            <BarChart
                width={1500}
                height={300}
                data={state}
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="code" stackId="a" fill="#8884d8" />
                <Bar dataKey="comment" stackId="a" fill="#82ca9d" />
            </BarChart>
        );
    }
}
