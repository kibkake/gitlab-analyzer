import {SiJsonwebtokens, TiCancel} from "react-icons/all";
import React, {useState} from "react";
import {user} from "./Navbar_Developers";
import ProfileService from "../Service/ProfileService";

export default function LoginToken(){

    const [usertoken, setUserToken] = useState("");
    const [token_toggle, setTokenToggle] = useState(false);

    // event handler onSubmit
    const toggleTokenSubmit = event => {
        setTokenToggle(!token_toggle);
        event.preventDefault();
    }

    // event handler onSubmit
    const handleSubmit = async event => {
        if(usertoken === ""){
            alert("Token cannot be empty");
        }else{
            sessionStorage.setItem("user","temp");
            sessionStorage.setItem("token",usertoken);
            await ProfileService.changeUserToken("temp",usertoken);
            toggleTokenSubmit(event);
            window.location.href="/Home";
        }
        event.preventDefault();
    }

    return(
        <>
            {!token_toggle &&
                <button className="login" onClick={toggleTokenSubmit}><SiJsonwebtokens/> Login with Token </button>
            }
            { token_toggle &&
                <form onSubmit={handleSubmit}>
                    <label>
                        <h5>Token:</h5>
                        <input name="username" type="text"  onChange={event => setUserToken(event.target.value)} />
                    </label>
                    <br/>
                    <br/>
                    <button className="login" type="submit" ><SiJsonwebtokens/> Login with Token</button> <div className="spacer"/> <button className="cancel" onClick={toggleTokenSubmit}><TiCancel/> Cancel</button>
                </form>
            }
        </>
    );
}