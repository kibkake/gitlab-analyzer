import React, { Component } from 'react';
import axios from "axios";
import "./ProjectList.css";

class ProjectList extends Component {

    state = {
        projects: []
    }

    componentDidMount() {
        axios.get('http://localhost:8090/api/v1/projects')
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
                    </thead>
                    <tbody>
                        { this.state.projects.map(projects =>
                            <tr>
                                <td>{projects.id}</td>
                                <td>{projects.name}</td>
                                <td>{projects.description}</td>
                            </tr>
                            )}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default ProjectList