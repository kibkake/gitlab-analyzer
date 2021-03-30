import axios from 'axios';

const REST_API = "/api/v1/";

export default class SnapshotService {

    static getSnapshots(username){
        return axios.get(REST_API+"getSnapshots/"+username);
    }

    // static setUserToken(token){
    //     const tokenInfo = JSON.stringify({ 'token':token });
    //     return axios.post(REST_API+"/setToken", tokenInfo, {
    //         headers: {
    //             'Content-Type': 'application/json'
    //         }
    //     });
    // }

}