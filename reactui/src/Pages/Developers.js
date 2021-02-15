import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import {AiOutlineHome} from 'react-icons/ai';
import '../components/Navbar.css';
import {MenuItems} from "../components/MenuItem_Developers";
import Developers2Button from "../components/Developers2Button";

class Developers extends Component{
    render(){
        return(
<ul>
            <nav className="navbarItems">

                <ul className='navMenu'>
                    {MenuItems.map((item,index)=>{
                        return(
                            <li key={index} className={item.cName}>
                                <Link to={item.url}>
                                    <span>{item.title}</span>
                                </Link>
                            </li>
                        )
                    })}


                </ul>

            </nav>
    <ul className='Footer'>
        <Developers2Button/>

    </ul>
</ul>
        )
    }
}
export default Developers;