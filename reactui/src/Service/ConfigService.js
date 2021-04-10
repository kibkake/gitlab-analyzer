import axios from 'axios';

const REST_API = "/api/v1/";

export default class ConfigService {

    static getConfig(configId){
        return axios.get(REST_API+"getConfig/"+configId);
    }


    static deleteConfig(configId){
        return axios.get(REST_API+"deleteCongif/"+configId);
    }

    static saveConfig(startDate,endDate){
        const configInfo = JSON.stringify({ 'startDate':startDate,'endDate':endDate,'languageScale':sessionStorage.getItem('languageScale')});
        console.log(configInfo);
        return axios.post(REST_API+"saveConfig",configInfo, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

}