import React, {Component, useState} from "react";
import LoginService from "../Service/LoginService";
import {Redirect} from "react-router-dom";

class Form extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username:'',
            password:'',
            response: [],
            redirect: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange = ({ target }) => {
        this.setState({ [target.name]: target.value });
    };

    handleSubmit = (event) => {
        this.state.response = LoginService.checkUserCredentials(this.state.username, this.state.password);
        this.state.response.then((response) => {
            console.log(response.data);
            alert('Login: ' + response.data)
            if(response.data === true){
                setUser();
            }
        }, (error) => {
            console.log(error);
        });

        event.preventDefault();
    };


    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Username:
                    <input name="username" type="text" value={this.state.username} onChange={this.handleChange} />
                </label>
                <label>
                    Password:
                    <input name="password" type="password" value={this.state.password} onChange={this.handleChange} />
                </label>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        );
    }
}export default Form;