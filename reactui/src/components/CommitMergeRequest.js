import React, {PureComponent} from "react";
import "./HBox.css"
import axios from "axios";

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

        if(this.state.data.diff.length === 0){
            return(
                <div>
                    no merge request!
                    </div>
            )
        }else if (this.state.data.diff.length > 0) {
            <div>
                merge request goes here
            </div>
        }

        return(
            <div>
                    ???
            </div>
        )
    }

}

export default CommitMergeRequest;