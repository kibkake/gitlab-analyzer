import React, {PureComponent} from "react";
import "./HBox.css"


class CommitMergeRequest extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            hash : this.props.hash
        }
    }
//http://localhost:8090/api/v2/projects/6/mergeRequests/81dcd6aab70ebf99195e234d9f4f49ec13d0a252
    async componentDidMount() {
        /*var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var hash = this.props.hash;

        let url2 = '/api/v2/projects/' + repNum + '/mergeRequests/' + hash;
        const result = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        const resp = await result.json();
        await this.setState({data:resp})*/
    }

    async getDataFromBackend () {

        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        var hash = this.props.hash;
        console.log("hash is ", hash)

        await axios.get("/api/v2/projects/" + repNum + "/mergeRequests/" + "81dcd6aab70ebf99195e234d9f4f49ec13d0a252")
            .then(response => {
                const nums = response.data
                this.setState({data : nums})
                console.log(this.state.data)
            }).catch((error) => {
                console.error(error);
            });
    }

    render() {

        return(
        <div style={{ overflow: "scroll", height: "1050px", width: "300px"}}>
            Commit's Merge request to be added here: {this.props.hash}
            </div>
        )
    }

}

export default CommitMergeRequest;