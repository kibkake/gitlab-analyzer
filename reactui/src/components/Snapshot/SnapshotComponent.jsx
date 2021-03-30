import React, { Component } from 'react';
import './SnapshotComponent.css';
import {RiFolderUploadLine} from "react-icons/all";

export default class SnapshotComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            snapshotId:""
        };
        this.handleChange=this.handleChange.bind(this);
    }

    componentDidMount() {
        const user = sessionStorage.getItem('user');
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    render(){
        return(
            <>
                <div className="snapshot-container">
                    <h2 className="snapshot-h"> Saved Snapshots </h2>
                </div>
                <h2 className="snapshot-l">Load Snapshot</h2>
                <form className="snapshot-h">
                    <label>
                        <h5 className="snapshot-unformat">Snapshot ID</h5>
                        <input className="snapshotId" name="snapshotId" type="text" onChange={this.handleChange}/>
                        <button className="login"> Load <RiFolderUploadLine/></button>
                    </label>
                </form>
            </>
        )
    }
}
