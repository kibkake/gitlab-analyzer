import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import Button from 'react-bootstrap/Button';

import {RepoItems} from '../Pages/sampleRepo';
import RepoButton from "./RepoButton";
//import "./RepoButton.css"
import "./ProjectList.css";
import {Table} from "react-bootstrap";



class SingleCommitDiff extends Component{
    constructor(props){
        super(props);
        this.state={
            data: []
        };
    }
//http://localhost:8080/getuserstats/6/arahilin/commitdiff/aea93f2c57387d310f02768d4ca6c2ed71a60b41

    //http://localhost:3000/Repo/6/Developers/user2/commits/1-24-2021/ac108a6cab6e2b63c8e2d4a1150ac67ba82849d0
    componentDidMount() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var userName = str.split("/")[4];
        var date = str.split("/")[6];
        var hash = str.split("/")[7];



        let url2 = 'http://localhost:8080/getuserstats/' + repNum + '/' + userName + '/commitdiff/' + hash
        fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then((result)=> {
            result.json().then((resp) => {
                this.setState({data:resp})
            })
        })

    }

    render() {

        var data = JSON.stringify(this.state.data);
        var DataArray = JSON.parse(data)


        console.log(DataArray);

        return (
            <div className="CodeDiffTable">
                <Table striped bordered hover>
                    <tbody>
                        <tr>
                            <td>File Name</td>
                            <td>Diff</td>
                        </tr>

                        {DataArray.map((item) =>
                        item.wrapperCommitDiffs.map((item2, index) =>
                            <tr key ={index}>

                            <td>{item2.newPath}</td>
                                <td>{item2.diff}</td>

                            </tr>


                        ))}





                    </tbody>
                </Table>

            </div>
        );
    }
}

export default SingleCommitDiff;