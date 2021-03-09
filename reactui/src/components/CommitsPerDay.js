import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

import {RepoItems} from '../Pages/sampleRepo';
import RepoButton from "./RepoButton";
//import "./RepoButton.css"
import  "./HBox.css"

class CommitsPerDay extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            devName: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime
        };
    }

    async componentDidMount(){
        await this.getDataFromBackend(this.props.devName, this.props.startTime,  this.props.endTime )
    }

    async getDataFromBackend(userName, date, date2) {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];

        let url2 = '/api/v1/projects/' + repNum + '/Commits/' + userName + '/' + date + "/" + date
        const result = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        const resp = await result.json();
        await this.setState({data:resp})
    }

    async componentDidUpdate(prevProps){
        console.log(this.props.devName)
        if(this.props.devName !== prevProps.devName ||
            this.props.startTime !== prevProps.startTime ||
            this.props.endTime !== prevProps.endTime){
            await this.getDataFromBackend(this.props.devName, this.props.startTime,this.props.endTime )
        }
    }

    render(){

        var output = this.state.data.map(function(item) {
            return {
                id: item.id,
                date: item.date
            };
        });

        var data = JSON.stringify(output);
        var DataArray = JSON.parse(data)
        //{DataArray.map(item => console.log(item.id))}

        return(
            <ul>
            {this.state.data.map(item => {
                return <li>
                    <a href= {"Developers/" + item }target= "_blank">
                        <Button className="Footer2" to={item.id}
                                type="button"
                                onClick={(e) => {
                                    e.preventDefault();
                                    {this.props.handler(item.id)}

                                }}>
                            <span >{item.id}          commited at:      {item.date.substring(11,19)}</span>
                        </Button>
                    </a>
                </li>;
            })}
        </ul>
        );
    }
}

export default CommitsPerDay;