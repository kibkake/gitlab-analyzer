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

        //request ref: http://localhost:8080/api/v1/projects/6/MRsAndCommitScoresPerDay/user2/2021-01-01/2021-02-22
        axios.get("http://localhost:8080/api/v1/projects/" +id+ "/MRsAndCommitScoresPerDay/"+developer+"/2021-01-01/2021-02-22")
            .then(response => {
                const score = response.data
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
        return <div>
        <BarChart
                width={1500}
                height={300}
                // data={this.state.codeScore}
                data = {{
                    labels: 'dateTime',
                    //data displayed in graph
                    datasets: [
                {
                    label: 'commitScore',
                    data: [5, 3, 7, 8, 3],
                    backgroundColor: 'orange',
                    borderColor: 'black'
                },
                {
                    label: 'mergeRequestScore',
                    data: [7, 5, 9, 10, 6],
                    backgroundColor: 'cyan',
                    borderColor: 'black',
                }
                ]
                }}
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
                options={{
                    scales: {
                        xAxes: [{
                            type: 'date',
                                time: {
                                    unit: 'day'
                                }
                            }]
                    }
                }}
            >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="commitScore" stackId="a" fill="orange" />
                <Bar dataKey="mergeRequestScore" stackId="a" fill="cyan" />
            </BarChart>
        </div>
    }
}
