import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import {AiOutlineHome} from 'react-icons/ai';
import './Navbar.css';
import {MenuItems} from "./MenuItem_Developers";

export var rep = window.location.pathname.split("/")[2]
export var user = window.location.pathname.split("/")[4]


export class Navbar_Developers extends Component{


    render(){
        //console.log(something)
        return(
            <nav className="navbarItems">
                <div>
                    <Link to='/Developers' className='nav-icon'>
                        <AiOutlineHome size={30} />
                    </Link>
                </div>
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
                <div>
                </div>
            </nav>

        )
    }
}
export default Navbar_Developers;