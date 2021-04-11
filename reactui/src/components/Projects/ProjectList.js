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
import UpdateModal from "./UpdateModal";

//[https://mdbootstrap.com/docs/react/tables/datatables/]
export default function ProjectList (){

    const [projects,getProjects]= useState([]);
    const [checked, setChecked] = useState([]);
    const [updatePopup, setUpdatePopup] = useState(false);
    const [areAllChecked, setAreAllChecked] = useState(false)

    //TODO: prevent infinite rerendering
    //[https://dmitripavlutin.com/react-useeffect-infinite-loop/#:~:text=The%20infinite%20loop%20is%20fixed,callback%2C%20dependencies)%20dependencies%20argument.&text=Adding%20%5Bvalue%5D%20as%20a%20dependency,so%20solves%20the%20infinite%20loop.]
    useEffect(()=>{
        axios.get('/api/v1/projects')
            .then(response => {
                getProjects(response.data)
                console.log(projects)

                const array = projects.map(item => ({...item, checked: areAllChecked}))
                const checkedStatusArray = array.map(({id, checked}) => ({id, checked}))
                setChecked(checkedStatusArray)
            })
            .catch((error) => {
                console.error(error);
            })
    })

    //[https://mdbootstrap.com/support/react/how-to-select-all-check-box-in-mdb-react-table/]
    const handleSelectAllChange = () => {setAreAllChecked(areAllChecked ? false : true)}

    const handleSingleCheckboxChange = id => {
        let currentChecked = [...checked];
        currentChecked.map((item) => {
            if ((item.id) === id){
                item.checked = !item.checked;
            }
            return item;
        })
        setChecked(currentChecked);
    }


    function handleUpdateRepos() {
        setUpdatePopup(true)

        const projectIdToUpdate = checked.reduce((result, {
            id,
            checked
        }) => [...result, ...checked ? [id] : []], []);
        console.log(projectIdToUpdate);

        axios.post("/api/v1/setProjectInfoWithSettings", projectIdToUpdate, {
            headers: {'Content-Type': 'application/json'}})
                .then(response => {
                    console.log("Id array sent successfully")
                })
                .catch((error) => {
                    console.error(error);
                })

    }

    //[https://mdbootstrap.com/support/react/how-to-select-all-check-box-in-mdb-react-table/]

    const output = projects.map(function (item, index) {
        let currentYear = new Date().getFullYear();
        let dateStringCreated = moment(item.created_at).format('ll').replace(", "+currentYear, "")
        let dateStringUpdated = moment(item.last_sync_at).format('lll').replace(currentYear, "")
        return {
            check: <input type="checkbox" checked={areAllChecked  ? true : false} //>/checked[index].checked}
                        onClick={() => handleSingleCheckboxChange(item.id)}/>,
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
            {label: <MDBInput label=" " type="checkbox" valueDefault={areAllChecked} onChange={handleSelectAllChange}/>,
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
                <button type="button" className="btn btn-secondary" onClick={() => {handleUpdateRepos(); setUpdatePopup(true);}}>Update Selected Projects</button>
                <UpdateModal show={updatePopup} onHide={setUpdatePopup(false)}>

            </UpdateModal>
                {/*<UpdatePopup closeOnOutsideClick={true} trigger={updatePopup} setTrigger={setUpdatePopup} transparent={false}/>*/}
            </div>
            <MDBDataTable hover btn sortable pagesAmount={20}
                searchLabel="Search By Project Name/Month"
                small
                data={data}
            />
        </div>
    );
}