import React, { useState } from 'react';
import { MenuItems } from './MenuItem_Dropdown';
import './Dropdown.css';
import { Link } from 'react-router-dom';

//[https://www.youtube.com/watch?v=T2MhVxJxsL0]
function Dropdown(props) {
    const [click, setClick] = useState(false);

    const handleClick = () => setClick(!click);

    return (
        <>
            <ul
                onClick={props.onCloseMobileMenu}
                className={click ? 'dropdown-menu clicked' : 'dropdown-menu'}
            >
                {MenuItems.map((item, index) => {
                    return (
                        <li key={index}>
                            <Link
                                className={item.cName}
                                to={item.path}
                                onClick={() => setClick(false)}
                            >
                                {item.title}
                            </Link>
                        </li>
                    );
                })}
            </ul>
        </>
    );
}

export default Dropdown;