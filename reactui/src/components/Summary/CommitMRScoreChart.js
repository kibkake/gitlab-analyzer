import React, { PureComponent } from 'react';
import {BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer} from 'recharts';
import axios from "axios";
import * as d3 from "d3-time";
import moment from 'moment'

//'https://jsfiddle.net/alidingling/90v76x08/']
export default class CommitMRScoreChart extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            codeScore:[{date: null, commitScore: 0, mergeRequestScore: 0,commitIds:[],mergeRequestIds:[]}],
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
        var name = username;
        if(sessionStorage.getItem('DeveloperNames' + id) != null && sessionStorage.getItem('Developers' + id) != null) {
            for (var i = 0; i < JSON.parse(sessionStorage.getItem('Developers' + id)).length; i++) {
                if (JSON.stringify(username) === JSON.stringify(JSON.parse(sessionStorage.getItem('Developers' + id))[i])) {
                    name = JSON.parse(sessionStorage.getItem('DeveloperNames' + id))[i]//use name to retrieve data
                }
            }
        }

        const response = await axios.get("/api/v1/projects/" + id + "/MRsAndCommitScoresPerDay/" + username + '/' +
            startTm + '/' +
            endTm + "/either")

        const score = await response.data
        console.log("scores")
        console.log(score)
        await this.setState({codeScore : score, parentdata: username,startTime: startTm,
            endTime: endTm})
        this.applyMultipliers();
        await console.log(this.state.codeScore)
    }

    applyMultipliers(){
        var scale = JSON.parse(sessionStorage.getItem('languageScale'));
        var newCodeScore = [...this.state.codeScore];
        for(const k in newCodeScore){
            var newCommitScore=0;
            var newMergeScore=0;
            for(const i in newCodeScore[k].commitIds){
                for(const p in newCodeScore[k].commitIds[i].diffs){
                    var fileExtension = newCodeScore[k].commitIds[i].diffs[p].new_path.split(".").pop();
                    const extensionIndex = scale.findIndex(scale => scale.extention === fileExtension);
                    if(extensionIndex!==-1){
                        var tempCommitScore = scale[extensionIndex].multiplier*newCodeScore[k].commitIds[i].diffs[p].diffScore;
                        newCommitScore = newCommitScore+tempCommitScore;
                    }else{
                        newCommitScore = newCommitScore+newCodeScore[k].commitIds[i].diffs[p].diffScore;
                    }
                }
            }
            for(const i in newCodeScore[k].mergeRequestIds){
                for(const p in newCodeScore[k].mergeRequestIds[i].diffs){
                    var fileExtension = newCodeScore[k].mergeRequestIds[i].diffs[p].new_path.split(".").pop();
                    const extensionIndex = scale.findIndex(scale => scale.extention === fileExtension);
                    if(extensionIndex!==-1){
                        var tempCommitScore = scale[extensionIndex].multiplier*newCodeScore[k].mergeRequestIds[i].diffs[p].diffScore;
                        newMergeScore = newMergeScore+tempCommitScore;
                    }else{
                        newMergeScore = newMergeScore+newCodeScore[k].mergeRequestIds[i].diffs[p].diffScore;
                    }
                }
            }
            newCodeScore[k].mergeRequestScore=newMergeScore;
            newCodeScore[k].commitScore=newCommitScore;
        }
        this.setState({
            codeScore:newCodeScore,
        })
    }
    

    async componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
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