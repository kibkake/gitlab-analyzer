import React, { Component } from 'react';
import axios from "axios";
import { Button,Table } from 'react-bootstrap';
import {LanguageScale} from './storage/LanguageScale'

class LanguageScaleTable extends Component{

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
                    <Button className='AddNewScale' Style='primary'>
                        Add new Language Multiplier
                    </Button>
                    
                </div>
                <Table striped borded hover>
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
                                </tr>
                                )}

                        </tbody>
                    </Table>
            </>

        
        )
    }


}

export default LanguageScaleTable;