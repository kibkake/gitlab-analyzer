import React, { Component } from 'react';
import ProjectList from "./ProjectList";

class ProjectComponent extends Component {
    render() {
        return (
            <>
                <h1>Projects</h1>
                <ProjectList/>
            </>
        )
    }
}

export default ProjectComponent