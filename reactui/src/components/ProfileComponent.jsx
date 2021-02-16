import React, { Component } from 'react';
import {RiLogoutBoxRLine} from "react-icons/all";
import ProfileService from "../Service/ProfileService";
import {Redirect} from "react-router-dom";

export default class ProfileComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            info:[],
            logout:false
        };
        this.handleLogout = this.handleLogout.bind(this)
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

    handleLogout(){
        ProfileService.logout();
        this.setState({ logout: true })
    }

    render() {
        if (this.state.logout) {
            return (
                <>
                    {window.location.reload()}
                    <Redirect to='/Home'/>
                </>
            )
        }

        return (
            <>
                <h1>Profile</h1>
                <br/>
                <h4>Username:</h4>
                <p>{this.state.username}</p>
                <h4>Token:</h4>
                <p>{this.state.token}</p>
                <button onClick={this.handleLogout}>Logout <RiLogoutBoxRLine/></button>
            </>
        )
    }
}