import React, {Component} from 'react'
import Button from 'react-bootstrap/Button';
import '../NavBars_Menu/Navbar.css';

class Developers2Button extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            developerNames: []
        };
    }

    async componentDidMount() {
       await this.getDataFromBackend();
    }

    async getDataFromBackend(){
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        let url2 = '/api/v1/getusernames/' + repNum
        const result = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        const resp = await result.json();
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
                               placeholder= {item + '\'s author name'}
                               onChange={this.handleChange(item)}  />
                    </li>;
                })}
            </ul>
        );

    }
}

export default Developers2Button;
