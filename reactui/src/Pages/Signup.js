import SignupComponent from "../components/LogIn/SignupComponent";

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
            <navbar/>
        </>
    );
}