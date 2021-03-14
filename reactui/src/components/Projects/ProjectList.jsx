import React, { Component } from 'react';
import axios from "axios";
import "./ProjectList.css";
import {Table} from 'react-bootstrap'
import moment from "moment";

class ProjectList extends Component {

    state = {
        projects: []
    }

    componentDidMount() {
        axios.get('/api/v1/projects')
            .then(response => {
                const projects = response.data
                this.setState({projects})
            })
    }

    render() {
        return (
                <Table striped borded hover>
                    <thead>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Last Activity</th>
                        <th>Data Synched</th>
                        <th>Created</th>
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
                                <td>{moment(projects.created_at).format('lll')}</td>
                                <td>No</td>
                                <td>{moment(projects.created_at).format('ll')}</td>

                            </tr>
                            )}
                    </tbody>
                </Table>
        )
    }
}

export default ProjectList