import axios from 'axios';

const REST_API = "/api/v1/user";

export default class ProfileService {

    static changeUserPassword(username,password){
        const userInfo = JSON.stringify({ 'username': username,'password':password });
        return axios.post(REST_API+"/changePass", userInfo, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    static changeUserToken(username,token){
        const userInfo = JSON.stringify({ 'username': username,'token':token });
        return axios.post(REST_API+"/changeToken", userInfo, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    static getUserInfo(username){
        return axios.get(REST_API+"/"+username);
    }

    static logout(){
        sessionStorage.clear();
    }
}