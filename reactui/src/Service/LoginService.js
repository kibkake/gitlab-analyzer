import axios from 'axios';

const REST_API = "http://localhost:8080/api/v1/user";

export default class LoginService {
    static checkUserCredentials(username, password){
        return axios.get(REST_API+"/userAuthenticated/"+username+"/"+password);
    }

}