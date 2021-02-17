import React, { Component } from 'react';
import {RiLogoutBoxRLine} from "react-icons/all";
import ProfileService from "../Service/ProfileService";
import {Redirect} from "react-router-dom";
import './ProfileComponent.css';

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
                <div className="profile">
                    <h2>Profile</h2>
                    <br/>
                    <h5>Username:</h5>
                    <p>{this.state.username}</p>
                    <h5>Token:</h5>
                    <p>{this.state.token}</p>
                    <br/>
                    <button className="logout" onClick={this.handleLogout}>Logout <RiLogoutBoxRLine/></button>
                </div>
            </>
        )
    }
}