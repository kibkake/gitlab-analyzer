import React, { Component } from 'react';
import ProfileService from "../Service/ProfileService";
import ConfigService from "../Service/ConfigService";
import {Button, Modal,Form,Table} from "react-bootstrap";
import axios from 'axios';

import "./HomeModal.css";

const REST_API = "/api/v1/";



export default class HomeInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            info:[],
            showSaveConfig:false,
            showLoadConfig:false,
            name:"",
            configs:[]
        };
        this.showSaveModal=this.showSaveModal.bind(this);
        this.handleSubmit=this.handleSubmit.bind(this);
        this.showLoadModal=this.showLoadModal.bind(this);
        this.closeModal=this.closeModal.bind(this);
        this.loadConfig=this.loadConfig.bind(this);
        this.deleteConfig=this.deleteConfig.bind(this);
        
    }

    async componentDidMount() {
        const user = sessionStorage.getItem('user');
        // must grab token before we can dynamically set the token
        await ProfileService.getUserInfo(user).then((response) => {
            this.setState(response.data);
            sessionStorage.setItem('token',response.data.token);
        }, (error) => {
            console.log(error);
        });

        await axios.get(REST_API+"getConfigs/").then(response => {
            this.setState({
                configs:response.data,
                name:response.data.name
            });
        }, (error) => {
            console.log(error);
        });
        if(!sessionStorage.getItem('__init__')){
            ProfileService.setUserToken(sessionStorage.getItem('token')).then((response) => {
                // sent post request
            }, (error) => {
                console.log(error);
            });
            sessionStorage.setItem('__init__',"__visited");
        }
    }



    async handleSubmit(e){
        e.preventDefault();
        console.log("test");
        await ConfigService.saveConfig(
            this.name.value,              // current user
            sessionStorage.getItem('startdate'),            // chosen snapshot date
            sessionStorage.getItem('enddate'),              // "                  "
        );
        this.setState({
            showSaveConfig:false
        })
    }

    loadConfig(index){
        this.setState({
            name:this.state.configs[index].name
        })
        sessionStorage.setItem("startdate",this.state.configs[index].startDate);
        sessionStorage.setItem("enddate",this.state.configs[index].endDate);
        sessionStorage.setItem("languageScale",JSON.stringify(this.state.configs[index].languageScale));
        this.closeModal();
    }

    deleteConfig(index){
        ConfigService.deleteConfig(this.state.configs[index].id);
        this.closeModal();
    }

    showSaveModal(){
        this.setState({
            showSaveConfig:true
        });
    }

    async showLoadModal(){
        await axios.get(REST_API+"getConfigs/").then(response => {
            var tempConfig = response.data;

            for(var k=0;k<tempConfig.length;k++){
                console.log("loop");
                var temp = tempConfig[k].languageScale;
                console.log(temp);
                temp=JSON.parse(temp);
                console.log(temp);
                tempConfig[k].languageScale=temp;
            }
            console.log(tempConfig);
            this.setState({
                configs:tempConfig,
            });
        }, (error) => {
            console.log(error);
        });
        this.setState({
            showLoadConfig:true
        });
    }

    closeModal(){
        this.setState({
            showSaveConfig:false,
            showLoadConfig:false
        });
    }
    render() {
        return (
            <>
                <br/>
                <h4 style={{textAlign:'center'}}>Welcome to Pluto GitLab Analyzer, {this.state.username}!</h4>

                <div style={{display: "flex",justifyContent: "center",alignItems:'center',marginTop:"20px"}}> 
                    <Button className='saveConfigButton' variant="outline-info" style={{marginRight:'10px'}} onClick={this.showSaveModal}>
                        Save Configuration
                    </Button>
                    <Button className="loadConfigButton" variant='info' onClick={this.showLoadModal}>
                        Load Configuration
                    </Button>
                </div>

            <Modal className='saveConfigModal' show={this.state.showSaveConfig} onHide={this.closeModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Save Configuration</Modal.Title>
                </Modal.Header>
                    <Form className="addConfigForm" onSubmit={(e)=> {}}>
                        <Form.Group controlId="name" style={{marginLeft:'12px',marginRight:'12px'}}>
                            <Form.Label>Configuration Name</Form.Label>
                            <Form.Control type="text" ref={(input)=>{this.name=input}} placeholder="Enter name"/>
                        </Form.Group>
                        <Button variant="primary" type="submit" onClick={this.handleSubmit} style={{marginLeft:'12px',marginBottom:'10px'}}>
                            Submit
                        </Button>
                        <Button variant="secondary" onClick={this.closeModal} style={{float:'right',marginRight:'12px',marginBottom:'10px'}}>
                            Cancel
                        </Button>
                    </Form>
            </Modal>

            <Modal className='loadConfigModal' show={this.state.showLoadConfig} onHide={this.closeModal} dialogClassName="loadModal">
                <Modal.Header closeButton>
                    <Modal.Title>Load Configuration</Modal.Title>
                </Modal.Header>
                <div>
                    <Table striped bordered hover className='configTable'>
                    <br/>
                        <thead>
                        <tr>
                            <th>Config Name</th>
                            <th>start date</th>
                            <th>end date</th>
                            <th>number of multipliers</th>
                            <th> </th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.configs.map((config,index)=>{
                            return(
                                <tr>
                                    <td>{config.name}</td>
                                    <td>{config.startDate}</td>
                                    <td>{config.endDate}</td>
                                    {<td>{config.languageScale.length}</td>}
                                    <td>
                                        <Button className="editButton" variant="primary"  onClick={()=>this.loadConfig(index)}
                                            style={{
                                                marginRight:'12px',
                                                width:'80px',
                                            }}
                                        >
                                            Load
                                        </Button>
                                        
                                        <Button className='deleteButton' variant="danger" onClick={()=>this.deleteConfig(index)}
                                            style={{
                                                width:'80px',
                                                marginLeft:'15px',
                                            }} 
                                        >
                                            Delete
                                        </Button>
                                    </td>
                                </tr>
                            );
                        })
                        }
                        </tbody>

                    </Table>


                </div>

                <Button variant="secondary" onClick={this.closeModal} style={{float:'right',marginLeft:'12px',marginBottom:'10px', width:"250px"}}>
                    Cancel
                </Button>
            </Modal>

            </>
        )
    }
}