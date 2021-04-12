


import React, {Component, useEffect, useState} from 'react';
import axios from "axios";
import "./ProjectList.css";
import moment from "moment";
import FormCheck from 'react-bootstrap/FormCheck'
import {MDBBtn, MDBDataTable, MDBInput,  MDBCard, MDBCardBody, MDBCardHeader, MDBTable, MDBTableBody, MDBTableHead  } from 'mdbreact';
import {ProgressBar} from "react-bootstrap";
import UpdatePopup from "./UpdatePopUp";
import {CircularProgress, Modal} from "@material-ui/core";
import Button from "react-bootstrap/Button";

//[https://mdbootstrap.com/docs/react/tables/datatables/]
const ProjectList = props => {
    const [projects, getProjects]= useState([]);
    const [areAllChecked, setAreAllChecked] = useState(false)
    const [isUpdating, setIsUpdating] = useState(false);
    const [isUpdated, setIsUpdated] = useState(false);
    const [checked, setChecked] = useState([]);

    useEffect(()=>{
        axios.get('/api/v1/projects')
            .then(response => {
                getProjects(response.data)
            })
            .catch((error) => {
                console.error(error);
            })
    }, [projects])

    useEffect(() => {
        axios.get('/api/v1/projects/updated')
            .then(response => {
                setIsUpdated(response.data)
                console.log(isUpdated)
                setIsUpdating(false)
            })
            .catch((error) => {
                console.error(error);
            })
    })

    //[https://mdbootstrap.com/support/react/how-to-select-all-check-box-in-mdb-react-table/]
    const checkAllHandler = () => {
        setAreAllChecked(!areAllChecked);
    };

    const checkHandler = id => {
        let currentChecked = [...checked];

        const changed = currentChecked.map((checkbox, index) => {
            return {
                index: index,
                checked: ((index+1) === id) ? !checkbox.checked : checkbox.checked
            }
        })

        setChecked(changed);
        console.log(checked)
    }



    // const handleSelectAllChange = () => {
    //     const newStatus = !areAllChecked
    //
    //     setAreAllChecked(newStatus)
    //     const array = projects.map((item, index) => ({...item, checked: newStatus}))
    //     const currentChecked = array.map(({id, checked}) => ({id, checked}))
    //     setChecked(currentChecked);
    // }

    const handleSingleCheckboxChange = id => {
        const array = projects.map((item, index) => ({checked: true, index: index}))
        const currentChecked = array.map(({id, checked}) => ({id, checked}))
        const changedStatus = currentChecked.map((item) => {
            return {
                id: item.id,
                checked:(item.id === id) ? !item.checked : item.checked
            }
        })

        setChecked(changedStatus);
        console.log(checked)

        // let currentChecked = [...checked];
        // const array = projects.map((item, index) => ({...item, checked: checked.checked}))
        // const currentChecked = array.map(({id, checked}) => ({id, checked}))
        // const changedStatus = currentChecked.map((item) => {
        //    return {
        //         id: item.id,
        //        checked:(item.id === id) ? !item.checked : item.checked
        //    }
        // })
        //
        // setChecked(changedStatus);
        // console.log(checked)
    }

    const handleUpdateRepos = () => {
        const array = projects.map((item, index) => ({...item, checked: checked.checked}))
        const currentChecked = array.map(({id, checked}) => ({id, checked}))
        setChecked(currentChecked);
        console.log(checked)

        const projectIdToUpdate = checked.reduce((result, {id, checked}) => [...result, ...checked ? [id] : []], []);
        console.log(projectIdToUpdate);

        axios.post("/api/v1/setProjectInfoWithSettings", projectIdToUpdate, {
            headers: {'Content-Type': 'application/json'}})
            .then(response => {
                console.log("Id array " + projectIdToUpdate + " sent successfully")
            })
            .catch((error) => {
                console.error(error);
            })
    }

    const openUpdate =() => {
        setIsUpdating(true)
    }

    const closeUpdate =() => {
        setIsUpdating(false)
    }

    //[https://mdbootstrap.com/support/react/how-to-select-all-check-box-in-mdb-react-table/]
    const output = projects.map(function (item, index) {
        let currentYear = new Date().getFullYear();
        let dateStringCreated = moment(item.created_at).format('ll').replace(", " + currentYear, "")
        let dateStringUpdated = moment(item.last_sync_at).format('lll').replace(currentYear, "")
        return {
            check: <input type="checkbox" defaultChecked={true} checked={checked.checked} onClick={() => checkHandler(index)}/>,
            ///checked={checked.checked} onClick={() => handleSingleCheckboxChange(item.id)}/>,
            // <MDBInput label=' ' checked={this.state.selectAll ? true : checked[1].checked} type='checkbox' id='checkbox7' onChange={() => checkHandler(2)}/>,
            //<MDBInput label=' ' checked={areAllChecked  ? true : checked[1].checked} type='checkbox' id='checkbox7'  onClick={() => checkHandler(2)}/>
            id: <button color="purple" onClick={(e) => { e.preventDefault();
                window.location.href= window.location.pathname + "/" + item.id + "/Developers";}}>{item.id}</button>,
            name: item.name,
            des: <MDBBtn color="purple" size="sm" onClick={(e) => {e.preventDefault();
                window.location.href= window.location.pathname + "/" + item.id + "/Developers";}}>{item.description}</MDBBtn>,
            created: dateStringCreated,
            updated: (item.last_sync_at === "never" || item.last_sync_at === null) ? "Not Available" : dateStringUpdated,
            syncing: (isUpdating && isUpdated ) ? <CircularProgress/> : <ProgressBar animated now={45} />,
        }
    })
    // console.log (this.state.checked)
    const data = {
        columns: [
            {label: 'ID', field: 'id', sort: 'asc', width: 150},
            {label: 'Project Name', field: 'name', sort: 'asc', width: 150},
            {label: 'Description', field: 'des', sort: 'asc', width: 400},
            {label: 'Created', field: 'created', sort: 'asc', width: 150},
            {label: 'Updated', field: 'updated', sort: 'asc', width: 150},
            {label: 'Sync Status', field: 'syncing', sort: 'asc', width: 150},
            {label: 'Select',
                // <input label=" " type="checkbox" defaultChecked={areAllChecked} onClick={checkAllHandler}/>,
                // <FormCheck class="form-check-inline"> <FormCheck.Label>
                //     <FormCheck.Input type="checkbox" defaultChecked={this.state.selectAll} onChange={this.handleSelectAllChange}/>
                // </FormCheck.Label>
                // </FormCheck>, //
                field: 'check', sort: 'asc', width: 150},
        ],
        rows: output,
    }
    return (
        <div>
            <div align="center" style={{padding: '20px'}}>
                <ul> </ul>
                <button type="button" className="btn btn-secondary" onClick={() => {handleUpdateRepos(); openUpdate();}}>Update Selected Projects</button>
                {/*<Modal show={isUpdating} onHide={closeUpdate()}>*/}
                {/*    <Modal.Header>*/}
                {/*        <Modal.Title>Updating in Progress...</Modal.Title>*/}
                {/*    </Modal.Header>*/}
                {/*    <Modal.Body> <CircularProgress/> </Modal.Body>*/}
                {/*    /!*<Modal.Footer>*!/*/}
                {/*    /!*    <Button variant="primary" onClick={setUpdatePopup(false)}>*!/*/}
                {/*    /!*        CANCEL UPDATE*!/*/}
                {/*    /!*    </Button>*!/*/}
                {/*    /!*</Modal.Footer>*!/*/}
                {/*</Modal>*/}

            </div>
            <MDBDataTable hover btn sortable pagesAmount={20}
                          searchLabel="Search By Project Name/Month"
                          small
                          data={data}
            />
        </div>
    );
}


export default ProjectList;
