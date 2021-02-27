import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

class Developers2Button extends Component{
    constructor(props){
        super(props);
        this.state={
            data: []
        };
    }

    async componentDidMount() {
        this.getDataFromBackend();
    }

    async getDataFromBackend(){
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        let url2 = '/getprojectmembers/' + repNum
        await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then((result)=> {
            result.json().then((resp) => {
                this.setState({data:resp})
                sessionStorage.setItem("Developers", JSON.stringify(this.state.data))
            })
        })
    }

    render(){
        var data = JSON.stringify(this.state.data);
        var DataArray = JSON.parse(data)
        return(
            <ul>
                <header></header>
                {DataArray.map(item => {
                    return <li>
                        <a href= {"Developers/" + item }target= "_blank">
                            <Button className="Footer" to={item.url}
                                    type="button"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        sessionStorage.setItem("CurrentDeveloper", item)
                                        window.location.href=  window.location.pathname + '/' + item + "/summary";
                                    }}>
                                <span >{item}</span>
                            </Button>
                            <input className="TextBox"
                                type="text"
                                placeholder= "name of developer"
                                value={this.state.value}
                                onChange={this.handleChange}
                            />
                        </a>
                    </li>;
                })}
            </ul>
        );

    }
}

export default Developers2Button;
