import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import {AiOutlineHome} from 'react-icons/ai';
import '../components/Navbar.css';
import {MenuItems} from "../components/MenuItem_Developers";
import Navbar_Developers from "../components/Navbar_Developers";

class Developers extends Component{
    render(){
        return(
            <div classname ="developers">
                <Navbar_Developers/>
                <br>
                </br>
                <h4 style={{textAlign:'center'}}>Add list of developer component here</h4>

            </div>
        )
    }
}
export default Developers;