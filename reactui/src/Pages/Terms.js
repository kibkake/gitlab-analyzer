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
                <table className="terms-tablet">
                    <td>
                        <ol className="terms-items">
                            <li>The administrators have the right to termination of service of any users.</li>
                            <li>No warranty is given for using the system and it is the user’s own risk when using the system.</li>
                            <li>We will only store information at sign up regarding personal information such as passwords, tokens,
                                and username. The password will be encrypted at storage. We will not share this information with 3rd parties</li>
                            <li>Other statistics we store may include GitLab repository data which serves only for optimization
                                or user saving purposes.</li>
                            <li>The developers do not claim responsibility in the case of damage or expenses incurred from data
                                breach events or other possibly malicious attacks from using the system. No compensation will be given in any case.</li>
                            <li>The application is released under GNU GPLv3 which explicitly states that any use of the code must be
                                open-sourced. The GPLv3 also states that no warranty for the application is in place. Use at your own risk.</li>
                        </ol>
                    </td>
                </table>
                <button className="signup" onClick={signupHandler}><HiOutlineEmojiHappy/> I Agree</button>
                <div className="spacer"/>
                <button className="cancel" onClick={cancelHandler}><MdMoodBad/> I Disagree</button>
                <br/>
                <br/>
            </div>
        </>
    )
}