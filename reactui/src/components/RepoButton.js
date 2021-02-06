import React, {Component} from 'react'
import {Link} from 'react-router-dom';

import {RepoItems} from '../Pages/sampleRepo';
import "./RepoButton.css"


class RepoButton extends Component{
    render(){
        return(
            <ul className='navMenu'>
                {RepoItems.map((item,index)=>{
                    return(
                        <li key={index} className={item.cName}>
                            <Link to={item.url}>
                                {item.icon}
                                {item.name}
                                <span>{item.owner.name}</span>
                            </Link>
                        </li>
                    )
                })}
            </ul>
        )

    }




}

export default RepoButton;