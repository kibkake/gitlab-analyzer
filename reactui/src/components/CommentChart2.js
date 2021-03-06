import {PureComponent} from "react";
import axios from "axios";


class CommentChart extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            commentScore: [],
            parentdata: this.props.devName,
            startTime: this.props.startTime,
            endTime: this.props.endTime
        }
    }

    async getDataFromBackend (username, startTm, endTm) {
        var pathArray = window.location.pathname.split('/');
        var id = pathArray[2];

        const response = await axios.get("/api/v1/projects/" + id + "/allUserNotesForChart/"+ username
            + '/' + startTm + '/' + endTm)
        const commentInfo = await response.data
        await this.setState({commentScore: commentInfo})
    }

}

export default CommentChart