package main.java.DatabaseClasses.controller;


import main.java.ConnectToGitlab.Wrapper.WrapperMergedMergeRequest;
import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import main.java.DatabaseClasses.repository.WrapperMergedMergeRequestRepository;
import main.java.DatabaseClasses.repository.WrapperProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@RestController("WrapperProjectController")
//@RequestMapping(path="projects")
public class WrapperProjectController {


    @Autowired
    private WrapperProjectRepository projectRepository;

    @Autowired
    private WrapperMergedMergeRequestRepository wrapperMergedMergeRequestRepository;


    @GetMapping("addproject")
    public void saveProject() throws IOException, ParseException {
        WrapperProject project = new WrapperProject ("cFzzy7QFRvHzfHGpgrr1", 6);
        List<WrapperMergedMergeRequest> mergedMergeRequests = project.getMergedMergeRequestsFromServer("cFzzy7QFRvHzfHGpgrr1", 6);
        projectRepository.save(project);
        wrapperMergedMergeRequestRepository.saveAll(mergedMergeRequests);
    }

}

