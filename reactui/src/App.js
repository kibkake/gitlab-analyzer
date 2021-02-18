import './App.css';
import Navbar from './components/Navbar';
import {BrowserRouter as Router, Switch, Route, Redirect, Link} from 'react-router-dom';
import React from 'react';
import Repo from './Pages/Repo';
import Home from './Pages/Home';
import Developers from './Pages/Developers';
import Settings from './Pages/Settings';
import Summary from "./Pages/Summary";
import Commits from "./Pages/Commits";
import Comments from "./Pages/Comments";
import CodeDiff from "./Pages/CodeDiff";
import Profile from "./Pages/Profile";
import Signup from "./Pages/Signup";
import LoginState from "./components/LoginState";
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
  }else{
      // will not show the rest of the pages until user is logged in
      return (
        <>
          <Router>

          <Navbar/>
            <Switch>
              <Route path={["/Home", "/"]} exact component={Home}/>
              <Route path='/Repo' exact component={Repo}/>
              <Route path='/Developers' exact component={Developers}/>
                <Route path='/Developers/summary' exact component={Summary}/>
                <Route path='/Developers/commits' exact component={Commits}/>
                <Route path='/Developers/codediff' exact component={CodeDiff}/>
              <Route path='/Developers/comments' exact component={Comments}/>

              <Route path='/Settings' exact component={Settings}/>
              <Route path='/Profile' exact component={Profile}/>
            </Switch>
          </Router>
            {!sessionStorage.getItem('new')  && window.location.pathname==="/Signup" &&
                <p>If not redirected, then please click on the home button and refresh the page</p>
            }
        </>
      );
  }
}

export default App;
