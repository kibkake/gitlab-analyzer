import React, { Component } from 'react';
import axios from "axios";
import {Table} from 'react-bootstrap'
import "./ProjectList.css";
import moment from "moment";
import ProjectService from "../../Service/ProjectService";

class ProjectList extends Component {

    constructor() {
        super();
        this.state = {
            projects: [],
            selectAll: false,
            checked: []
        }
        this.handleSelectAllChange = this.handleSelectAllChange.bind(this);
        this.handleSingleCheckboxChange = this.handleSingleCheckboxChange.bind(this);
    }

    componentDidMount() {
        axios.get('/api/v1/projects')
            .then(response => {
                const projects = response.data
                this.setState({projects: projects})
            })
            .catch((error) => {
                console.error(error);
            })
    }

    //Handles checkbox behaviour [https://stackoverflow.com/questions/50495130/react-table-select-all-select-box]
    handleSelectAllChange = () => {
        var selectAll = !this.state.selectAll;
        this.setState({ selectAll: selectAll });
        var checkedCopy = [];
        this.state.projects.forEach(function(item, index) {
            checkedCopy.push(selectAll);
        });
        this.setState({checked: checkedCopy});
    };

    handleSingleCheckboxChange = id => {
        console.log(id);

        const change = this.state.projects.map(function(item)  {
            return {}
            if (item.id === id) {
                if (!item.checked) {
                    this.setState({ selectAll: false });
                }
                item.checked = !item.checked;

            }
        })
        this.setState({projects: change});
    };

    // Handles Update button onclick behavior
    updateRepos() {
        // this.state.projects.map(item => {
        //     if (item.checked) {
        //         ProjectService.sendUpdateDecision(item.id);
        //     }
        // })
    }

    render() {
        console.log(this.state.projects);

        return (
            <div>
                <div align="end">
                     <button type="button" className="btn btn-secondary" onClick={this.updateRepos()}>Update Selected Projects</button>
                </div>
                <Table striped borded hover>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Last Updated</th>
                        <th>Sync Data
                            <input type="checkbox"
                            onChange={this.handleSelectAllChange}/>
                        </th>
                        <th>Created</th>
                    </tr>
                    </thead>
                    <tbody>
                        { this.state.projects.map(projects =>
                            <tr>
                                <button className="Button" to={projects.url}
                                        type="button"
                                        onClick={(e) => {
                                            e.preventDefault();
                                            window.location.href= window.location.pathname + "/" + projects.id + "/Developers";

                                        }}>{projects.id}</button>

                                <td>{projects.name}</td>
                                <td>{projects.description}</td>
                                <td>{moment(projects.last_sync_at).format('lll')}</td>
                                <td>
                                    <input type="checkbox" defaultChecked={!projects.checked}
                                    checked={projects.checked}
                                    onChange={() => this.handleSingleCheckboxChange(projects.id)}/>
                                </td>
                                <td>{moment(projects.created_at).format('ll')}</td>

                            </tr>
                            )}
                    </tbody>
                </Table>
            </div>
        )
    }
}

export default ProjectList