import "../App.css"
import React, {Component, useRef} from "react";
import {Button, Table} from 'react-bootstrap'
import Highlight from 'react-highlight'
import axios from "axios";
import Popup from "./Popup";
import {useState,useEffect} from "react";
import { MenuItems } from "./MenuItem_Developers";

function CodeDiffTable({devName}) {

    const [commits,getCommits]=useState([]);
    const [buttonPopup, setButtonPopup] = useState(false);

    const mounted = useRef();
    useEffect(()=>{
        if (!mounted.current) {
            var str = window.location.pathname;
            var repNum = str.split("/")[2];
            var name = str.split("/")[4];
            axios.get("/api/v1/projects/" + repNum + "/Commits/" + devName + "/2021-01-01/2021-05-09")
                .then(response => {
                    getCommits(response.data)
                });
            mounted.current = true;
        } else {
            var str = window.location.pathname;
            var repNum = str.split("/")[2];
            var name = str.split("/")[4];
            axios.get("/api/v1/projects/" + repNum + "/Commits/" + devName + "/2021-01-01/2021-05-09")
                .then(response => {
                    getCommits(response.data)
                });        }
    }, [commits]);
        return (
            <div className="CodeDiffTable">
                <Table striped bordered hover>
                    <tbody>
                    <tr>
                        <td>Commit ID</td>
                        <td>Date</td>
                        <td>Score</td>
                        <td>Commit Message</td>
                        <td>Code Diff</td>
                        <td></td>
                    </tr>
                    {
                        commits.map((item, index)=>
                            <tr key ={index}>
                                <td>{item.id}</td>
                                <td>{item.date}</td>
                                <td>{item.commitScore}</td>
                                <td>{item.message}</td>
                                <td>{item.diffs.length} files changed</td>

                                <td>                      
                                    <button onClick={()=> setButtonPopup(true)}> Difference in code</button>
                                    <Popup closeOnOutsideClick={true} trigger={buttonPopup} setTrigger = {setButtonPopup} >
                                        {item.diffs}
                                    </Popup>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </Table>
            </div>
        );
    }
    export default CodeDiffTable;


