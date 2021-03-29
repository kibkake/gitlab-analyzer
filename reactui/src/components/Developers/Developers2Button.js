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
        axios.get(url2).then(response => {
                this.setState({ users: response.data })
                console.log("dev objects", this.state.users)
            })


        await sessionStorage.setItem("Developers" + repNum, JSON.stringify(this.state.users))
        console.log("devs from storage", JSON.parse(this.state.users));

        // console.log("stingify usernames", JSON.stringify(this.state.username))
        // console.log("Developer", JSON.parse(sessionStorage.getItem('Developers' + repNum)))
        // console.log(JSON.parse(JSON.stringify(this.state.users)));
        this.storeNames()
    }

    storeNames() {
        console.log("storing names")
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var devNames = "";
        this.state.users.forEach(dev => console.log(JSON.stringify(dev.username)))
        // console.log("devNames", devNames);
        // await sessionStorage.setItem('DeveloperNames' + repNum, JSON.stringify(devNames))
        //
        //
        // console.log("Developer",sessionStorage.getItem('Developers' + repNum))
        // console.log("DeveloperNames",sessionStorage.getItem('DeveloperNames' + repNum))
    }

    handleChange = (item) => (event)=> {
        console.log("item", item)
        event.preventDefault();
        var tempDevNames = this.state.users;
        var tempDevUsernames = JSON.parse(JSON.stringify(this.state.users.username));

        for(var i = 0; i < tempDevUsernames.length; i++){
            if(tempDevUsernames[i] === item){
                if(event.target.value !== "") {
                    tempDevNames[i] = event.target.value;
                }else{
                    tempDevNames[i] = item;
                }
            }
        }
        this.setState({developernames: tempDevNames})
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
