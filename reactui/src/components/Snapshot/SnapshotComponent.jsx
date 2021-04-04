import React, { Component } from 'react';
import './SnapshotComponent.css';
import {AiOutlineCopy, RiDeleteBinLine, RiFolderUploadLine} from "react-icons/all";
import SnapshotService from "../../Service/SnapshotService";

export default class SnapshotComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            snapshot:[],
            snapshots: [],
            snapshotId: ""
        };
        this.handleChange = this.handleChange.bind(this);
        this.loadSnapshot = this.loadSnapshot.bind(this);
    }

    async componentDidMount() {
        const user = sessionStorage.getItem('user');
        await SnapshotService.getSnapshots(user).then((response) => {
            this.setState({snapshots: response.data});
        }, (error) => {
            console.log(error);
        });
        console.log(this.state.snapshots);
    }

    async handleChange(event) {
        await this.setState({
            [event.target.name]: event.target.value
        });
    }

    async loadSnapshot(){
        await SnapshotService.getSnapshot(this.state.snapshotId).then((response) => {
            this.setState({snapshot: response.data});
        }, (error) => {
            console.log(error);
        });
        sessionStorage.setItem("CurrentDeveloper",this.state.snapshot.dev)
        sessionStorage.setItem("startdate",this.state.snapshot.startDate)
        sessionStorage.setItem("enddate",this.state.snapshot.endDate)
        window.location.href="/Repo/"+this.state.snapshot.projectId + "/Developers/"+this.state.snapshot.dev+"/"+this.state.snapshot.page
    }

    render() {

        // non-https copy method from Stack Overflow
        // https://stackoverflow.com/questions/51805395/navigator-clipboard-is-undefined
        function copyToClipboard(textToCopy) {
            // text area method
            let textArea = document.createElement("textarea");
            textArea.value = textToCopy;
            // make the textarea out of viewport
            textArea.style.position = "fixed";
            textArea.style.left = "-999999px";
            textArea.style.top = "-999999px";
            document.body.appendChild(textArea);
            textArea.focus();
            textArea.select();
            return new Promise((res, rej) => {
                // here the magic happens
                document.execCommand('copy') ? res() : rej();
                textArea.remove();
            });
        }

        async function deleteSnapshot(snapId) {
            await SnapshotService.deleteSnapshot(snapId);
            window.scrollTo(window.screenX,window.screenY);
            window.location.reload();
        }

        return (
            <>
                <div className="snapshot-container">
                    <h2 className="snapshot-h">Saved Snapshots</h2>
                    <br/>
                    <table className="snapshots-table">
                        <thead>
                        <tr>
                            <th>&nbsp;</th>
                            <th>Snapshot ID</th>
                            <th>Developer</th>
                            <th>Project ID</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Page</th>
                            <th>&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.snapshots.map(function (snaps) {
                            return (
                                <tr>
                                    <td>
                                        <button className="login"
                                                onClick={() => copyToClipboard(snaps.id)}> Copy ID <AiOutlineCopy/>
                                        </button>
                                    </td>
                                    <td>{snaps.id}</td>
                                    <td>{snaps.dev}</td>
                                    <td>{snaps.projectId}</td>
                                    <td>{snaps.startDate}</td>
                                    <td>{snaps.endDate}</td>
                                    <td>{snaps.page}</td>
                                    <td>
                                        <button className="cancel"
                                                onClick={() => deleteSnapshot(snaps.id)}> Delete <RiDeleteBinLine/>
                                        </button>
                                    </td>
                                </tr>
                            );
                        })
                        }
                        </tbody>
                    </table>
                </div>
                <h2 className="snapshot-l">Load Snapshot</h2>
                <form className="snapshot-h">
                    <label>
                        <h5 className="snapshot-unformat">Snapshot ID:</h5>
                        <input className="snapshotId" name="snapshotId" type="text" onChange={this.handleChange}/>
                        <button type="button" className="login" onClick={this.loadSnapshot}> Load <RiFolderUploadLine/></button>
                    </label>
                </form>
            </>
        )
    }
}
