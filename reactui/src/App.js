import './App.css';
import Navbar from './components/Navbar';
import {BrowserRouter as Router,Switch,Route, Redirect} from 'react-router-dom';

import Repo from './Pages/Repo';
import Home from './Pages/Home';
import Developers from './Pages/Developers';
import Settings from './Pages/Settings';

function App() {
  return (
    <>
      <Router> 

      <Navbar/>
        <Switch>
          <Route path="/" component={Home} exact/>
          <Route path='/Home' exact component={Home}/>
          <Route path='/Repo' exact component={Repo}/>
          <Route path='/Developers' exact component={Developers}/>
          <Route path='/Settings' exact component={Settings}/>
        </Switch>
      </Router>
    </>
  );
}

export default App;
