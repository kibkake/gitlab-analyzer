import axios from 'axios';

const REST_API = "http://142.58.22.166:8090/api/v1/user";

export default class LoginService {

    static checkUserCredentials(username, password) {
        return axios.get(REST_API+"/userAuthenticated/" + username + "/" + password);
    }

    static checkUserExists(username){
        return axios.get(REST_API+"/"+username);
    }

    static createNewAccount(username,password,token){
        const userInfo = JSON.stringify({ 'username': username,'password':password,'token':token });
        return axios.post(REST_API, userInfo, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

}