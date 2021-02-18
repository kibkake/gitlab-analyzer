import React, {Component} from 'react';
import BarChart from 'react-bar-chart';
import axios from "axios";

const margin = {top: 20, right: 20, bottom: 30, left: 40};

class CommentChart extends Component {

    getInitialState() {
        return { width: 500 };
    }

    constructor() {
        super();
        this.state = {
            commentScore: []
        }
    }

    //response ref: http://localhost:8080/api/v1/projects/6/topTenUserNotes/arahilin/2021-01-01/2021-02-15
    componentDidMount () {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];
        var developer = pathArray[4];
        axios.get("http://localhost:8080/api/v1/projects/" + id + "/topTenUserNotes/"+ developer +"/2021-01-01/2021-02-15")
            .then(response => {
                const commentInfo = response.data;
                this.setState({commentScore: commentInfo})
                console.log(this.state.commentScore);

            }).catch((error) => {
            console.error(error);
        });
    }

    render() {
        var output = this.state.commentScore.map(function(item) {
            return {
                text: item.created_at,
                value: item.wordCount
            };
        });
        console.log(output);

        return (
            <div ref='root'>
                <div style={{width: '50%'}}>
                    <BarChart ylabel='Score'
                              width={1500}
                              height={500}
                              margin={margin}
                              data={output}

                    />
                </div>
            </div>
        );
    }
}

export default CommentChart