import RepoButton from "../components/RepoButton";
import ProjectComponent from "../components/ProjectComponent";
import DateRangeSettingComponent from "../components/DateRangeSettings";

function Repo(){
    return(
        <>
            <div className='dateRangeSetting'>
                <DateRangeSettingComponent/>
            </div>
            <div className='projects'>
                <ProjectComponent/>
            </div>
        </>
    )


}

export default Repo;