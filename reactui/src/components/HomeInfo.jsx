import React, { Component } from 'react';
import ProfileService from "../Service/ProfileService";

export default class HomeInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            info:[],
        };
    }

    async componentDidMount() {
        const user = sessionStorage.getItem('user');
        // must grab token before we can dynamically set the token
        await ProfileService.getUserInfo(user).then((response) => {
            this.setState(response.data);
            sessionStorage.setItem('token',response.data.token);
        }, (error) => {
            console.log(error);
        });
        if(!sessionStorage.getItem('__init__')){
            ProfileService.setUserToken(sessionStorage.getItem('token')).then((response) => {
                // sent post request
            }, (error) => {
                console.log(error);
            });
            sessionStorage.setItem('__init__',"__visited");
        }
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