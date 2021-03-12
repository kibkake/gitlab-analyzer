import React, {Component} from 'react'
import '../components/NavBars_Menu/Navbar.css';
import Navbar_Developers from "../components/NavBars_Menu/Navbar_Developers";

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