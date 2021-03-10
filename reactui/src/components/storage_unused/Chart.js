import React, {PureComponent} from 'react'
import { Bar} from 'react-chartjs-2'
import { Chart } from 'react-charts'
import axios from "axios";

// creating a stacked barchart for mr/commits
export default class BarChart extends PureComponent {

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

        //request ref: http://localhost:8080/api/v1/projects/6/MRsAndCommitScoresPerDay/user2/2021-01-01/2021-02-10
        axios.get("/api/v1/projects/" +id+ "/MRsAndCommitScoresPerDay/"+developer+"/2021-01-01/2021-02-10")
            .then(response => {
                const score = response.data
                this.setState({codeScore: score})
                console.log(this.state.codeScore);
            }).catch((error) => {
            console.error(error);
        });
    }

    render() {
        return <div>
            <Bar data={{
                //labels x for the axis
                labels: ['time', 'time', 'time', 'time', 'time'],
                //data displayed in graph
                datasets: [
                    {
                        label: 'commits',
                        data: [5, 3, 7, 8, 3],
                        backgroundColor: 'orange',
                        borderColor: 'black'
                    },
                    {
                        label: 'merge requests',
                        data: [7, 5, 9, 10, 6],
                        backgroundColor: 'cyan',
                        borderColor: 'black',
                    }
                ]
            }}
                 height={200}
                 width={200}
                 options={{
                     maintainAspectRatio: false,
                     scales: {
                         xAxes: [{
                             type: 'dateTime',
                             stacked: true
                         }],
                         yAxes: [
                             {
                                 stacked: true,
                                 ticks: {
                                     beginAtZero: true,
                                 }
                             }
                         ]
                     }
                 }}
            />
        </div>
    }
}
