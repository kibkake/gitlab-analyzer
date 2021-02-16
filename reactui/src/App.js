import './App.css';
import Navbar from './components/Navbar';
import {BrowserRouter as Router,Switch,Route, Redirect} from 'react-router-dom';
import Repo from './Pages/Repo';
import Home from './Pages/Home';
import Developers from './Pages/Developers';
import Settings from './Pages/Settings';
import Summary from "./Pages/Summary";
import Commits from "./Pages/Commits";
import Comments from "./Pages/Comments";
import CodeDiff from "./Pages/CodeDiff";
import Chart from "./Pages/Chart";
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar_dropdown from "./components/storage/Navbar_dropdown";

function App() {
  return (
    <>
      <Router> 

      <Navbar/>
        <Switch>
          <Route path="/" component={Home} exact/>
          <Route path='/Home' exact component={Home}/>
          <Route path='/Repo' exact component={Repo}/>
          <Route path='/Repo/*/Developers' exact component={Developers}/>
            <Route path='/Developers/summary' exact component={Summary}/>
            <Route path='/Developers/commits' exact component={Commits}/>
            <Route path='/Developers/codediff' exact component={CodeDiff}/>
          <Route path='/Developers/comments' exact component={Comments}/>
          <Route path='/Repo/*/Developers/*/chart' exact component={Chart}/>

          <Route path='/Settings' exact component={Settings}/>
        </Switch>
      </Router>
    </>
  );
}

export default App;
