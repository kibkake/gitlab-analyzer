import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

import {RepoItems} from '../Pages/sampleRepo';
import "./RepoButton.css"


class RepoButton extends Component{
    render(){
        return(
            <ul className='navMenu'>
                {RepoItems.map((item,index)=>{
                    return(
                        <li key={index} className={item.cName}>
                            <Button variant="outline" className="repoButton" to={item.url}>
                                {item.icon}
                                <span className="projectTag">Project Name:</span>
                                <span className="projectName">{item.name}</span>
                                <span className="ownerTag">Owner:</span>
                                <span className="ownerName">{item.owner.name}</span>
                            </Button>
                        </li>
                    )
                })}
            </ul>
        )

    }




}

export default RepoButton;