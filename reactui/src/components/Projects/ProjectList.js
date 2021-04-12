import React, { Component } from 'react';
import axios from "axios";
import "./ProjectList.css";
import moment from "moment";
import FormCheck from 'react-bootstrap/FormCheck'
import {MDBBtn, MDBDataTable, MDBInput,  MDBCard, MDBCardBody, MDBCardHeader, MDBTable, MDBTableBody, MDBTableHead  } from 'mdbreact';

//[https://mdbootstrap.com/docs/react/tables/datatables/]
export default class ProjectList extends Component {

    constructor() {
        super();
        this.state = {
            projects: [],
            selectAll: true,
        }
        this.handleSelectAllChange = this.handleSelectAllChange.bind(this);
        this.handleSingleCheckboxChange = this.handleSingleCheckboxChange.bind(this);
        // this.handleRowClick = this.handleRowClick(this);
    }

    componentDidMount() {
        axios.get('/api/v1/projects')
            .then(response => {
                const projects = response.data
                this.setState({projects: projects})
                console.log(this.state.projects)
            })
            .catch((error) => {
                console.error(error);
            })
    }

    // checkAllHandler = () => {
    //     setAreAllChecked(areAllChecked ? false : true);
    // };
    // Handles checkbox behaviour
    handleSelectAllChange = () => {
        const selectAll = !this.state.selectAll;
        this.setState({selectAll: selectAll});

        const changed = this.state.projects.map(function (item) {
            return {
                id: item.id,
                name: item.name,
                description: item.description,
                last_sync_at: item.last_sync_at,
                created_at: item.created_at,
                checked: selectAll,
            }
        });
        this.setState({projects: changed})
        console.log(this.state.projects);
    };

    handleSingleCheckboxChange = id => {
        const changed = this.state.projects.map(function (item) {
            return {
                id: item.id,
                name: item.name,
                description: item.description,
                last_sync_at: item.last_sync_at,
                created_at: item.created_at,
                checked: (item.id === id) ? !item.checked : item.checked
            }
        })

        this.setState({projects: changed});
    };

    // Handles Update button onclick behavior, but the update function in the backend isn't working yet so commented out.

    updateRepos() {
        // this.state.projects.map(item => {
        //     if (item.checked) {
        //         ProjectService.sendUpdateDecision(item.id);
        //     }
        // })
    }
    //
    // handleRowClick(e, props)  {
    //         e.preventDefault();
    //         window.location.href= window.location.pathname + "/" + props + "/Developers";
    // }

    //[https://mdbootstrap.com/support/react/how-to-select-all-check-box-in-mdb-react-table/]
    render() {

        const output = this.state.projects.map(function (item) {
            let currentYear = new Date().getFullYear();
            let dateString = moment(item.created_at).format('ll').replace(", "+currentYear, "")
            return {
                id: <button color="purple" className= "repoSelectBtn" onClick={(e) => { e.preventDefault();
                    window.location.href= window.location.pathname + "/" + item.id + "/Developers";}}>{item.id}</button>,
                name: item.name,
                des: <MDBBtn color="purple" size="sm" onClick={(e) => {e.preventDefault();
                    window.location.href= window.location.pathname + "/" + item.id + "/Developers";}}>{item.description}</MDBBtn>,
                created: dateString,
                updated: "null", //item.lastProjectUpdateAt,
                check: <input type="checkbox" defaultChecked={true}/>,
            }
        })

        const data = {
            columns: [
                {
                    label: 'ID',
                    field: 'id',
                    sort: 'asc',
                    width: 150
                },
                {
                    label: 'Project Name',
                    field: 'name',
                    sort: 'asc',
                    width: 150
                },
                {
                    label: 'Description',
                    field: 'des',
                    sort: 'asc',
                    width: 400
                },
                {
                    label: 'CreatedAt',
                    field: 'created',
                    sort: 'asc',
                    width: 150
                },
                {
                    label: 'UpdatedAt',
                    field: 'updated',
                    sort: 'asc',
                    width: 150
                },
                {
                    label: <FormCheck class="form-check-inline"> <FormCheck.Label>
                        <FormCheck.Input type="checkbox" defaultChecked={this.state.selectAll} onChange={this.handleSelectAllChange}/>
                        Sync Data</FormCheck.Label>
                    </FormCheck>, //<MDBInput label=" " type="checkbox" id="checkbox5"/>,
                    field: 'check',
                    sort: 'asc',
                    width: 50
                },
            ],
            rows: output,
        }

        return (
            <div>
                <div align="center" style={{padding: '20px'}}>
                    <button type="button" className="btn btn-secondary" onClick={this.updateRepos()}>Update Selected Projects</button>
                </div>
                <MDBDataTable hover
                    searchLabel="Search By Project Name/Month"
                    striped
                    small
                    data={data}
                />
            </div>
        );

            // <div>
            //     <div>
            //         <h1>Clear search bar and filter</h1>
            //         <ToolkitProvider
            //             bootstrap4
            //             keyField="name"
            //             data={output}
            //             columns={this.columns}
            //             search
            //         >
            //         <div align="center" style={{padding: '20px'}}>
            //             <button type="button" className="btn btn-secondary" onClick={this.updateRepos()}>Update Selected Projects</button>
            //         </div>
            //         <Table striped borded hover style={{margin: 'auto', width: '85%'}}>
            //             <thead>
            //             <tr>
            //                 <th>ID</th>
            //                 <th>Name</th>
            //                 <th>Description</th>
            //                 <th>Last Updated</th>
            //                 <th><FormCheck class="form-check-inline">
            //                     <FormCheck.Label>
            //                         <FormCheck.Input type="checkbox" defaultChecked={this.state.selectAll} onChange={this.handleSelectAllChange}/>
            //                         Sync Data</FormCheck.Label>
            //                 </FormCheck>
            //                 </th>
            //                 <th>Created</th>
            //             </tr>
            //             </thead>
            //             <tbody>
            //             { this.state.projects.map(projects =>
            //                 <tr>
            //                     <button className="Button" to={projects.url}
            //                             type="button"
            //                             onClick={(e) => {
            //                                 e.preventDefault();
            //                                 window.location.href= window.location.pathname + "/" + projects.id + "/Developers";
            //
            //                             }}>{projects.id}</button>
            //
            //                     <td>{projects.name}</td>
            //                     <td>{projects.description}</td>
            //                     <td>{(projects.last_sync_at === "never") ? "Not Available" : moment(projects.last_sync_at).format('lll')}</td>
            //                     <td>
            //                         <input type="checkbox" defaultChecked={!projects.checked}
            //                                checked={projects.checked}
            //                                onChange={() => this.handleSingleCheckboxChange(projects.id)}/>
            //                     </td>
            //                     <td>{moment(projects.created_at).format('ll')}</td>
            //
            //                 </tr>
            //             )}
            //             </tbody>
            //         </Table>
            //         </ToolkitProvider>
            // </div>
        }
}