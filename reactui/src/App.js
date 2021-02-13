import './App.css';
import Navbar from './components/Navbar';
import Navbar1 from './components/Navbar1';

import {BrowserRouter as Router,Switch,Route, Redirect} from 'react-router-dom';

import Repo from './Pages/Repo';
import Home from './Pages/Home';
import Developers from './Pages/Developers';
import Settings from './Pages/Settings';
import Summary from "./Pages/Summary";
import Commits from "./Pages/Commits";
import Comments from "./Pages/Comments";
import CodeDiff from "./Pages/CodeDiff";

function App() {
  return (
    <>
      <Router> 

      <Navbar1/>
        <Switch>
          <Route path="/" component={Home} exact/>
          <Route path='/Home' exact component={Home}/>
          <Route path='/Repo' exact component={Repo}/>
          <Route path='/Developers' exact component={Developers}/>
            <Route path='/Developers/summary' exact component={Summary}/>
            <Route path='/Developers/Commits' exact component={Commits}/>
            <Route path='/Developers/Comments' exact component={Comments}/>
          <Route path='/Developers/CodeDiff' exact component={CodeDiff}/>

          <Route path='/Settings' exact component={Settings}/>
        </Switch>
      </Router>
    </>
  );
}

export default App;
