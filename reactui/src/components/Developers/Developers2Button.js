import React, {Component} from 'react'
import Button from 'react-bootstrap/Button';
import '../NavBars_Menu/Navbar.css';
import axios from "axios";

class Developers2Button extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            users: []
        };
    }



    async componentDidMount() {
       await this.getDataFromBackend();
    }




    async getDataFromBackend(){
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        let url2 = '/api/v1/projects/' + repNum + '/developers'
        axios.get(url2).then(response => response.data)
            .then((data) => {
                this.setState({ users: data })
                console.log(this.state.users)
            })
        // const resp = await result.json();
        await this.setState({data:resp , developerNames:JSON.parse(JSON.stringify(resp))})
        await sessionStorage.setItem("Developers" + repNum, JSON.stringify(this.state.data))
    }

    async storeNames() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        await sessionStorage.setItem('DeveloperNames' + repNum, JSON.stringify(this.state.developerNames))
        console.log("Developer",sessionStorage.getItem('Developers' + repNum))
        console.log("DeveloperNames",sessionStorage.getItem('DeveloperNames' + repNum))
    }

    handleChange = (item) => (event)=> {
        event.preventDefault();
        var tempDevNames = this.state.developerNames;
        var tempDevUsernames = JSON.parse(JSON.stringify(this.state.data));

        for(var i = 0; i < tempDevUsernames.length; i++){
            if(tempDevUsernames[i] === item){
                if(event.target.value !== "") {
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
                {this.state.users.map((user) => {
                    return <li>
                        <a href= {"Developers/" + user.username }target= "_blank">
                            <Button className="Footer" to={user.username.url}
                                    type="button"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        sessionStorage.setItem("CurrentDeveloper", user.username)
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
