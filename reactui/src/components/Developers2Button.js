import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import './Navbar.css';

class Developers2Button extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            developerNames: [],
            submitted: false
        };
    }

    async componentDidMount() {
       await this.getDataFromBackend();
    }

    async getDataFromBackend(){
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        let url2 = '/api/v1/getmembers/' + repNum
        await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then((result)=> {
            result.json().then((resp) => {
                this.setState({data:resp , developerNames:JSON.parse(JSON.stringify(resp))})
                sessionStorage.setItem("Developers", JSON.stringify(this.state.data))
            })
        })
    }

    storeNames() {
        sessionStorage.setItem('DeveloperNames', JSON.stringify(this.state.developerNames))
    }

    handleChange = (item) => (event) => {
        event.preventDefault();
        var tempDevNames = this.state.developerNames;
        var tempDevUsernames = JSON.parse(JSON.stringify(this.state.data));

        for(var i = 0; i < tempDevUsernames.length; i++){
            if(tempDevUsernames[i] === item){
                if(event.target.value != "") {
                    tempDevNames[i] = event.target.value;
                }else{
                    tempDevNames[i] = item;
                }
            }
        }
        this.setState({davelopernames: tempDevNames})
    }

    render(){
        var data = JSON.stringify(this.state.data);
        var DataArray = JSON.parse(data)

        return(

            <ul>
                <header className="header">Any data associated with the developer names will also be pulled(after the back end is updated)</header>
                {DataArray.map(item => {
                    return <li>
                        <a href= {"Developers/" + item }target= "_blank">
                            <Button className="Footer" to={item.url}
                                    type="button"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        sessionStorage.setItem("CurrentDeveloper", item)
                                        this.storeNames()

                                        window.location.href=  window.location.pathname + '/' + item + "/summary";
                                    }}>
                                <span >{item}</span>
                            </Button>
                        </a>
                        <input className="TextBox"
                               type="text"
                               placeholder= {item + '\'s name'}
                               onChange={this.handleChange(item)}  />
                    </li>;
                })}
            </ul>
        );

    }
}

export default Developers2Button;
