import React from "react";
import {HiOutlineEmojiHappy, MdMoodBad} from "react-icons/all";

function signupHandler(){
    sessionStorage.removeItem("terms");
    sessionStorage.setItem("new","true");
    window.location.href="/Signup";
}

function cancelHandler(){
    sessionStorage.clear();
    window.location.href="/";
}

export default function Terms(){
    return(
        <>
            <div className='terms'>
                <h2>Terms and Conditions</h2>
                <button className="signup" onClick={signupHandler}><HiOutlineEmojiHappy/> I Agree</button>
                <div className="spacer"/>
                <button className="cancel" onClick={cancelHandler}><MdMoodBad/> I Disagree</button>
            </div>
        </>
    )
}