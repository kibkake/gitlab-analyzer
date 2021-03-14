import React, { Component } from 'react';
import axios from "axios";
import {Table} from 'react-bootstrap'
import "./ProjectList.css";
import moment from "moment";
import ProjectService from "../../Service/ProjectService";

// import { DataGrid } from '@material-ui/data-grid';
import Checkbox from '@material-ui/core/Checkbox';
import {Book} from "@material-ui/icons";
// import { Checkbox, Table } from 'semantic-ui-react';
// import {CheckBox} from "@material-ui/icons";

class ProjectList extends Component {

    constructor() {
        super();
        this.state = {
            projects: [],
            selectAll: false
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSingleCheckboxChange = this.handleSingleCheckboxChange.bind(this);
    }

    componentDidMount() {
        axios.get('/api/v1/projects')
            .then(response => {
                const projects = response.data
                this.setState({ projects})
            })
    }

    //Handles checkbox behaviour
    handleChange = () => {
        var selectAll = !this.state.selectAll;
        this.setState({ projects: {checked:selectAll}, selectAll: selectAll });
    };

    handleSingleCheckboxChange = id => {
        console.log(id);
        // (this.state.project.id = id
        var checkedCopy = this.state.checked;
        checkedCopy[id] = !this.state.checked[id];
        if (checkedCopy[id] === false) {
            this.setState({ selectAll: false });
        }
    };

    findProjectById = (projects, id)=>{
        this.state.projects.find(el => el.id === id);
        this.setState()
    }

    // updateRepos = {
    //     this.state.project.map(function () forEach(checked: true) =>
    //     ProjectService.sendUpdateDecision(projects.id);
    // }

    render() {
        console.log(this.state.projects);


        return (
            <div>
            <button type="button" className="btn btn-secondary" onClick={this.updateRepos()}>Update Selected Project</button>
                <Table striped borded hover>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Last Updated</th>
                        <th>Sync Data
                            <input type="checkbox"
                            onChange={this.handleChange}/>
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
                                    <input type="checkbox" defaultChecked={!this.state.projects.checked}
                                    checked={this.state.projects.checked}
                                    onChange={() => this.handleSingleCheckboxChange(this.states.projects.id)}/>
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