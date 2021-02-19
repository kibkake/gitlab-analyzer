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
            <navbar/>
        </>
    );
}