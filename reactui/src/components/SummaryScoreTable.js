// class CodeDiffTable extends Component{
import {Table} from "react-bootstrap";
import React from "react";

function SummaryScoreTable() {
    // TODO: Do not delete, will be used once the API is set
    // constructor(props) {
    //     super(props);
    //     this.state = {
    //         score:[]
    //     }
    // }
    //
    // componentDidMount() {
    //     ProjectService.getCodeDiff().then((response) => {
    //         this.setState({WrapperUser: response.data})??
    //     });
    // }

    //Fake data for table creation testing
    const scoreSummary = [
        {commitScore:3, scoreMR: 204, wordCountComment: 100, numCommentPerDay: 1},
        {commitScore:2, scoreMR: 12, wordCountComment: 200, numCommentPerDay: 0.5},
    ];

    return (
        <div className="CodeDiffTable">
            <Table striped bordered hover>
                <tbody>
                <tr>
                    <td>Commit</td>
                    <td>Merge Request</td>
                    <td>Word Count for Comments</td>
                    <td># of Comments per Day</td>
                </tr>
                {
                    scoreSummary.map((item, index)=>
                        <tr key ={index}>
                            <td>{item.commitScore}</td>
                            <td>{item.scoreMR}</td>
                            <td>{item.wordCountComment}</td>
                            <td>{item.numCommentPerDay}</td>
                            </tr>
                    )}
                </tbody>
            </Table>

        </div>
    );
}

export default SummaryScoreTable;
