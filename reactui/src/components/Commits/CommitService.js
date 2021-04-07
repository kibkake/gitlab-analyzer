import axios from 'axios';
import TableCell from "@material-ui/core/TableCell";
import React from "react";
const PROJECT_URL = "/api/v1/";

class CommitService {
    //rowcommitdiff
    addFileToListOfExcluded(tempArray, newF, hash) {
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        for(var i = 0; i < tempArray.length; i++){
            if(tempArray[i] === hash + "_" + newF ){
                return
            }
        }
        tempArray.push(hash + "_" + newF)
        sessionStorage.setItem("excludedFiles",JSON.stringify( tempArray))
        console.log("hello", JSON.parse(sessionStorage.getItem("excludedFiles")))
    }


    checkIfFileIsExcluded(tempArray, newF, hash){
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        for(var i = 0; i < tempArray.length; i++){
            if(tempArray[i] === hash + "_" + newF ){
                return true;
            }
        }
        return false;
    }

    removeFromExcludedFiles(tempArray, newF, hash){
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        for(var i = 0; i < tempArray.length; i++){
            if(tempArray[i] === hash + "_" + newF ){
                tempArray.splice(i, 1);
                //return
            }
        }
        sessionStorage.setItem("excludedFiles",JSON.stringify( tempArray))
        console.log("hello", JSON.parse(sessionStorage.getItem("excludedFiles")))
    }
    //

}

export default new CommitService();