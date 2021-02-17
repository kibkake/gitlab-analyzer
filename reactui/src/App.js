import './App.css';
import Navbar from './components/Navbar';
import {BrowserRouter as Router,Switch,Route, Redirect} from 'react-router-dom';
import React, { useState } from 'react';
import Repo from './Pages/Repo';
import Home from './Pages/Home';
import Developers from './Pages/Developers';
import Settings from './Pages/Settings';
import Summary from "./Pages/Summary";
import Commits from "./Pages/Commits";
import Comments from "./Pages/Comments";
import CodeDiff from "./Pages/CodeDiff";
import Profile from "./Pages/Profile";
import LoginState from "./components/LoginState";
import 'bootstrap/dist/css/bootstrap.min.css';
import useToken from "./useToken";
import Navbar_dropdown from "./components/storage/Navbar_dropdown";

function App() {
  const { user, setUser } = useToken();
  const signed = sessionStorage.getItem('user');
  // requires a authentication token to proceed
  if(!signed) {
    return (
        <>
            <Router>
                <Navbar/>
            </Router>
            <div class="loginwrapper">
                <h2> Please Login to Continue</h2>
                <br/>
                <LoginState setUser={setUser} />
            </div>
        </>
    );
  }
  // will not show the rest of the pages until user is logged in
  return (
    <>
      <Router> 

      <Navbar/>
        <Switch>
          <Route path="/" component={Home} exact/>
          <Route path='/Home' exact component={Home}/>
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
    </>
  );
}

export default App;
