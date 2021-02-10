package main.java.ConnectToGitlab;

import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WrapperProjectService {

    private final WrapperProjectRepository wrapperProjectRepository;

    @Autowired
    public WrapperProjectService(WrapperProjectRepository wrapperProjectRepository) {
        this.wrapperProjectRepository = wrapperProjectRepository;
    }

    public List<WrapperProject> getProject() {
        return wrapperProjectRepository.findAll();
    }

    public void addProject(List<WrapperProject> project) {
        wrapperProjectRepository.saveAll(project);
    }
}
