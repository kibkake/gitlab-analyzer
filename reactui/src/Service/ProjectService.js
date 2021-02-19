import axios from 'axios';

const PROJECT_URL = "http://localhost:8080/api/v1/";

/*
  This class contains methods for sending HTTP requests to Apis by spring.
 */

// For now can't really work on... The structure of DB & RestController should be set in the backend
class ProjectService {

    // Just fake functions by content, free to modify

    getAllProject() {
        // return axios.get(PROJECT_REST_API_URL);
    }

    getAllMembers() {

    }

    getCodeScore(projectId, committerName) {
        return axios.get(PROJECT_URL+ "projects/commitScoresPerDay/${projectId}/${committerName}")
    }

    getCommentScore(){

    }

    getCodeDiff() {

    }

    getCommentInfo(projectId, committerName, start, end) {
        return axios.get(PROJECT_URL+ "projects/${projectId}/topTenUserNotes/${committerName}/${start}/${end}")
        // axios.get("http://localhost:8080/api/v1/projects/" + id + "/topTenUserNotes/"+developer +"/2021-01-01/2021-02-15")

    }

}

export default new ProjectService();