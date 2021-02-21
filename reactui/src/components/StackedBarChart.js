import React, { PureComponent } from 'react';
import {BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer} from 'recharts';
import ProjectService from "../Service/ProjectService";
import axios from "axios";


const getDaysArray = function(start, end) {
    for(var arr=[],dt=new Date(start); dt<=end; dt.setDate(dt.getDate()+1)){

        var day3temp = new Date(dt).toLocaleDateString().split("-")[1];
        var month3temp = new Date(dt).toLocaleDateString().split("-")[0];
        var year3 = new Date(dt).toLocaleDateString().split("-")[2];
        var day3;
        var month3;
        if(day3temp < 10){
            day3 = '0' + day3temp;
        }else{
            day3 = day3temp;
        }
        if(month3temp < 10){
            month3 = '0' + month3temp;
        }else{
            month3 = month3temp;
        }

        var completeDate = year3 + "-" + month3 + "-" + day3;
        arr.push(completeDate);
        greenArr.push('rgba(123, 239, 178, 1)')
        blackArr.push('rgba(0, 0, 0, 0.5)')
    }
    return arr;
};


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
                <ResponsiveContainer width = '85%' height = {500} >
            <BarChart
                data={this.state.codeScore}
                margin={{
                    top: 20,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}
            >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date"/>
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
