import React, { PureComponent } from 'react';
import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import ProjectService from "../Service/ProjectService";


const data = [
    {

    }
    {
        name: 'Page A',
        uv: 4000,
        pv: 2400,
        amt: 2400,
    },
    {
        name: 'Page B',
        uv: 3000,
        pv: 1398,
        amt: 2210,
    },
    {
        name: 'Page C',
        uv: 2000,
        pv: 9800,
        amt: 2290,
    },
    {
        name: 'Page D',
        uv: 2780,
        pv: 3908,
        amt: 2000,
    },
    {
        name: 'Page E',
        uv: 1890,
        pv: 4800,
        amt: 2181,
    },
    {
        name: 'Page F',
        uv: 2390,
        pv: 3800,
        amt: 2500,
    },
    {
        name: 'Page G',
        uv: 3490,
        pv: 4300,
        amt: 2100,
    },
];

export default class StackedBarChart extends PureComponent {

    constructor(props) {
        super();
        this.state = {
            codeScore: []
        }
    }

    var str = window.location.pathname;
    var repNum = str.split("/")[2];
    let url2 = 'http://localhost:8080/getprojectmembers/' + repNum

    componentDidMount() {
        ProjectService.getCodeScore(6, ara)
    }

    static jsfiddleUrl = 'https://jsfiddle.net/alidingling/90v76x08/';

    render() {
        return (
            <BarChart
                width={1500}
                height={300}
                data={data}
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
