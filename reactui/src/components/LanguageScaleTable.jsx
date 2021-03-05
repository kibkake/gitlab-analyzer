import React, { Component } from 'react';
import axios from "axios";
import { Button,Table,Modal, Form} from 'react-bootstrap';
import { LanguageScale } from './storage/LanguageScale';


class LanguageScaleTable extends Component{

    constructor() {
        super();
        this.state = {
            LanguageScale:[{
                name:'Default',
                extention:"",
                multiplier:1
            }],
            newScaleIsShown:false,
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    handleSubmit(e) {
        
        const newScale={
            name:e.target.name.value,
            extention:e.target.extention.value,
            multiplier:e.target.multiplier.value,
        }
       this.setState(prevState=>({
           LanguageScale:[...prevState.LanguageScale,newScale]
       }))
       sessionStorage.setItem('languageScale',LanguageScale);
    }
    showModal = () => {
        this.setState({ 
            newScaleIsShown: true 
        });
    };
    closeModal = () => {
        this.setState({ 
            newScaleIsShown: false 
        });
    };
    render(){
        return(
        <>
                <div   style={{
                    marginTop:'20px',
                    marginBottom:'20px',
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                }}>
                    <Button className='AddNewScale' Style='primary' onClick={this.showModal}>
                        Add new Language Multiplier
                    </Button>

                    <Modal show={this.state.newScaleIsShown}>
                        <Modal.Header closeButton>
                            <Modal.Title>Add a new multiplier</Modal.Title>
                        </Modal.Header>
                        <Form className="addForm" onSubmit={this.handleSubmit}>
                            <Form.Row>
                                <Form.Group controlId="name">
                                    <Form.Label>Language Name</Form.Label>
                                    <Form.Control type="text" placeholder="Enter name"/>
                                </Form.Group>
                                <Form.Group controlId="extention">
                                    <Form.Label>Extention Name</Form.Label>
                                    <Form.Control type="text" placeholder='(eg. ".js")'/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Group controlId="multiplier">
                                    <Form.Label>Language Multiplier</Form.Label>
                                    <Form.Control type="text" placeholder="Enter multiplier"/>
                            </Form.Group>
                            <Button Style="primary" type="submit" onClick={this.handleSubmit}>Submit</Button>
                            <Button Style="secondary" onClick={this.closeModal}>Cancel</Button>
                        </Form>
                    </Modal>
                        





                    
                </div>
                <div>
                    <Table striped bordered hover 
                        style={{
                            marginLeft:'auto',
                            marginRight:'auto',
                        }}>
                            <thead>
                                <tr>
                                    <th>Language name</th>
                                    <th>File extention</th>
                                    <th>Multiplier</th>
                                    <th>  </th>
                                </tr>
                            </thead>
                            <tbody>

                                {this.state.LanguageScale.map(scales=>
                                    <tr>
                                        <td>{scales.name}</td>
                                        <td>{scales.extention}</td>
                                        <td>{scales.multiplier}</td>
                                        <td>
                                            <Button className="editButton" variant="outline-secondary" 
                                                style={{
                                                    marginRight:'12px',
                                                    width:'80px',
                                                }}
                                            >
                                                Edit
                                            </Button>
                                            
                                            <Button className='deleteButton' variant="danger" 
                                                style={{
                                                    width:'80px',
                                                }}
                                            >
                                                Delete
                                            </Button>
                                        </td>
                                    </tr>
                                    )}

                            </tbody>
                        </Table>
                    </div>
            </>

        
        )
    }


}

export default LanguageScaleTable;