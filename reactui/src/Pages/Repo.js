import RepoButton from "../components/RepoButton";
import ProjectComponent from "../components/ProjectComponent";

function Repo(){
    return(
        <>
        <div classname='repo'>
           <RepoButton/>
        </div>
        <div classname='projects'>
            <ProjectComponent/>
        </div>
        </>
    )


}

export default Repo;