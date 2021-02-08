import React, {Component} from 'react'
import {MenuItems} from "./MenuItems";
import {Link} from 'react-router-dom';

import './Navbar.css';

class Navbar extends Component{
    render(){
        return(
            <nav className="navbarItems">
                <div className="navbarContainer">

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
            </nav>
        )
    }
}
export default Navbar