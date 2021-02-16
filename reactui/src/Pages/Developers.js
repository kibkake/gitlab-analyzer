import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import {AiOutlineHome} from 'react-icons/ai';
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
                <header >This is not dynamically changed right now. After the repository page sends client to a page
                which includes the project number, the list will be changed dynamically. This list is from project 6</header>
                <ul className='Footer'>
                    <Developers2Button/>

</ul>
            </div>
        )
    }
}
export default Developers;