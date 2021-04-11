import axios from 'axios';

const REST_API = "/api/v1/";

export default class ConfigService {

    static getConfig(configId){
        return axios.get(REST_API+"getConfig/"+configId);
    }

    static getConfigs(){
        return axios.get(REST_API+"getConfigs/");
    }

    static deleteConfig(configId){
        console.log(configId);
        return axios.get(REST_API+"deleteConfig/"+configId);
    }

    static saveConfig(name,startDate,endDate){
        const configInfo = JSON.stringify({ 'name':name,'startDate':startDate,'endDate':endDate,'languageScale':sessionStorage.getItem('languageScale')});
        console.log(configInfo);
        return axios.post(REST_API+"saveConfig",configInfo, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

}