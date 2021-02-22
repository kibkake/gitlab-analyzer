import React, {Component} from 'react';
import {BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer} from 'recharts';
import axios from "axios";
import * as d3 from "d3-time";
import moment from "moment";

const margin = {top: 20, right: 20, bottom: 30, left: 40};


class CommentChart extends Component {

    getInitialState() {
        return { width: 500 };
    }

    constructor() {
        super();
        this.state = {
            commentScore: []
        }
    }

    componentDidMount () {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];
        var developer = pathArray[4];

        //response ref: http://localhost:8090/api/v1/projects/6/allUserNotes/arahilin/2021-01-01/2021-02-22
        axios.get("/api/v1/projects/" + id + "/allUserNotes/"+ developer +"/2021-01-01/2021-02-22")
            .then(response => {
                const commentInfo = response.data
                console.log(commentInfo)
                this.setState({commentScore: commentInfo})
                console.log(this.state.commentScore)

            }).catch((error) => {
            console.error(error);
        });
    }

    handleBarClick(element, id){
        console.log(`The bin ${element.text} with id ${id} was clicked`);
    }

    render() {
        const output = this.state.commentScore.map(function(item) {
            return {
                date: (new Date(item.created_at)).getTime(),
                wordCount: item.wordCount
            };
        });
        console.log(output);
        const from = Number(new Date('2021-01-15'));
        const to = Number(new Date('2021-02-23'));

        return (
            <div>
                <ResponsiveContainer width = '100%' height = {500} >
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
                        <Bar dataKey="wordCount" stackId="a" fill="#8884d8" />
                    </BarChart>
                </ResponsiveContainer>
            </div>
        );
    }
        // return (
        //     <div ref='root'>
        //         <div style={{width: '95%'}}>
        //             <BarChart ylabel='Score'
        //                       width={1500}
        //                       height={200}
        //                       margin={margin}
        //                       data={output}
        //                       color="#ffe0b2"
        //                       onBarClick={this.handleBarClick}/>
        //
        //         </div>
        //     </div>
        // );
}

export default CommentChart