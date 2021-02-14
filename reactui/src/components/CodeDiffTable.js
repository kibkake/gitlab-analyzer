import "../App.css"
import React,{ Component } from "react";
import ProjectService from "../Service/ProjectService";
import * as ReactBootStrap from "react-bootstrap"
import {Table} from 'react-bootstrap'

class CodeDiffTable extends Component{

    // TODO: Do not delete, will be used once the API is set
    constructor(props) {
        super(props);
        this.state = {
            WrapperUser:[]
        }
    }

    componentDidMount() {
        ProjectService.getCodeDiff().then((response) => {
            this.setState({WrapperUser: response.data})
        });
    }

    // Fake data for table creation testing
    // const codeDiff = [
    //     {mergeRequestIid:3, date: 204, score: 2, commitComments: "initial commit", codeDiff: "Dsdfsdfsdf"},
    //     {mergeRequestIid:8, date: 304, score: 0, commitComments: "check out commit", codeDiff: "sdfsdfads"},
    // ]

    render () {
        return (
            <div className="CodeDiffTable">
                <Table striped bordered hover>
                    <tbody>
                    <tr>
                        <td>Date</td>
                        <td>Score</td>
                        <td>Commit Comments</td>
                        <td>Code Diff</td>
                    </tr>
                    {
                        this.state.WrapperUser.map(WrapperUser=>
                            <tr>
                                <td>{WrapperUser.mergeRequestIid}</td>
                                <td>{WrapperUser.mergeRequestTitle}</td>
                                {/*<td>{WrapperUser.score}</td>*/}
                                {/*<td>{WrapperUser.commitComments}</td>*/}
                                {/*<td>{WrapperUser.mergeRequestCommits.diff}</td>*/}
                            </tr>
                        )}
                    </tbody>
                </Table>

            </div>
        );
    }
}

export default CodeDiffTable;
