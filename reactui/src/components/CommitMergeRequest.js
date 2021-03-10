import React, {PureComponent} from "react";
import "./HBox.css"


class CommitMergeRequest extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {}
    }


    render() {

        return(
        <div className="horizontalBar" style={{ overflow: "scroll", height: "1000px", width: "700px"}}>
            Commit's Merge request to be added here
            </div>
        )
    }

}

export default CommitMergeRequest;