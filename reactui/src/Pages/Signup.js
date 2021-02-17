import SignupComponent from "../components/SignupComponent";
import {Redirect} from "react-router-dom";

export default function Signup(){
    if(sessionStorage.getItem('new')) {
        return (
            <>
                <div className='signup'>
                    <SignupComponent/>
                </div>
            </>
        )
    }return <Redirect to='/Home'/>;
}