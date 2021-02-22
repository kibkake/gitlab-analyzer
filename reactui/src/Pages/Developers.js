import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import '../components/Navbar.css';
import {MenuItems} from "../components/MenuItem_Developers";
import Navbar_Developers from "../components/Navbar_Developers";
import Developers2Button from "../components/Developers2Button";

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