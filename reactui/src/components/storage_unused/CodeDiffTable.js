import "../../App.css"
import React, {Component, useRef} from "react";
import {Button, Table} from 'react-bootstrap'
import axios from "axios";
import Popup from "./Popup";
import {useState,useEffect} from "react";


function createData(id, date, score, title, fullDiff) {
    return {
        id,
        date,
        score,
        title,
        fullDiff,
        commits: [
            { date: '2020-01-05', committer: '11091700', score: 1, msg: 3, codeDiff:"" },
            { date: '2020-01-02', committer: 'Anonymous', score: 1, msg: 1, codeDiff:"" },
        ],
    };
}


function CodeDiffTable({devName}) {

    const [commits,getCommits]=useState([]);
    const [buttonPopup, setButtonPopup] = useState(false);

    const mounted = useRef();
    useEffect(()=>{
        if (!mounted.current) {
            getDataFromBackend(devName)

        mounted.current = true;
        } else {
            getDataFromBackend(devName)}
    }, [commits]);

    function getDataFromBackend (username) {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var name = str.split("/")[4];
        axios.get("/api/v1/projects/" + repNum + "/Commits/" + devName + "/2021-01-01/2021-05-09/either")
            .then(response => {
                getCommits(response.data)
            });}

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


