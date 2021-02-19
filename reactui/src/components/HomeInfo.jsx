import React, { Component } from 'react';
import ProfileService from "../Service/ProfileService";
import {Redirect} from "react-router-dom";

export default class HomeInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            info:[],
        };
    }

    componentDidMount() {
        const user = sessionStorage.getItem('user');
        ProfileService.getUserInfo(user).then((response) => {
            this.setState(response.data);
            sessionStorage.setItem('token',response.data.token);
        }, (error) => {
            console.log(error);
        });
    }


    render() {
        return (
            <>
                <br/>
                <h4 style={{textAlign:'center'}}>Welcome to Pluto GitLab Analyzer, {this.state.username}!</h4>;
            </>
        )
    }
}