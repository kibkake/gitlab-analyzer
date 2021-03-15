import ProjectList from "../components/Projects/ProjectList";

function Repo(){
    return(
        <>
            <div className='projects'>
                <br></br>
                <h1 style={{textAlign:'center'}}>Project List</h1>
                <br></br>

                <ProjectList/>
            </div>
        </>
    )


}

export default Repo;