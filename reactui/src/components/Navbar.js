import React, {Component} from 'react'
import {MenuItems} from "./MenuItems";
import {Link} from 'react-router-dom';
import {AiOutlineHome} from 'react-icons/ai';

import './Navbar.css';

class Navbar extends Component{
    render(){
        return(
            <nav className="navbarItems">
                <div>

                    <Link to='/Home' className='nav-icon'>
                        <AiOutlineHome size={30} />
                    </Link>
                </div>
                <ul className='navMenu'>
                    {MenuItems.map((item,index)=>{
                        return(
                            <li key={index} className={item.cName}>
                                <Link to={item.url}>
                                    {item.icon}
                                    <span>{item.title}</span>
                                </Link>
                            </li>
                        )
                    })}
                </ul>
                <div>
                </div>
            </nav>
        )
    }
}
export default Navbar