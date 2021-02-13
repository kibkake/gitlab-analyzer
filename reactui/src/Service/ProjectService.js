import axios from 'axios';

const PROJECT_REST_API_URL = "http://localhost:8080/";

/*
  This class contains methods for sending HTTP requests to Apis by spring.
 */

// For now can't really work on... The structure of DB & RestController should be set in the backend
class ProjectService {

    getAll() {
        return axios.get(PROJECT_REST_API_URL);
    }
}

export default new ProjectService();