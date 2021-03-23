import React, {PureComponent} from "react";
import axios from "axios";
import {BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, ReferenceLine} from 'recharts';
import * as d3 from "d3-time";
import moment from "moment";
import SummaryChartFunctions from "./SummaryChartFunctions";


class CommentChart extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            commentScore: [],
            parentdata: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime
        }
    }

    componentDidMount(){
        const {parentdata} = this.state;
        this.getDataFromBackend(parentdata, this.props.startTime,  this.props.endTime )
    }

    async getDataFromBackend (username, startTm, endTm) {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];

        const response = await axios.get("/api/v1/projects/" + id + "/allUserNotesForChart/"+ username
            + '/' + startTm + '/' + endTm)
        const commentInfo = await response.data
        await this.setState({commentScore: commentInfo})
    }

    async componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
        }
    }

    CustomToolTip = ({ active, payload, label }) => {
        if (active && payload && payload.length) {
            const commentVal = Math.abs(Math.round(payload[0].value * 10)/10.0);
            console.log(commentVal)

            return (
                <div className="tooltipBox">
                    <p className="label">Date: {moment(label).format('YYYY-MM-DD')}</p>
                    <p className="label1">{`${'number of Comments'}: ${commentVal}`}</p>
                </div>
            );
        }
        return null;
    };

    render() {
        var output = this.state.commentScore.map(function(item) {
            return {
                date: (new Date(item.formattedDate)).getTime(),
                wordCount: +item.wordCount
            };
        });
        console.log(output);
        const from = Number(new Date( this.props.startTime));
        const to = Number(new Date(this.props.endTime));

        return (
            <div>
                <ResponsiveContainer width = '94%' height = {680} >
                    <BarChart
                        data={output}
                        margin={{ top: 20, right: 30, left: 20, bottom: 5,}}
                        >
                        <CartesianGrid
                            strokeDasharray="3 1"
                            vertical={false}
                        />
                        <XAxis dataKey= "date"
                               type = "number"
                               name = 'date'
                               allowDataOverflow={false}
                               tickSize={10}
                               hide={false}
                               angle={0}
                               interval={"preserveStartEnd"}
                               domain={[
                                   d3.timeDay.ceil(from).getTime(),
                                   d3.timeDay.ceil(to).getTime()
                               ]}
                               tickFormatter = {SummaryChartFunctions.formatDate}
                               tickCount={SummaryChartFunctions.getTickCount(to, from)}
                               mirror={false}
                        />
                        <ReferenceLine
                            y={0}
                            stroke="#000000"
                            type='category'
                        />
                        <YAxis
                            tickFormatter = {(value) =>  Math.abs(value)}
                            tickSize={10}
                            interval={0}
                            angle={0}
                            allowDecimal={false}
                        />
                        <Tooltip
                            cursor={false}
                            content={this.CustomToolTip}
                            />
                        <Legend />
                        <Bar dataKey="wordCount" stackId="a" fill="#8884d8" />
                    </BarChart>
                </ResponsiveContainer>
            </div>
        );
    }
}

export default CommentChart