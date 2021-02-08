import './App.css';
import Navbar from './components/Navbar';
import {BrowserRouter as Router,Switch,Route} from 'react-router-dom';

import Repo from './Pages/Repo';
import Student from './Pages/Developers';

function App() {
  return (
    <>
      <Router> 
      <Navbar/>
        <Switch>
          <Route path='/Repo' exact component={Repo}/>
          <Route path='/Developers' exact component={Student}/>
        </Switch>
      </Router>
    </>
  );
}

export default App;
