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
      /*const resp = await result.json();
      await this.setState({data:resp})
      console.log(resp)

        console.log(this.props.hash)
        console.log(this.props.commits)*/

        var hash = this.props.hash;
        console.log(hash)
        console.log(this.state.commits)
        var tempArr = [];
        //tempArr = this.props.commits
        //console.log(this.props.commits)

        //if(this.props.commit != null) {
        //var tempArr = this.props.commits;
        //console.log(tempArr)
        //}

        await this.state.commits.map(function(item) {
            if(item.id === hash){
                tempArr.push(item)
            }
        })
        console.log(tempArr)
        await this.setState({data:tempArr})
    }

    async componentDidUpdate(prevProps){
        if(this.props.hash !== prevProps.hash){
            await this.getDataFromBackend()
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