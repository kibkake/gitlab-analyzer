import React,{ Component } from "react";
import ProfileService from "../Service/ProfileService";

class Form extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username:'',
            password:'',
            response: []
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange = ({ target }) => {
        this.setState({ [target.name]: target.value });
    };

    handleSubmit = (event) => {
        this.state.response = ProfileService.checkUserCredentials(this.state.username, this.state.password);
        alert('Login: ' + this.state.response);
        event.preventDefault();
    };


    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Username:
                    <input name="username" type="text" value={this.state.username} onChange={this.handleChange} />
                </label>
                <label>
                    Password:
                    <input name="password" type="password" value={this.state.password} onChange={this.handleChange} />
                </label>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        );
    }
}export default Form;