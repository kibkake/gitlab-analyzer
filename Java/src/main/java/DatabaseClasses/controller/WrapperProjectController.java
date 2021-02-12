package main.java.DatabaseClasses.controller;


import main.java.ConnectToGitlab.Wrapper.WrapperCommit;
import main.java.ConnectToGitlab.Wrapper.WrapperMergedMergeRequest;
import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import main.java.DatabaseClasses.repository.WrapperCommitRepository;
import main.java.DatabaseClasses.repository.WrapperMergedMergeRequestRepository;
import main.java.DatabaseClasses.repository.WrapperProjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.Query;
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

    @Autowired
    private WrapperCommitRepository wrapperCommitRepository;


    @GetMapping("addproject")
    public void saveProject() throws IOException, ParseException {
        WrapperProject project = new WrapperProject ("cFzzy7QFRvHzfHGpgrr1", 6);
        List<WrapperMergedMergeRequest> mergedMergeRequests = project.getMergedMergeRequestsFromServer("cFzzy7QFRvHzfHGpgrr1", 6);
        List<WrapperCommit> commits = new ArrayList<>();
        projectRepository.save(project);
        for(int i = 0; i < mergedMergeRequests.size(); i++){
            commits.addAll(mergedMergeRequests.get(i).getSingleMergedMergeRequestCommits("cFzzy7QFRvHzfHGpgrr1"));
        }
        wrapperMergedMergeRequestRepository.saveAll(mergedMergeRequests);
        wrapperCommitRepository.saveAll(commits);
    }

    @GetMapping("getproject")
    public WrapperProject getProject() throws IOException, ParseException {
        Optional<WrapperProject> project = projectRepository.findById(6);
        List<Integer> mergeRequestIds = new ArrayList<>();
        List<WrapperMergedMergeRequest> mergedMergeRequests = new ArrayList<>();

        if(project.isPresent()){
            mergeRequestIds = project.get().getMergeRequestIds();
            Iterator<WrapperMergedMergeRequest> itr = wrapperMergedMergeRequestRepository.findAllById(
                    project.get().getMergeRequestIds()).iterator();
            while (itr.hasNext()){
                mergedMergeRequests.add(itr.next());
            }
            project.get().addMergedMergeRequests(mergedMergeRequests);
            //getCommitID();

            for(int i = 0; i < project.get().getMergedMergeRequests().size(); i++){


                List<WrapperCommit> commits = new ArrayList<>();
                for(int j = 0; j < project.get().getMergedMergeRequests().get(i).getMergeRequestCommitIds().size(); j++ ){
                    commits.add(wrapperCommitRepository.findByID(project.get().getMergedMergeRequests().get(i).getMergeRequestCommitIds().get(j)));
                }
                project.get().getMergedMergeRequests().get(i).addMergedMergeRequestsCommits(commits);
                System.out.println(project.get().getMergedMergeRequests().get(i).getMERGE_DIFFS().get(0).getDiff());

            }




            return project.get();

        }
        return null;
    }

    //@Query("{ '_id': ?0 }")
    public void getCommitID(){
        System.out.println(wrapperCommitRepository.findByID("ac108a6cab6e2b63c8e2d4a1150ac67ba82849d0").getTitle());
    }


}

