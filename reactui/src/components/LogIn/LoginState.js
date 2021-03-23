import React, { useState } from 'react';
import PropTypes from 'prop-types';
import LoginService from "../../Service/LoginService";
import {RiLoginBoxLine} from "react-icons/all";

/*
    LoginState was created with the guidance of DigitalOcean, Inc.
        See More --> "https://www.digitalocean.com/community/tutorials/how-to-add-login-authentication-to-react-applications"
 */
function LoginState({ setUser }) {
    // we use this because functions dont have super(props)
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();

    function attemptLogin(username,password) {
        LoginService.checkUserCredentials(username,password).then((response) => {
            if(response.data === true){
                setUser(username);
                window.location.href="/Home";
            }else{
                alert('Login Failed: Credentials Invalid!');
            }
        }, (error) => {
            console.log(error);
        });
    }

    // event handler onSubmit
    const handleSubmit = event => {
        const user = username;
        attemptLogin(username,password);
        event.preventDefault();
    }

    return(
        <form onSubmit={handleSubmit}>
            <label>
                <h5>Username:</h5>
                <input name="username" type="text"  onChange={event => setUsername(event.target.value)} />
            </label>
            <br/>
            <label>
                <h5>Password:</h5>
                <input name="password" type="password"  onChange={event => setPassword(event.target.value)} />
            </label>
            <br/>
            <br/>
            <button className="login" type="submit" > <RiLoginBoxLine/> Login</button>
        </form>
    );
}export default LoginState;

// allows for setting passed in functions
LoginState.propTypes = {
    setUser: PropTypes.func.isRequired
};