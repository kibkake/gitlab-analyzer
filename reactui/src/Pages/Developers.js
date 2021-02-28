import React, {Component} from 'react'
import '../components/Navbar.css';
import Navbar_Developers from "../components/Navbar_Developers";

class Developers extends Component{
    render(){
        return(
            <div classname ="developers">
                <Navbar_Developers/>
                <br>
                </br>
            </div>
        )
    }
}
export default Developers;