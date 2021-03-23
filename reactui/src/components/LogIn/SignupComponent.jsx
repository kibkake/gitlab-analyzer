import React, { Component } from 'react';
import {Route} from "react-router-dom";
import {
    BsInfoCircle,
    BsQuestionCircle, BsXCircle,
    FaCheckCircle,
    ImCancelCircle,
    ImClipboard,
    RiLoginBoxLine,
    SiGnuprivacyguard
} from "react-icons/all";
import './SignupComponent.css';
import LoginService from "../../Service/LoginService";

export default class SignupComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            info:null,
            registered:false,
            cancelled:false,
            username:"",
            password:"",
            token:"",
            avail:null
        };
        this.handleChange=this.handleChange.bind(this);
        this.checkUser=this.checkUser.bind(this);
        this.updateUser = this.updateUser.bind(this);
        this.signupHandler = this.signupHandler.bind(this);
        this.cancelHandler= this.cancelHandler.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    async checkUser(event){
        event.preventDefault();
        await this.updateUser();
        if(this.state.info==="NaN"){
            await this.setState({avail:true});
        }else await this.setState({avail:false});
    }

    updateUser(){
        LoginService.checkUserExists(this.state.username)
            .then(responseData => {
                this.setState({info:responseData.data.password});
            })
    }

    async signupHandler(event){
        event.preventDefault();
        await this.updateUser();
        if(this.state.info==="NaN" && this.state.password!=="" && this.state.token!==""){
            await LoginService.createNewAccount(this.state.username,this.state.password,this.state.token);
            sessionStorage.removeItem('new');
            sessionStorage.setItem('user',this.state.username);
            sessionStorage.setItem('token',this.state.token);
            this.setState({ registered: true });
            window.location.href='/Home';
        }else{
            if(this.state.username!=="" && this.state.password!=="" && this.state.token!=="") {
                alert("Account Creation Failed: User Already Exists!");
            }else if(this.state.username===""){
                alert("Account Creation Failed: Username Field Empty");
            }else if(this.state.password===""){
                alert("Account Creation Failed: Password Field Empty!");
            }else if(this.state.token===""){
                alert("Account Creation Failed: Token Field Empty!");
            }
        }
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
                    {window.location.href="/Home"}
                </>
            )
        }
        if (this.state.cancelled) {
            return (
                <>
                    {window.location.href="/"}
                </>
            )
        }

        return (

            <>
                <div className="signupform">
                    <h2>Sign Up</h2>
                    <form>
                        <label>
                            <h5>Username:</h5>
                            <input name="username" type="text"  onChange={this.handleChange}/>{this.state.avail? <FaCheckCircle className='success'/> : <BsXCircle className='fail'/> } <button className="checkAvail" onClick={this.checkUser} onMouseOver={this.checkUser}>Check Availability </button>
                        </label>
                        <br/>
                        <label>
                            <h5>Password:</h5>
                            <input name="password" type="password"  onChange={this.handleChange} />
                        </label>
                        <br/>
                        <label>
                            <h5>Token:</h5>
                            <input name="token" type="text"  onChange={this.handleChange} />
                        </label>
                        <br/>
                        <br/>
                        <button className="signup" onClick={this.signupHandler} onMouseOver={this.checkUser}><ImClipboard/> Confirm</button>
                        <div className="spacer"/>
                        <button className="cancel" onClick={this.cancelHandler}><ImCancelCircle/> Cancel </button>
                    </form>
                </div>
            </>
        )
    }
}