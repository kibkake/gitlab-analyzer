import TableCell from "@material-ui/core/TableCell";

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

    //commitperday
    calculateExcludedScoreWithoutHashProvided(commits){
        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var excludedScore = 0.0
        commits.map(function (item) {
            item.diffs.map(function (item2) {
                for (var i = 0; i < tempArray.length; i++) {
                    if (tempArray[i] === item.id + "_" + item2.new_path) {
                        excludedScore += item2.diffScore
                    }
                }
            })
        });
        return excludedScore
    }
    //

    //commitperdayinfo/commitinfo
    calculateNumberOfExcludedFilesInCommit(diffs, id){
        var tempArray = []
        tempArray = JSON.parse(sessionStorage.getItem("excludedFiles"))
        var numberOfFilesExcluded = 0
        diffs.map(function (item2) {
            for (var i = 0; i < tempArray.length; i++) {
                if (tempArray[i] === id + "_" + item2.new_path) {
                    numberOfFilesExcluded++
                }
            }
        })
        return numberOfFilesExcluded
    }

    calculateNumberOfFilesInCommit(diffs){
        var numberOfFilesInCommit = 0
        diffs.map(function (item2) {
            numberOfFilesInCommit++
        })
        return numberOfFilesInCommit
    }

    adjustTheColorOfScore(numberOfFilesExcluded, numberOfFilesInCommit, commitScore){
        if(numberOfFilesInCommit === numberOfFilesExcluded){
            return(
                <TableCell align="right">
                    {numberOfFilesExcluded > 0 ?
                        <div
                            style={{color:"red", fontSize:"15", fontWeight:"bold"}}>
                            {commitScore.toFixed(1)} </div> :
                        <div style={{color:"blue", fontSize:"15", fontWeight:"bold"}}>
                            {commitScore.toFixed(1)}
                        </div>}
                </TableCell>
            )
        }else{
            return(
                <TableCell align="right">
                    {numberOfFilesExcluded > 0 ?
                        <div
                            style={{color:"darkorange", fontSize:"15", fontWeight:"bold"}}>
                            {commitScore.toFixed(1)} </div> :
                        <div style={{color:"blue", fontSize:"15", fontWeight:"bold"}}>
                            {commitScore.toFixed(1)} </div>}
                </TableCell>
            )
        }
    }

    initializeStorageForExcludedFiles(){
        if(sessionStorage.getItem("excludedFiles") === null){
            var fileArray = []
            sessionStorage.setItem("excludedFiles",  JSON.stringify(fileArray))
        }
    }
}

export default new CommitService();