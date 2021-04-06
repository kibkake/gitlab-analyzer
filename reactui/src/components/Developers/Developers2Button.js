import React, {Component} from 'react'
import Button from 'react-bootstrap/Button';
import '../NavBars_Menu/Navbar.css';
import axios from "axios";
import moment from "moment";

class Developers2Button extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            users: [],
            devNames: []
        };
    }





    async componentDidMount() {
       await this.getDataFromBackend();
    }



    async getDataFromBackend(){
        var str = window.location.pathname;
        var repNum = str.split("/")[`2`];
        let url2 = '/api/v2/Project/' + repNum + '/Developers/all'
        await fetch(url2)
            .then(response => {
                return response.json();
            })
            .then(d => {
                this.setState({ users: d });
                console.log("developer state", this.state.users);
            })
        await this.setState({ data: await this.state.users.map(({username,id})=> ({username,id}))})
        console.log(this.state.data)

        await sessionStorage.setItem("Developers" + repNum, JSON.stringify(this.state.data))
        this.storeNames()
    }

    async storeNames() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var names = await this.state.data.map(({username})=> ({username}))
        var ids = await this.state.data.map(({id})=> ({id}))
        await sessionStorage.setItem('DeveloperNames' + repNum, JSON.stringify(names))
        await sessionStorage.setItem("DeveloperIds" + repNum, JSON.stringify(ids))

        console.log("Developer",sessionStorage.getItem('Developers' + repNum))
        console.log("DeveloperNames",sessionStorage.getItem('DeveloperNames' + repNum))
    }

    handleChange = (item) => (event)=> {
        event.preventDefault();
        var tempDevNames = this.state.data.username;
        var tempDevUsernames = JSON.parse(JSON.stringify(this.state.data.username));

        for(var i = 0; i < tempDevUsernames.length; i++){
            if(tempDevUsernames[i] === item){
                if(event.target.value !== "") {
                    tempDevNames[i] = event.target.value;
                }else{
                    tempDevNames[i] = item;
                }
            }
        }
        this.setState({developerNames: tempDevNames})
    }

    render(){
        var data = JSON.stringify(this.state.data);
        var DataArray = JSON.parse(data)
        return(
            <ul>
                {this.state.users.map((user) => {
                    return <li>
                        <a href= {"Developers/" + user.username }target= "_blank">
                            <Button className="Footer" to={user.username.url}
                                    type="button"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        sessionStorage.setItem('CurrentDeveloper', JSON.stringify(user))
                                        console.log("Developer",sessionStorage.getItem('CurrentDeveloper'))
                                        this.storeNames()

                                        window.location.href=  window.location.pathname + '/' + user.username + "/summary";
                                    }}>
                                <span >{user.username}</span>
                            </Button>
                        </a>
                        <input className="TextBox"
                               type="text"
                               placeholder= {user.username + '\'s author name'}
                               onChange={this.handleChange(user.username)}  />
                    </li>;
                })}
            </ul>
        );

    }
}

export default Developers2Button;
