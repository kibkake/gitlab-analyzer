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
        var str = window.location.pathname;
        var repNum = str.split("/")[2];
        let url2 = PROJECT_URL + 'getusernames/' + repNum
        const result = fetch(url2, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        return result
    }

    getCodeScore(projectId, committerName) {
        return axios.get(PROJECT_URL+ "projects/commitScoresPerDay/${projectId}/${committerName}")
    }

    sendUpdateDecision(projectId) {
        return axios.post(PROJECT_URL + "updateRepo").then()
    }

    getCommentInfo(projectId, committerName, start, end) {
        return axios.get(PROJECT_URL+ "projects/${projectId}/topTenUserNotes/${committerName}/${start}/${end}")
        // axios.get("http://localhost:8080/api/v1/projects/" + id + "/topTenUserNotes/"+developer +"/2021-01-01/2021-02-15")

    }

    getMrsAndCommitScoresPerDay(startTm,endTm, id, username){
         return axios.get("/api/v1/projects/" + id + "/MRsAndCommitScoresPerDay/" + username + '/' +
            startTm + '/' +
            endTm + "/either")
    }

    getTopUserNotes(startTm,endTm, id, username){
        return axios.get("/api/v1/projects/" + id + "/topTenUserNotes/" + username + "/" + startTm + '/' + endTm)
    }

    convertMonthToNumber(month) {
        const monthList = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
                         "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
        const monthNum = monthList.indexOf(month) + 1;
        return monthNum < 10 ? '0'.concat(monthNum.toString()) : monthNum.toString();
    }

    async storeDevelopers(repNum) {
        console.log("getting list of users")

        let url2 = '/api/v2/Project/' + repNum + '/Developers/all'
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