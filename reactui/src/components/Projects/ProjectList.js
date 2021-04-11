import React, { Component } from 'react';
import axios from "axios";
import "./ProjectList.css";
import moment from "moment";
import FormCheck from 'react-bootstrap/FormCheck'
import {MDBBtn, MDBDataTable, MDBInput,  MDBCard, MDBCardBody, MDBCardHeader, MDBTable, MDBTableBody, MDBTableHead  } from 'mdbreact';
import {ProgressBar} from "react-bootstrap";
import UpdatePopup from "./UpdatePopUp";

//[https://mdbootstrap.com/docs/react/tables/datatables/]
export default class ProjectList extends Component {

    constructor() {
        super();
        this.state = {
            projects: [],
            checkedStatus: [],
            selectAll: true,
            updating: false,
        }
        this.handleSelectAllChange = this.handleSelectAllChange.bind(this);
        this.handleSingleCheckboxChange = this.handleSingleCheckboxChange.bind(this);
    }

    componentDidMount() {
        axios.get('/api/v1/projects')
            .then(response => {
                const projects = response.data
                this.setState({projects: projects})
                console.log(this.state.projects)

                const array = this.state.projects.map(item => ({...item, checked: this.state.selectAll}))
                const checkedStatusArray = array.map(({id, checked}) => ({id, checked}));
                this.setState({checkedStatus: checkedStatusArray})
                console.log(this.state.checkedStatus)
            })
            .catch((error) => {
                console.error(error);
            })
    }

    // Handles checkbox behaviour
    handleSelectAllChange = () => {
        const selectAll = !this.state.selectAll;
        this.setState({selectAll: selectAll});
        console.log(this.state.selectAll);

        const allChecked = this.state.checkedStatus.map(item => ({...item, checked: selectAll}))
        console.log(allChecked)
        this.setState({checkedStatus: allChecked})
        console.log(this.state.checkedStatus);
    };

    handleSingleCheckboxChange = id => {
        const singleChecked = this.state.checkedStatus.map(function (item) {
            return {
                id: item.id,
                checked: (item.id === id) ? !item.checked : item.checked
            }
        })
        this.setState({checkedStatus: singleChecked});
    };

    // Handles Update button onclick behavior
    handleUpdateRepos= () => {
        const projectIdToUpdate = this.state.checkedStatus
            .reduce((result, {id, checked}) => [...result, ...checked ? [id] : []], []);
        console.log(projectIdToUpdate);

        axios.post("/api/v1/setProjectInfoWithSettings", projectIdToUpdate, {
            headers: {'Content-Type': 'application/json'}
        }).then(response => {
            console.log("Id array sent successfully")
        }).catch((error) => {
            console.error(error);
        })
    }

    triggerUpdatePopup = (boolean) => {
        this.setState({updating: boolean})
    }

    //[https://mdbootstrap.com/support/react/how-to-select-all-check-box-in-mdb-react-table/]
    render() {
        const selected = this.state.selectAll
        const output = this.state.projects.map(function (item) {
            let currentYear = new Date().getFullYear();
            let dateStringCreated = moment(item.created_at).format('ll').replace(", "+currentYear, "")
            let dateStringUpdated = moment(item.last_sync_at).format('lll').replace(currentYear, "")
            return {
                check: <input type="checkbox" defaultChecked={selected}
                            onChange={() => this.handleSingleCheckboxChange(item.id)}/>,
                // <MDBInput label=' ' checked={this.state.selectAll ? true : checked[1].checked} type='checkbox' id='checkbox7' onChange={() => checkHandler(2)}/>,

                //<MDBInput label=' ' checked={areAllChecked  ? true : checked[1].checked} type='checkbox' id='checkbox7'  onClick={() => checkHandler(2)}/>
                id: <button color="purple" onClick={(e) => { e.preventDefault();
                    window.location.href= window.location.pathname + "/" + item.id + "/Developers";}}>{item.id}</button>,
                name: item.name,
                des: <MDBBtn color="purple" size="sm" onClick={(e) => {e.preventDefault();
                    window.location.href= window.location.pathname + "/" + item.id + "/Developers";}}>{item.description}</MDBBtn>,
                created: dateStringCreated,
                updated: (item.last_sync_at === "never" || item.last_sync_at === null) ? "Not Available" : dateStringUpdated,
                syncing: <ProgressBar animated now={45} />,
            }
        })

        // console.log (this.state.checked)

        const data = {
            columns: [
                {label: 'ID', field: 'id', sort: 'asc', width: 150},
                {label: 'Project Name', field: 'name', sort: 'asc', width: 150},
                {label: 'Description', field: 'des', sort: 'asc', width: 400},
                {label: 'CreatedAt', field: 'created', sort: 'asc', width: 150},
                {label: 'UpdatedAt', field: 'updated', sort: 'asc', width: 150},
                {label: 'Sync Progress', field: 'syncing', sort: 'asc', width: 150},
                {label: <MDBInput label=" " type="checkbox" valueDefault={this.state.selectAll} checked={!this.state.selectAll} onChange={this.handleSelectAllChange}/>,
                    // <FormCheck class="form-check-inline"> <FormCheck.Label>
                    //     <FormCheck.Input type="checkbox" defaultChecked={this.state.selectAll} onChange={this.handleSelectAllChange}/>
                    // </FormCheck.Label>
                    // </FormCheck>, //
                    field: 'check', sort: 'asc', width: 10},
            ],
            rows: output,
        }

        return (
            <div>
                <div align="center" style={{padding: '20px'}}>
                    <button type="button" className="btn btn-secondary" onClick={() => {this.handleUpdateRepos(); this.triggerUpdatePopup(true);}}>Update Selected Projects</button>
                    {/*<UpdatePopup closeOnOutsideClick={true} trigger={this.state.updating} setTrigger={this.triggerUpdatePopup(true)}/>*/}
                </div>
                <MDBDataTable hover btn sortable pagesAmount={20}
                    searchLabel="Search By Project Name/Month"
                    small
                    data={data}
                />
            </div>
        );
    }
}