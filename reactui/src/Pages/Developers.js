import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import {AiOutlineHome} from 'react-icons/ai';
import '../components/Navbar.css';
import {MenuItems} from "../components/MenuItem_Developers";

class Developers extends Component{
    render(){
        return(

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
                <div>
                </div>
            </nav>

        )
    }
}
export default Developers;