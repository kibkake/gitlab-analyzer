import {SiJsonwebtokens, TiCancel} from "react-icons/all";
import React, {useState} from "react";
import ProfileService from "../Service/ProfileService";

export default function LoginToken(){

    const [usertoken, setUserToken] = useState("");

    const handleSubmit = async event => {
        if(usertoken === ""){
            alert("Token cannot be empty");
        }else{
            sessionStorage.setItem("user","temp");
            sessionStorage.setItem("token",usertoken);
            // need to change the temp user token in database before user can go home
            await ProfileService.changeUserToken("temp",usertoken);
            window.location.href="/Home";
        }
        event.preventDefault();
    }

    // cancel simply by reloading
    const handleCancel = event =>{
        window.location.reload();
        event.preventDefault();
    }

    return(
        <>
            <form onSubmit={handleSubmit}>
                <label>
                    <h5>Token:</h5>
                    <input name="u_token" type="text"  onChange={event => setUserToken(event.target.value)} />
                </label>
                <br/>
                <br/>
                <button className="login" type="submit" ><SiJsonwebtokens/> Login with Token</button> <div className="spacer"/> <button className="cancel" onClick={handleCancel}><TiCancel/> Cancel</button>
            </form>
        </>
    );
}