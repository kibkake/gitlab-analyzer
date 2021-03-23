import React, {PureComponent} from "react";
import "./HBox.css"
import axios from "axios";
import {Table} from "react-bootstrap";
import "../Projects/ProjectList.css";

class CommitMergeRequest extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            data: {mrScore: 0, diffs : [{diff: []}]},
            hash : this.props.hash
        }
    }
//http://localhost:8090/api/v2/projects/6/mergeRequests/81dcd6aab70ebf99195e234d9f4f49ec13d0a252
    async componentDidMount() {
        await this.getDataFromBackend();
    }

    async componentDidUpdate(prevProps){
        if(this.props.hash !== prevProps.hash){
            await this.getDataFromBackend()
        }
    }

    async getDataFromBackend () {

        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var hash = this.props.hash;
        console.log("hash is ", hash)

        await axios.get("/api/v2/projects/" + repNum + "/mergeRequests/" + hash)
            .then(response => {
                const nums = response.data
                this.setState({data : nums})
                console.log(this.state.data)
            }).catch((error) => {
                console.error(error);
        });
    }

    render() {

        console.log(this.state.data.mrScore);

        if(this.state.data === "") {
            return (
                <div>
                    No merge request for this commit: Either commited directly to master or MR is not in db
                </div>
            )
        }

        return(
            <div style={{ overflow: "scroll", height: "1050px", width: "1000px"}}>
                <div>
                    <span>
                        <td className="commitscore">Merge Score = {this.state.data.mrScore}</td>
                    </span>
                </div>
                <div className="CodeDiffTable">
                    <Table striped bordered hover>
                        <tbody>
                            <tr>
                                <td>File Name</td>
                                <td>Diff</td>
                            </tr>
                            {this.state.data.diffs.map((item2, index) =>
                                <tr key ={index}>
                                    <td>{item2.new_path}</td>
                                    <td>{item2.diff.toString()}</td>
                                </tr>
                            )}
                        </tbody>
                </Table>
               </div>
            </div>

        )
    }
}

export default CommitMergeRequest;