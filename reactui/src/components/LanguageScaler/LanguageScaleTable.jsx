import React, { Component } from 'react';
import { Button,Table,Modal, Form} from 'react-bootstrap';


class LanguageScaleTable extends Component{

    constructor() {
        super();
        const me=this
        this.state = {
            LanguageScale:[],
            newScaleIsShown:false,
            editScaleIsShown:false,
            chosenScale:[],
            chosenIndex:0
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.deleteModifier= this.deleteModifier.bind(this);
        this.saveTable=this.saveTable.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
    }
    async componentDidMount(){
        const baseScale=[{
            name:'Default',
            extension:"",
            multiplier:1,
        }]
        console.log("mouting");
        var tempScale =JSON.parse(sessionStorage.getItem('languageScale'));
        if(tempScale===null||Object.keys(tempScale).length===0){
            tempScale=baseScale
            this.setState({
                LanguageScale:baseScale,
            })

        }else{
            console.log('in');
            console.log(tempScale);
            this.setState({
                LanguageScale:tempScale
            })
        }
        sessionStorage.setItem('languageScale',JSON.stringify(tempScale));
    }
    handleSubmit(e) {
        e.preventDefault();
        let tempScale=[...this.state.LanguageScale];
        const newScale={
            name:this.name.value,
            extension:this.extension.value,
            multiplier:this.multiplier.value,
        }
        tempScale=[...this.state.LanguageScale,newScale]
        this.setState({
            LanguageScale:tempScale,
            newScaleIsShown:false,
        })
        console.log("submite state");
        console.log(this.state.LanguageScale)
        sessionStorage.setItem('languageScale',JSON.stringify(tempScale));
    };

    handleEdit(e){
        e.preventDefault();
        let tempScale=[...this.state.LanguageScale];
        const newScale={
            name:this.name.value,
<<<<<<< HEAD
            extension:this.extension.value,
=======
            extention:this.extention.value,
>>>>>>> 271be13f3888b844adcc10ac38e451165195b7ac
            multiplier:this.multiplier.value,
        }
        tempScale[this.state.chosenIndex]=newScale;

        this.setState({
            LanguageScale:tempScale,
            editScaleIsShown:false
        })
        console.log("edit state");
        console.log(this.state.LanguageScale)
        sessionStorage.setItem('languageScale',JSON.stringify(tempScale));
    }
    showModal = () => {
        this.setState({ 
            newScaleIsShown: true 
        });
    };
    closeModal = () => {
        this.setState({ 
            newScaleIsShown: false,
            editScaleIsShown:false
        });
    };
    saveTable=()=>{
        console.log("submite state");
        console.log(this.state.LanguageScale)
        sessionStorage.setItem('languageScale',JSON.stringify(this.state.LanguageScale));
    };
    deleteModifier(index){
        let tempScale = this.state.LanguageScale;
        tempScale.splice(index, 1);
        this.setState({
            LanguageScale: tempScale 
        });
        this.saveTable();
    }

    chooseScale(index){
        let tempIndex = index
        let tempScale = this.state.LanguageScale[index];
        this.setState({
            chosenScale:tempScale,
            chosenIndex:tempIndex,
            editScaleIsShown:true
        });
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
                            Add new language multiplier
                        </Button>
                    </div>
                    <Modal className="NewScaleModal" show={this.state.newScaleIsShown}>
                        <Modal.Header closeButton>
                            <Modal.Title>Add a new multiplier</Modal.Title>
                        </Modal.Header>
                        <Form className="addForm" onSubmit={(e)=> {this.handleSubmit(e)}}>
                            <Form.Row>
                                <Form.Group controlId="name" style={{marginLeft:'12px',marginRight:'12px'}}>
                                    <Form.Label>Language Name</Form.Label>
                                    <Form.Control type="text" ref={(input)=>{this.name=input}} placeholder="Enter name"/>
                                </Form.Group>
                                <Form.Group controlId="extension" style={{marginLeft:'12px'}}>
                                    <Form.Label>extension Name</Form.Label>
                                    <Form.Control type="text" ref={(input)=>{this.extension=input}} placeholder='(eg. "js") do not include the .'/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Group controlId="multiplier" style={{marginLeft:'12px'}}>
                                    <Form.Label>Language Multiplier</Form.Label>
                                    <Form.Control type="number" ref={(input)=>{this.multiplier=input}} placeholder="Enter multiplier"/>
                            </Form.Group>
                            <Button variant="primary" type="submit" onClick={this.handleSubmit} style={{marginLeft:'12px',marginBottom:'10px'}}>
                                Submit
                            </Button>
                            <Button variant="secondary" onClick={this.closeModal} style={{float:'right',marginRight:'12px',marginBottom:'10px'}}>
                                    Cancel
                            </Button>
                        </Form>
                    </Modal>

                    <Modal className="EditScaleModal" show={this.state.editScaleIsShown}>
                        <Modal.Header closeButton>
                            <Modal.Title>Add a new multiplier</Modal.Title>
                        </Modal.Header>
                        <Form className="addForm" onSubmit={(e)=> {}}>
                            <Form.Row>
                                <Form.Group controlId="name" style={{marginLeft:'12px',marginRight:'12px'}}>
                                    <Form.Label>Language Name</Form.Label>
                                    <Form.Control type="text" ref={(input)=>{this.name=input}} defaultValue={this.state.chosenScale.name}/>
                                </Form.Group>
<<<<<<< HEAD
                                <Form.Group controlId="extension" style={{marginLeft:'12px'}}>
                                    <Form.Label>extension Name</Form.Label>
                                    <Form.Control type="text" ref={(input)=>{this.extension=input}} defaultValue={this.state.chosenScale.extension}/>
=======
                                <Form.Group controlId="extention" style={{marginLeft:'12px'}}>
                                    <Form.Label>Extention Name</Form.Label>
                                    <Form.Control type="text" ref={(input)=>{this.extention=input}} defaultValue={this.state.chosenScale.extention}/>
>>>>>>> 271be13f3888b844adcc10ac38e451165195b7ac
                                </Form.Group>
                            </Form.Row>
                            <Form.Group controlId="multiplier" style={{marginLeft:'12px'}}>
                                    <Form.Label>Language Multiplier</Form.Label>
                                    <Form.Control type="number" ref={(input)=>{this.multiplier=input}} defaultValue={this.state.chosenScale.multiplier}/>
                            </Form.Group>
                            <Button variant="primary" type="submit" onClick={this.handleEdit} style={{marginLeft:'12px',marginBottom:'10px'}}>
                                Save
                            </Button>
                            <Button variant="secondary" onClick={this.closeModal} style={{float:'right',marginRight:'12px',marginBottom:'10px'}}>
                                    Cancel
                            </Button>
                        </Form>
                    </Modal>
                </div>

                <div style={{  margin: 'auto',
                                width: '50%',
                                padding: '10px'
                                }}>
                    <Table striped bordered hover 
                        style={{
                            marginLeft:'auto',
                            marginRight:'auto',
                        }}>
                            <thead>
                                <tr>
                                    <th>Language name</th>
                                    <th>File extension</th>
                                    <th>Multiplier</th>
                                    <th>  </th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.LanguageScale.map((scales,index)=>
                                    <tr>
                                        <td>{scales.name}</td>
                                        <td>{scales.extension}</td>
                                        <td>{scales.multiplier}</td>
                                        <td>
                                            <Button className="editButton" variant="outline-secondary"  onClick={()=>this.chooseScale(index)}
                                                style={{
                                                    marginRight:'12px',
                                                    width:'80px',
                                                }}
                                            >
                                                Edit
                                            </Button>
                                            
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