import React, { Component } from 'react';
import axios from "axios";
import "./ProjectList.css";

class ProjectList extends Component {

    state = {
        projects: []
    }

    componentDidMount() {
        axios.get('http://localhost:8080/api/v1/projects')
            .then(response => {
                const projects = response.data
                this.setState({projects})
            })
    }

    render() {
        return (
            <div className="container">
                <h3>All Projects</h3>
                <table>
                    <thead>
                        <th>Project ID</th>
                        <th>Project Name</th>
                        <th>Project Description</th>
                        <th>Date Created</th>
                    </thead>
                    <tbody>
                        { this.state.projects.map(projects =>
                            <tr>
                                <button className="Button" to={projects.url}
                                        type="button"
                                        onClick={(e) => {
                                            e.preventDefault();
                                            window.location.href=  window.location.pathname + "/" + projects.id + "/Developers";

                                        }}>{projects.id}</button>

                                <td>{projects.name}</td>
                                <td>{projects.description}</td>
                                <td>{projects.created_at}</td>
                            </tr>
                            )}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default ProjectList