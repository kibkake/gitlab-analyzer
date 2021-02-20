import axios from 'axios';

const PROJECT_REST_API_URL = "http://localhost:8090/";

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

    getCodeScore() {

    }

    getCommentScore(){

    }

    getCodeDiff() {
        return axios.get("http://localhost:8090/getuserstats/6/arahilin");
    }

    getCommentInfo() {

    }

}

export default new ProjectService();