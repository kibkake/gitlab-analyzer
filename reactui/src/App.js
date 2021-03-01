import './App.css';
import Navbar from './components/Navbar';
import {BrowserRouter as Router, Switch, Route, Redirect, Link} from 'react-router-dom';
import React from 'react';
import Repo from './Pages/Repo';
import Home from './Pages/Home';
import Developers from './Pages/Developers';
import Developers2 from './Pages/Developers2';
import Summary from "./Pages/Summary";
import Commits from "./Pages/Commits";
import Commits2 from "./Pages/Commits2";
import SingleCommit from "./Pages/SingleCommit";
import Comments from "./Pages/Comments";
import MergeRequest from "./Pages/MergeRequest";
import Profile from "./Pages/Profile";
import Signup from "./Pages/Signup";
import Setting from "./Pages/Settings"
import LoginState from "./components/LoginState";
import Chart from "./Pages/Chart";
import 'bootstrap/dist/css/bootstrap.min.css';
import useToken from "./useToken";
import Navbar_dropdown from "./components/storage/Navbar_dropdown";
import {SiGnuprivacyguard} from "react-icons/all";
import {AiOutlineHome} from "react-icons/ai";
import SignupComponent from "./components/SignupComponent";

function signupHandler(){
    sessionStorage.setItem('new','true');
    sessionStorage.setItem('user','temp');
    window.location.reload();
}

function App() {
  const { user, setUser } = useToken();
    sessionStorage.setItem("startdate", "2021-01-11");
    sessionStorage.setItem("enddate", "2021-02-22");
    console.log(sessionStorage.getItem("startdate"));
    console.log(sessionStorage.getItem("enddate"));

    // requires a authentication token to proceed
  if(!sessionStorage.getItem('user')) {
    return (
        <>
            <Router>
                <Navbar/>
                <switch>
                    <Route path='/Signup' exact component={Signup}/>
                </switch>
            </Router>
            <div className="loginwrapper">
                <h2> Please Login or Sign Up to Continue</h2>
                <br/>
                <LoginState setUser={setUser} />
                <br/>
                <p>Don't have an account?<button className="login" onClick={signupHandler}><SiGnuprivacyguard/>Sign Up</button></p>
            </div>

        </>
    );
  }else if(sessionStorage.getItem('new')){ // signup
      return(
          <Router>

              <Navbar/>
              <Redirect to='/Signup'/>
              <SignupComponent/>
          </Router>
      );
  }
  else{
      // will not show the rest of the pages until user is logged in
      return (
        <>
          <Router>

      <Navbar/>
        <Switch>
          <Route path="/" component={Home} exact/>
          <Route path='/Home' exact component={Home}/>
          <Route path='/Repo' exact component={Repo}/>

          <Route path='/Repo/*/Developers/*/commits' exact component={Chart}/>
          <Route path='/Repo/*/Developers' exact component={Developers2}/>
          <Route path='/Repo/*/Developers/*/summary' exact component={Summary}/>

          <Route path='/Repo/*/Developers/*/codeContribution' exact component={Commits}/>
          <Route path='/Repo/*/Developers/*/codediff' exact component={MergeRequest}/>
          <Route path='/Repo/*/Developers/*/comments' exact component={Comments}/>
          <Route path='/Repo/*/Developers/*/commits/*/*' exact component={SingleCommit}/>
          <Route path='/Repo/*/Developers/*/commits/*' exact component={Commits2}/>

                <Route path='/Settings' exact component={Setting}/>
              <Route path='/Profile' exact component={Profile}/>
            </Switch>
          </Router>
        </>
      );
  }
}

export default App;
