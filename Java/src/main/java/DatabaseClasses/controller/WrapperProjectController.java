package main.java.DatabaseClasses.Controller;


import main.java.ConnectToGitlab.Wrapper.WrapperCommit;
import main.java.ConnectToGitlab.Wrapper.WrapperMergedMergeRequest;
import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import main.java.ConnectToGitlab.Wrapper.WrapperUser;
import main.java.DatabaseClasses.Repository.WrapperCommitRepository;
import main.java.DatabaseClasses.Repository.WrapperMergedMergeRequestRepository;
import main.java.DatabaseClasses.Repository.WrapperProjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;


@RestController("WrapperProjectController")
public class WrapperProjectController {

    private String token = "cFzzy7QFRvHzfHGpgrr1";

    @Autowired
    private WrapperProjectRepository projectRepository;

    @Autowired
    private WrapperMergedMergeRequestRepository wrapperMergedMergeRequestRepository;

    @Autowired
    private WrapperCommitRepository wrapperCommitRepository;

    //curl -i -X POST -d "{\"token\":\"cFzzy7QFRvHzfHGpgrr1\"}" -H "Content-Type:application/json" http://localhost:8080/settoken
    @PostMapping("settoken")
    private void setToken(@RequestBody Map<String, String> requestBody) {
        if(requestBody.get("token") != null) {
            token = requestBody.get("token");
            System.out.println(token);
        }
        else{
            System.out.println("token is null!");
        }
    }

    @GetMapping("addproject")
    private void saveProject() throws IOException, ParseException {
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

    @CrossOrigin
    @GetMapping("getprojectmembers/{pId}")
    public List<String> getProjectMember(@PathVariable String pId) throws IOException, ParseException {
        int projectId = Integer.parseInt(pId);
        WrapperProject project = new WrapperProject(token, projectId);
        return  project.getListOfMembers(token);
    }

    @CrossOrigin
    @GetMapping("getuserstats/{pId}/{usrname}")
    public WrapperUser getProject(@PathVariable String pId, @PathVariable String usrname)
        throws IOException, ParseException {
        int projectId = Integer.parseInt(pId);
        String userName = usrname;

        Optional<WrapperProject> project = projectRepository.findById(projectId);
        List<Integer> mergeRequestIds = new ArrayList<>();
        List<WrapperMergedMergeRequest> mergedMergeRequests = new ArrayList<>();

        if(project.isEmpty()) {
            System.out.println("empty");
            WrapperProject gitlabProject = new WrapperProject (token, projectId);
            List<WrapperMergedMergeRequest> gitlabMergedMergeRequests = gitlabProject.getMergedMergeRequestsFromServer
                    (token, projectId);
            List<WrapperCommit> gitlabCommits = new ArrayList<>();
            projectRepository.save(gitlabProject);
            for(int i = 0; i < gitlabMergedMergeRequests.size(); i++){
                gitlabCommits.addAll(gitlabMergedMergeRequests.get(i).getSingleMergedMergeRequestCommits(token));
            }
            wrapperMergedMergeRequestRepository.saveAll(gitlabMergedMergeRequests);
            wrapperCommitRepository.saveAll(gitlabCommits);
            project = projectRepository.findById(projectId);
        }
        //System.out.println(project.get().getListOfMembers(token));
        mergeRequestIds = project.get().getMergeRequestIds();
        Iterator<WrapperMergedMergeRequest> itr = wrapperMergedMergeRequestRepository.findAllById(
                project.get().getMergeRequestIds()).iterator();
        while (itr.hasNext()){
            mergedMergeRequests.add(itr.next());
        }

        project.get().addMergedMergeRequests(mergedMergeRequests);

        for(int i = 0; i < project.get().getMergedMergeRequests().size(); i++){
            List<WrapperCommit> commits = new ArrayList<>();
            for(int j = 0; j < project.get().getMergedMergeRequests().get(i).getmergeRequestCommitIds().size(); j++ ){
                commits.add(wrapperCommitRepository.findByID(project.get().getMergedMergeRequests().get(i).getmergeRequestCommitIds().get(j)));
            }
            project.get().getMergedMergeRequests().get(i).addMergedMergeRequestsCommits(commits);
        }
        return getUserData(userName, project.get());
    }

    private WrapperUser getUserData(String userName, WrapperProject project){
        WrapperUser user = new WrapperUser(userName, project);
        return user;
    }


}

