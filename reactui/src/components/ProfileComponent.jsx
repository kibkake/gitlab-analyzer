import React, { Component } from 'react';
import {
    GiToken,
    ImClipboard,
    RiLockPasswordLine,
    RiLogoutBoxRLine,
} from "react-icons/all";
import ProfileService from "../Service/ProfileService";
import {Redirect} from "react-router-dom";
import './ProfileComponent.css';
import LoginService from "../Service/LoginService";

export default class ProfileComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            info:[],
            logout:false,
            pass:false,
            ctoken:false,
            oldpassword:"",
            newpassword:"",
            conpassword:"",
            newtoken:"",
            passok:false,
            passconfirm:true
        };
        this.handleLogout=this.handleLogout.bind(this);
        this.handlePass=this.handlePass.bind(this);
        this.handleToken=this.handleToken.bind(this);
        this.handleChange=this.handleChange.bind(this);
        this.passChangeHandler=this.passChangeHandler.bind(this);
        this.checkPass=this.checkPass.bind(this);
        this.checkAuthenticated=this.checkAuthenticated.bind(this);
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

    handlePass(event){
        event.preventDefault();
        this.setState({pass:true});
    }

    handleToken(event){
        event.preventDefault();
        this.setState({ctoken:true});
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    async passChangeHandler(event){
        event.preventDefault();
        await this.checkPass(event);
        if(this.state.passok && this.state.passconfirm){
            await ProfileService.changeUserPassword(sessionStorage.getItem('user'), this.state.newpassword);
            alert("Password Successfully Changed!");
        }
        this.setState({pass:false,passok:false,passconfirm:true});
    }

    async tokenChangeHandler(event){
        event.preventDefault();
        if(this.state.newtoken===""){
            alert("Token Cannot Be Empty");
        }else{
            await ProfileService.changeUserToken(sessionStorage.getItem('user'),this.state.newtoken);
            alert("Successfully Changed Token!");
        }
        this.setState({token:false});
    }

    async checkPass(event){
        event.preventDefault();
        await this.checkAuthenticated(event);
        if(this.state.newpassword !== this.state.conpassword){
            await this.setState({passconfirm:false});
            alert("New Password Did Not Match With Confirm Password");
        }
        if(this.state.passok === false){
            alert("Old Password Did Not Match With Database!");
        }
        if(this.state.newpassword===""){
            alert("Password Cannot Be Blank");
            this.setState({passok:false});
        }
    }

    async checkAuthenticated(event){
        event.preventDefault();
        await LoginService.checkUserCredentials(sessionStorage.getItem('user'), this.state.oldpassword)
            .then(responseData => {
                this.setState({passok:responseData.data});
            })
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
                    <button className="tokenChange" onClick={this.handleToken}>Change Token <GiToken/></button>
                    {this.state.ctoken &&
                        <form>
                            <label>
                                <h5>New Token:</h5>
                                <input className="newtoken" name="newtoken" type="text" onChange={this.handleChange}/>
                            </label>
                            <button className="signup" onClick={this.tokenChangeHandler}>Confirm <ImClipboard/></button>
                        </form>
                    }
                    <br/>
                    <button className="passChange" onClick={this.handlePass}>Change Password <RiLockPasswordLine/></button>
                    {this.state.pass &&
                        <form>
                            <label>
                                <h5>Old Password:</h5>
                                <input className="oldpass" name="oldpassword" type="password"  onChange={this.handleChange} />
                            </label>
                            <label>
                                <h5>New Password:</h5>
                                <input name="newpassword" type="password"  onChange={this.handleChange} />
                            </label>
                            <label>
                                <h5>Confirm New Password:</h5>
                                <input name="conpassword" type="password"  onChange={this.handleChange} />
                            </label>
                            <button className="signup" onClick={this.passChangeHandler}>Confirm <ImClipboard/></button>
                        </form>
                    }
                    <br/>
                    <br/>
                    <button className="logout" onClick={this.handleLogout}>Logout <RiLogoutBoxRLine/></button>
                </div>
            </>
        )
    }
}