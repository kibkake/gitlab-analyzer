// import React, {Component} from 'react'
import React, {useState} from 'react'

import {MenuItems} from "./MenuItems";
import {Link} from 'react-router-dom';
import {AiOutlineHome} from 'react-icons/ai';

import './Navbar.css';
import * as HiIcons from "react-icons/hi";
import * as AIIcons from "react-icons/ai";
import {IoSettingsOutline} from "react-icons/io5";
import {CgProfile} from "react-icons/cg";
import Dropdown from "./Dropdown";
import * as GoIcons from "react-icons/go";

function Navbar1() {
    const [click, setClick] = useState(false);
    const [dropdown, setDropdown] = useState(false);

    const handleClick = () => setClick(!click);
    const closeMobileMenu = () => setClick(false);

    const onMouseEnter = () => {
        if (window.innerWidth < 960) {
            setDropdown(false);
        } else {
            setDropdown(true);
        }
    };

    const onMouseLeave = () => {
        if (window.innerWidth < 960) {
            setDropdown(false);
        } else {
            setDropdown(false);
        }
    };

    const extendElement = () => {
        dropdown ? setDropdown(false) : setDropdown(true);
    }



    // render() {

        return (
            <nav className="navbarItems">
                <div>

                    <Link to='/Home' className='nav-icon'>
                        <AiOutlineHome size={30}/>
                    </Link>
                </div>
                <ul className='navMenu'>

                    <li  className='nav-links'>
                        <Link to='/repo'>
                            <GoIcons.GoRepo/>
                            <span>Repositories</span>
                        </Link>
                    </li>

                    <li className='nav-links'
                        onMouseEnter={onMouseEnter}
                        onMouseLeave={onMouseLeave}
                        onClick={extendElement}>
                        <Link to='/developers'
                              onClick={closeMobileMenu}
                             >
                            <HiIcons.HiOutlineUserGroup/>
                            Developers<i className='fas fa-caret-down'/>
                        </Link>
                        {dropdown && <Dropdown onCloseMobileMenu={closeMobileMenu}/>}
                    </li>

                    <li className='nav-links'>
                        <Link to='/export'>
                            <AIIcons.AiOutlineExport/>
                            <span>Export</span>
                        </Link>
                    </li>
                    <li className='nav-links'>
                        <Link to='/settings'>
                            <IoSettingsOutline/>
                            <span>Setting</span>
                        </Link>
                    </li>
                    <li className='nav-links'>
                        <Link to='/#'>
                            <CgProfile/>
                            <span>Profile</span>
                        </Link>
                    </li>
                </ul>
                <div>
                </div>
            </nav>
        )
  //  }
}
export default Navbar1