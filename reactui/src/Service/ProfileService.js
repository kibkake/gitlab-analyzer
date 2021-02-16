import axios from 'axios';

const REST_API = "http://localhost:8080/api/v1/user";

export default class ProfileService {
    static getUserInfo(username){
        return axios.get(REST_API+"/"+username);
    }

    static logout(){
        sessionStorage.clear();
    }
}