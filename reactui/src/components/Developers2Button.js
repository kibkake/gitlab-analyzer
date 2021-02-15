import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

import {RepoItems} from '../Pages/sampleRepo';
import RepoButton from "./RepoButton";
//import "./RepoButton.css"


class Developers2Button extends Component{
    constructor(props){
        super(props);
        this.state={
            data: []
        };
    }

    componentDidMount() {
        var str = window.location.pathname;
        var strArr = str.split("/");
        let url = 'http://localhost:8080/getuserstats/6/arahilin'
        let url2 = 'http://localhost:8080/getprojectmembers/' + strArr[1]
        fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then((result)=> {
            result.json().then((resp) => {
                this.setState({data:resp})
            })
        })

    }

    render(){
        var str = window.location.pathname;
        var strArr = str.split("/");
        var data = JSON.stringify(this.state.data);
        var DataArray = JSON.parse(data)


        console.log(DataArray);

        return(
            <div> Component </div>
        );

    }

}

export default Developers2Button;
