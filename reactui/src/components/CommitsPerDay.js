import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

import {RepoItems} from '../Pages/sampleRepo';
import RepoButton from "./RepoButton";
//import "./RepoButton.css"


class CommitsPerDay extends Component{
    constructor(props){
        super(props);
        this.state={
            data: []
        };
    }
//http://localhost:3000/Repo/6/Developers/user2/commits/2021-01-24

    //http://localhost:8080/api/v1/projects/6/Commits/arahilin/2021-01-24/2021-01-24
    componentDidMount() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var userName = str.split("/")[4];
        var date = str.split("/")[6];


        let url2 = 'http://localhost:8080/api/v1/projects/' + repNum + '/Commits/' + userName + '/' + date + "/" + date
        fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then((result)=> {
            result.json().then((resp) => {
                this.setState({data:resp})
            })
        })

    }

    render(){
        var str = window.location.pathname;
        var strArr = str.split("/");
        var data = JSON.stringify(this.state.data);
        var DataArray = JSON.parse(data)

        return(
            //<div> Name: {DataArray} </div>

            <ul>
                <header></header>
                {DataArray.map(item => {
                    return <li>
                        <a href= {"Developers/" + item }target= "_blank">
                            <Button className="Footer" to={item.id}
                                    type="button"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        window.location.href=  window.location.pathname + "/" + item.id;

                                    }}
                            >
                                <span >{item.id}</span>
                            </Button>
                        </a>
                    </li>;
                })}
            </ul>



        );

    }
}

export default CommitsPerDay;