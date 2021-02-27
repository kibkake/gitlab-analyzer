import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import {AiOutlineHome} from 'react-icons/ai';
import './Navbar.css';
import {MenuItems} from "./MenuItem_Developers";
import axios from "axios";

export var rep = window.location.pathname.split("/")[2]
export var user = window.location.pathname.split("/")[4]


export class Navbar_Developers extends Component{

    constructor(props) {
        super(props);
        this.state = {
            parentdata:[
                {
                    title: 'Summary',
                    url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers/' + window.location.pathname.split("/")[4] + '/summary',
                    cName: 'nav-links'
                },
                {
                    title: 'Commits',
                    url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers/' + window.location.pathname.split("/")[4] + '/commits',
                    cName: 'nav-links'
                },
                {
                    title: 'Merge Requests',
                    url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers/' + window.location.pathname.split("/")[4] + '/codediff',
                    cName: 'nav-links'
                },
                {
                    title: 'Comments',
                    url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers/' + window.location.pathname.split("/")[4] + '/comments',
                    cName: 'nav-links'
                },
                {
                    title: 'Developers',
                    url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers',
                    cName: 'nav-links'
                },
            ],
            givenDevName : this.props.devName
        }
    }

    componentDidMount() {}

    componentDidUpdate(prevProps){
        if(this.props.devName !== prevProps.devName){
            this.setState({parentdata: [
                    {
                        title: 'Summary',
                        url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers/' +  this.props.devName + '/summary',
                        cName: 'nav-links'
                    },
                    {
                        title: 'Commits',
                        url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers/' +  this.props.devName + '/commits',
                        cName: 'nav-links'
                    },
                    {
                        title: 'Merge Requests',
                        url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers/' +  this.props.devName + '/codediff',
                        cName: 'nav-links'
                    },
                    {
                        title: 'Comments',
                        url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers/' +  this.props.devName + '/comments',
                        cName: 'nav-links'
                    },
                    {
                        title: 'Developers',
                        url: '/Repo/' + window.location.pathname.split("/")[2] + '/Developers',
                        cName: 'nav-links'
                    },
                ]});
        }
    }

    render(){
        //console.log(something)
        const {parentdata} = this.state;
        return(
            <nav className="navbarItems">
                <div>
                    <Link to='/Developers' className='nav-icon'>
                        <AiOutlineHome size={30} />
                    </Link>
                </div>
                <ul className='navMenu'>
                    {this.state.parentdata.map((item,index)=>{
                        return(
                            <li key={index} className={item.cName}>
                                <Link to={{pathname: item.url + '/'}}>
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