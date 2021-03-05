import axios from 'axios';

const PROJECT_URL = "/api/v1/";

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

    getCodeScore(projectId, committerName) {
        return axios.get(PROJECT_URL+ "projects/commitScoresPerDay/${projectId}/${committerName}")
    }

    getCommentScore(){

    }

    getCodeDiff() {

    }

    getCommentInfo(projectId, committerName, start, end) {
        return axios.get(PROJECT_URL+ "projects/${projectId}/topTenUserNotes/${committerName}/${start}/${end}")
        // axios.get("http://localhost:8080/api/v1/projects/" + id + "/topTenUserNotes/"+developer +"/2021-01-01/2021-02-15")

    }

    getMrsAndCommitScoresPerDay(startTm,endTm ){
         return axios.get("/api/v1/projects/" + id + "/MRsAndCommitScoresPerDay/" + username + '/' +
            startTm + '/' +
            endTm)
    }

    convertMonthToNumber(month) {
        if(month === "Jan"){
            return "01";
        }else if(month === "Feb"){
            return "02";
        }else if(month === "Mar"){
            return "03";
        }else if(month === "Apr"){
            return "04";
        }else if(month === "May"){
            return "05";
        }else if(month === "Jun"){
            return "06";
        }else if(month === "Jul"){
            return "07";
        }else if(month === "Aug"){
            return "08";
        }else if(month === "Sep"){
            return "09";
        }else if(month === "Oct"){
            return "10";
        }else if(month === "Nov"){
            return "11";
        }else{
            return "12"
        }
    }

    async getListOfDevs(repNum){

        let url2 = '/api/v1/getusernames/' + repNum
        const result = await fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        var resp;
        resp = result.json();
        var listOfDevelopers = await resp;
        await sessionStorage.setItem("Developers" + repNum, JSON.stringify(listOfDevelopers));
    }
}

export default new ProjectService();