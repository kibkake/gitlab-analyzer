import './App.css';
import Navbar from './components/Navbar';
import Navbar1 from './components/Navbar1';

import {BrowserRouter as Router,Switch,Route, Redirect} from 'react-router-dom';

import Repo from './Pages/Repo';
import Home from './Pages/Home';
import Developers from './Pages/Developers';
import Settings from './Pages/Settings';
import Summary from "./Pages/DeveloperData/Summary";
import Commits from "./Pages/DeveloperData/Commits";
import Comments from "./Pages/DeveloperData/Comments";
import CodeDiff from "./Pages/DeveloperData/CodeDiff";

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
            <Route path='/Summary' exact component={Summary}/>
            <Route path='/Commits' exact component={Commits}/>
            <Route path='/Comments' exact component={Comments}/>
          <Route path='/CodeDiff' exact component={CodeDiff}/>

          <Route path='/Settings' exact component={Settings}/>
        </Switch>
      </Router>
    </>
  );
}

export default App;
