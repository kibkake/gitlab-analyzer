import axios from 'axios';

const REST_API = "/api/v1/";

export default class SnapshotService {

    static getSnapshot(snapId){
        return axios.get(REST_API+"getSnapshot/"+snapId);
    }

    static getSnapshots(username){
        return axios.get(REST_API+"getSnapshots/"+username);
    }

    static deleteSnapshot(snapId){
        return axios.get(REST_API+"deleteSnapshot/"+snapId);
    }

    static saveSnapshot(username,startDate,endDate,projectId,dev, page){
        const snapInfo = JSON.stringify({ 'username':username, 'startDate':startDate,'endDate':endDate,'projectId':projectId,'dev':dev,'page':page});
        console.log(snapInfo);
        return axios.post(REST_API+"saveSnapshot", snapInfo, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

}