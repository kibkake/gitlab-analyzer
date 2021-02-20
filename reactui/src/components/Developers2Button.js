import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

import {RepoItems} from '../Pages/sampleRepo';
import RepoButton from "./RepoButton";
//import "./RepoButton.css"


class Developers2Button extends Component{
    constructor(props){
        super(props);
        this.state={
            data: []
        };
    }

    componentDidMount() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        let url2 = '/getprojectmembers/' + repNum
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


        console.log(DataArray);

        return(
            //<div> Name: {DataArray} </div>

            <ul>
                <header></header>
                {DataArray.map(item => {
                    return <li>
                        <a href= {"Developers/" + item }target= "_blank">
                            <Button className="Footer" to={item.url}
                                    type="button"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        window.location.href=  window.location.pathname + "/" + item + "/summary";

                                    }}
                            >
                                <span >{item}</span>
                            </Button>
                        </a>
                    </li>;
                })}
            </ul>



        );

    }
}

export default Developers2Button;
