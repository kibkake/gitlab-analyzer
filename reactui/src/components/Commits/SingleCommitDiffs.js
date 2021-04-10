import React, {Component} from 'react'
import "../Projects/ProjectList.css";
import {Table} from "react-bootstrap";
import HighlightCodeDiffs from "./HighlightCodeDiffs"

class SingleCommitDiff extends Component{
    constructor(props){
        super(props);
        this.state={
            data: [],
            hash : this.props.hash
        };
    }

    async componentDidMount() {
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var hash = this.props.hash;

        let url2 = '/api/v1/projects/' + repNum + '/Commit/' + hash;
        const result = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        const resp = await result.json();
        await this.setState({data:resp})
    }

    render() {
        return (
            <div   style={{ overflow: "scroll", height: "1050px", width: "1000px"}}>
                <div>
                    { this.state.data.map(projects =>
                        <span>
                            <td className="commitscore">Commit Score = {projects.commitScore}</td>
                        </span>
                    )}
                </div>
            <div className="CodeDiffTable">

                <Table striped bordered hover>
                    <tbody>
                        <tr>
                            <td>File Name</td>
                            <td>Diff</td>
                        </tr>

                        {this.state.data.map((item) =>
                        item.diffs.map((item2, index) =>
                            <tr key ={index}>
                                <td>{item2.new_path}</td>
                                <td>{HighlightCodeDiffs(item2.diff)}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>

            </div>
                </div>
        );
    }
}

export default SingleCommitDiff;