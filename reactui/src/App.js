import './App.css';
import Navbar from './components/NavBars_Menu/Navbar';
import {BrowserRouter as Router, Switch, Route, Redirect, Link} from 'react-router-dom';
import React, {useState} from 'react';
import Repo from './Pages/Repo';
import Home from './Pages/Home';
import Developers2 from './Pages/Developers2';
import Summary from "./Pages/Summary";
import Commits from "./Pages/Commits";
import SingleCommit from "./Pages/SingleCommit";
import Comments from "./Pages/Comments";
import MergeRequest from "./Pages/MergeRequest";
import Profile from "./Pages/Profile";
import Signup from "./Pages/Signup";
import LoginState from "./components/LogIn/LoginState";
import Chart from "./Pages/Chart";
import 'bootstrap/dist/css/bootstrap.min.css';
import useToken from "./useToken";
import {SiGnuprivacyguard, SiJsonwebtokens} from "react-icons/all";
import SignupComponent from "./components/LogIn/SignupComponent";
import LoginToken from "./components/LogIn/LoginToken.js";
import Terms from "./Pages/Terms";

function signupHandler(){
    sessionStorage.setItem('terms','view');
    sessionStorage.setItem('user','signup');
    window.location.reload();
}

function App() {
  const { user, setUser } = useToken();
  const [token_toggle, setTokenToggle] = useState(false);

  const toggleTokenSubmit = event => {
      setTokenToggle(!token_toggle);
      event.preventDefault();
  }

  // requires a authentication token to proceed
  if(!sessionStorage.getItem('user')) {
      return (
          <>
              <Router>
                  <Navbar/>
                  <switch>
                      <Route path='/Signup' exact component={Signup}/>
                      <Route path='/Terms' exact component={Terms}/>
                  </switch>
              </Router>
              <div className="loginwrapper">
                  <h2 className="login-h2"> Please Login or Sign Up to Continue</h2>
                  <br/>
                  {!token_toggle &&
                  <>
                      <LoginState setUser={setUser}/>
                      <br/>
                      <button className="login" onClick={toggleTokenSubmit}><SiJsonwebtokens/> Login with Token</button>
                      <br/>
                      <br/>
                      <p className="signuptext">Don't have an account?
                          <button className="login" onClick={signupHandler}><SiGnuprivacyguard/>Sign Up</button>
                      </p>
                  </>
                  }
                  {token_toggle &&
                  <LoginToken/>
                  }
                  <br/>
              </div>

          </>
      );
  }else if(sessionStorage.getItem('new')) { // signup
      return (
          <Router>

              <Navbar/>
              <Redirect to='/Signup'/>
              <SignupComponent/>
              <switch>
                  <Route path='/Terms' exact component={Terms}/>
              </switch>
          </Router>
      );
  }else if(sessionStorage.getItem('terms')) { // terms
      return (
          <Router>
              <Navbar/>
              <Redirect to='/Terms'/>
              <switch>
                  <Route path='/Terms' exact component={Terms}/>
              </switch>
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
          <Route path='/Repo/*/Developers/*/merges' exact component={MergeRequest}/>
          <Route path='/Repo/*/Developers/*/merges/*' exact component={MergeRequest}/>

          <Route path='/Repo/*/Developers/*/comments' exact component={Comments}/>
          <Route path='/Repo/*/Developers/*/commits/*' exact component={SingleCommit}/>

                <Route path='/Settings' exact component={Home}/>
              <Route path='/Profile' exact component={Profile}/>
            </Switch>
          </Router>
        </>
      );
  }
}

export default App;
