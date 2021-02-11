package main.java.DatabaseClasses.controller;


import main.java.ConnectToGitlab.Wrapper.WrapperMergedMergeRequest;
import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import main.java.DatabaseClasses.repository.WrapperMergedMergeRequestRepository;
import main.java.DatabaseClasses.repository.WrapperProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


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

    @GetMapping("getproject")
    public WrapperProject getProject() throws IOException, ParseException {
        Optional<WrapperProject> project = projectRepository.findById(6);
        List<Integer> mergeRequestIds = new ArrayList<>();
        List<WrapperMergedMergeRequest> mergedMergeRequests = new ArrayList<>();
        if(project.isPresent()){
            mergeRequestIds = project.get().getMergeRequestIds();
            Iterator<WrapperMergedMergeRequest> itr = wrapperMergedMergeRequestRepository.findAllById(project.get().getMergeRequestIds()).iterator();
            while (itr.hasNext()){
                mergedMergeRequests.add(itr.next());
            }

            project.get().addMergedMergeRequests(mergedMergeRequests);
            return project.get();

        }
        return null;
    }


}

