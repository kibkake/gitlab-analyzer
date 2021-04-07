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

    //expandbutton
    checkAllFilesAreIgnored(commit, hash){
        console.log(commit)

        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var tempHash = hash
        var allFilesExcluded = true

        commit.map(function (item) {
            item.diffs.map(function (item2) {
                var isExcluded = false;
                for (var i = 0; i < tempArray.length; i++) {
                    if (tempArray[i] === tempHash + "_" + item2.new_path) {
                        isExcluded = true
                    }
                }
                if(!isExcluded){
                    allFilesExcluded = false
                }
            })
        });

        return allFilesExcluded
    }

    ignoreAllFilesOfCommit(commit, hash){
        console.log(commit)

        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var tempHash = hash

        commit.map(function (item) {
            item.diffs.map(function (item2) {
                var isExcluded = false;
                for (var i = 0; i < tempArray.length; i++) {
                    if (tempArray[i] === tempHash + "_" + item2.new_path) {
                        isExcluded = true
                    }
                }
                if(!isExcluded){
                    tempArray.push(tempHash + "_" + item2.new_path)
                }
            })
        });

        sessionStorage.setItem("excludedFiles",JSON.stringify( tempArray))
    }

    reAddAllFilesOfCommit(commit, hash){
        console.log(commit)

        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var tempHash = hash

        commit.map(function (item) {
            item.diffs.map(function (item2) {
                var isExcluded = false;
                var index = -1
                for (var i = 0; i < tempArray.length; i++) {
                    if (tempArray[i] === tempHash + "_" + item2.new_path) {
                        isExcluded = true
                        index = i
                    }
                }
                if(isExcluded && index != -1){
                    tempArray.splice(index, 1);
                }
            })
        });

        sessionStorage.setItem("excludedFiles",JSON.stringify( tempArray))
    }

    calculateExcludedScore(commit, hash){
        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var tempHash = hash
        var excludedScore = 0.0
        if(hash != null) {
            commit.map(function (item) {
                item.diffs.map(function (item2) {
                    for (var i = 0; i < tempArray.length; i++) {
                        if (tempArray[i] === tempHash + "_" + item2.new_path) {
                            excludedScore += item2.diffScore
                        }
                    }
                })
            });
        }
        return excludedScore
    }
    //
}

export default new CommitService();