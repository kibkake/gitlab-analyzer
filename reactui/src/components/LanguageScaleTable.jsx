import React, { Component } from 'react';
import axios from "axios";
import { Button,Table} from 'react-bootstrap';
import {LanguageScale} from './storage/LanguageScale';
import Modal from 'react-modal';


class LanguageScaleTable extends Component{

    constructor() {
        super();
        this.state = {
            contacts: LanguageScale
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    state ={newScaleIsShown:false};
    handleSubmit(e) {

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

                    <Modal isOpen={this.state.newScaleIsShown} shouldCloseOnOverlayClick={false}>
                        <h1>testing if this works</h1>

                        <div>
                            <Button Style="Secondary" onClick={this.closeModal}>close modal</Button>
                        </div>
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

                                {LanguageScale.map(scales=>
                                    <tr>
                                        <td>{scales.name}</td>
                                        <td>{scales.Extention}</td>
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