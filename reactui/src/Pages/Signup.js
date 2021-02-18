import SignupComponent from "../components/SignupComponent";

export default function Signup(){
    if(sessionStorage.getItem('new')) {
        return (
            <>
                <div className='signup'>
                    <SignupComponent/>
                </div>
            </>
        )
    }else return(
        <>
            <p>If not redirected, then please click on the home button</p>
        </>
    );
}