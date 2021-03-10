import React, { Component } from 'react';
import axios from "axios";
import { Button,Table,Modal, Form} from 'react-bootstrap';
import { LanguageScale } from './storage/LanguageScale';


class LanguageScaleTable extends Component{

    constructor() {
        super();
        const me=this
        this.state = {
            LanguageScale:[{
                name:'Default',
                extention:"",
                multiplier:1,
            }],
            newScaleIsShown:false,
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.deleteModifier= this.deleteModifier.bind(this);
    }
    componentDidMount(){
        sessionStorage.setItem('languageScale',JSON.stringify(this.state.LanguageScale))
    }
    handleSubmit(e) {
        e.preventDefault();
        const{LanguageScale}=this.state.LanguageScale;
        const newScale={
            name:this.name.value,
            extention:this.extention.value,
            multiplier:this.multiplier.value,
        }
        this.setState({
            LanguageScale:[...this.state.LanguageScale,newScale],
            newScaleIsShown:false,
        })

        this.saveTable();
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
    saveTable=()=>{
        sessionStorage.setItem('languageScale',JSON.stringify(this.state.LanguageScale));
    }
    deleteModifier(index){
        let tempScale = this.state.LanguageScale;
        tempScale.splice(index, 1);
        this.setState({
            LanguageScale: tempScale 
        });
        this.saveTable();
    }


    render(){
        return(
        <>
                <div style={{
                    marginTop:'20px',
                    marginBottom:'20px',
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                }}>
                    <div>
                        <Button className='AddNewScale' variant='primary' onClick={this.showModal} style={{marginRight:'10px'}}>
                            Add new Language Multiplier
                        </Button>
                        <Button className='saveScale' variant='info' onClick={this.saveTable}>
                            Submit modifiers
                        </Button>
                    </div>
                    <Modal show={this.state.newScaleIsShown}>
                        <Modal.Header closeButton>
                            <Modal.Title>Add a new multiplier</Modal.Title>
                        </Modal.Header>
                        <Form className="addForm" onSubmit={(e)=> {this.handleSubmit(e)}}>
                            <Form.Row>
                                <Form.Group controlId="name" style={{marginLeft:'12px', marginRight:'12px'}}>
                                    <Form.Label>Language Name</Form.Label>
                                    <Form.Control type="text" ref={(input)=>{this.name=input}} placeholder="Enter name"/>
                                </Form.Group>
                                <Form.Group controlId="extention">
                                    <Form.Label>Extention Name</Form.Label>
                                    <Form.Control type="text" ref={(input)=>{this.extention=input}} placeholder='(eg. ".js")'/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Group controlId="multiplier" >
                                    <Form.Label>Language Multiplier</Form.Label>
                                    <Form.Control type="number" ref={(input)=>{this.multiplier=input}} placeholder="Enter multiplier"/>
                            </Form.Group>
                            <Button variant="primary" type="submit" onClick={this.handleSubmit} style={{marginLeft:'12px'}}>
                                Submit
                            </Button>
                            <Button variant="secondary" onClick={this.closeModal} style={{float:'right',marginRight:'12px'}}>
                                    Cancel
                            </Button>
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
                                {this.state.LanguageScale.map((scales,index)=>
                                    <tr>
                                        <td>{scales.name}</td>
                                        <td>{scales.extention}</td>
                                        <td>{scales.multiplier}</td>
                                        <td>
                                            {/* <Button className="editButton" variant="outline-secondary" 
                                                style={{
                                                    marginRight:'12px',
                                                    width:'80px',
                                                }}
                                            >
                                                Edit
                                            </Button> */}
                                            
                                            <Button className='deleteButton' variant="danger" onClick={()=> this.deleteModifier(index)}
                                                style={{
                                                    width:'80px',
                                                    marginLeft:'15px',
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