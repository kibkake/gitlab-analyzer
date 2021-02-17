import React, { Component } from 'react';
import {Redirect} from "react-router-dom";
import {ImCancelCircle, ImClipboard, SiGnuprivacyguard} from "react-icons/all";
import './SignupComponent.css';

export default class SignupComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            info:[],
            registered:false,
            cancelled:false
        };
        this.signupHandler = this.signupHandler.bind(this);
        this.cancelHandler= this.cancelHandler.bind(this);
    }

    componentDidMount() {
        // const user = sessionStorage.getItem('user');
        // ProfileService.getUserInfo(user).then((response) => {
        //     this.setState(response.data);
        //     sessionStorage.setItem('token',response.data.token);
        // }, (error) => {
        //     console.log(error);
        // });
    }

    signupHandler(){
        //SignupService.
        sessionStorage.removeItem('new');
        sessionStorage.removeItem('user');
        this.setState({ registered: true })
        alert("Account Successfully Created! Please Sign in Again.");
    }

    cancelHandler(){
        sessionStorage.removeItem('new');
        sessionStorage.removeItem('user');
        this.setState({cancelled:true});
    }

    render() {
        if (this.state.registered) {
            return (
                <>
                    <Redirect to='/Home'/>
                    {window.location.reload()}
                </>
            )
        }
        if (this.state.cancelled) {
            return (
                <>
                    <Redirect to='/Home'/>
                    {window.location.reload()}
                </>
            )
        }

        return (
            <>
                <div className="signupform">
                    <h2>Sign Up</h2>
                    <button className="cancel" onClick={this.cancelHandler}>Cancel <ImCancelCircle/></button>
                    <button className="signup" onClick={this.signupHandler}>Confirm <ImClipboard/></button>
                </div>
            </>
        )
    }
}