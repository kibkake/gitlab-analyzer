import SingleCommitDiffs from "../components/SingleCommitDiffs";
import Navbar_Developers from "../components/Navbar_Developers";
import React from "react";

function SingleCommit(){

    return(


        <header classname='Rest'>
            <Navbar_Developers/>
            <SingleCommitDiffs/>
        </header>

    )
}

export default SingleCommit;