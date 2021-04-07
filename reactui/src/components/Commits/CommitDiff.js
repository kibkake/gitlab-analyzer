import React, {Component} from 'react'
import "../Projects/ProjectList.css";
import {Table} from "react-bootstrap";
import HighlightCodeDiffs from "./HighlightCodeDiffs"
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import ExpandButton from "./ExpandButton";
import Spinner from "react-bootstrap/Spinner";

class SingleCommitDiff extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            hash : this.props.hash,
            expanded: false,
            commits : this.props.commits
        };
        this.handler = this.handler.bind(this)
    }

    async handler() {
        if(this.state.expanded === true) {
            await this.setState({
                expanded: false
            })
        } else{
            await this.setState({
                expanded: true
            })
        }
    }

    async componentDidMount() {
        await this.getDataFromBackend();
        this.applyMultipliersCommits();
    }

    async getDataFromBackend(){
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var hash = this.props.hash;

        let url2 = '/api/v1/projects/' + repNum + '/Commit/' + hash;
        const result = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        const resp = await result.json();
        await this.setState({data:resp})
        console.log(resp)
    }

    applyMultipliersCommits(){
        var scale = JSON.parse(sessionStorage.getItem('languageScale'));
        var newCommits = [...this.state.data];

        for(const k in newCommits){
            var newCommitsScore=0;
            for(var i in newCommits[k].diffs){
                var fileExtension = newCommits[k].diffs[i].new_path.split(".").pop();
                const extensionIndex = scale.findIndex(scale => scale.extension === fileExtension);
                if(extensionIndex!==-1){
                    var newScore = scale[extensionIndex].multiplier * newCommits[k].diffs[i].diffScore;
                    newCommits[k].diffs[i] = {...newCommits[k].diffs[i], diffScore:newScore};
                    newCommitsScore = newCommitsScore+newScore;
                }else{
                    const defaultIndex = scale.findIndex(scale => scale.name==='Default');
                    var newScore = scale[defaultIndex].multiplier * newCommits[k].diffs[i].diffScore;
                    newCommits[k].diffs[i] = {...newCommits[k].diffs[i], diffScore:newScore};
                    newCommitsScore = newCommitsScore+newScore;
                }
            }
            newCommits[k].commitScore=newCommitsScore;
        }

        console.log(newCommits);
        this.setState({
            data:newCommits,
        })
    }
    async componentDidUpdate(prevProps){
        if(this.props.hash !== prevProps.hash){
            await this.getDataFromBackend();
            this.applyMultipliersCommits();
        }
    }

    render() {
        if(this.state.data.length === 0){
            return (
                <div
                    style={{ overflow: "scroll", minHeight: "1000px", width: "1000px"}}>
                    <br>
                    </br>
                    <text style={{fontWeight: 'bold', fontSize: '20px', color: 'blue'}}>
                        Loading commit diff....
                    </text>
                    <Spinner
                        animation="border"
                        variant="primary"
                        size="la"
                    />
                </div>
            )
        }
        return (
            <div>
                <ExpandButton
                    ever = {this.state.data}
                    handler = {this.handler}
                    expanded = {this.state.expanded}>
                </ExpandButton>
            </div>
        )
    }
}

export default SingleCommitDiff;