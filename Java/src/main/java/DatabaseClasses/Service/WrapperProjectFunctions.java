package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.Wrapper.WrapperCommit;
import main.java.ConnectToGitlab.Wrapper.WrapperMergedMergeRequest;
import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import main.java.ConnectToGitlab.Wrapper.WrapperUser;
import main.java.DatabaseClasses.Repository.WrapperCommitRepository;
import main.java.DatabaseClasses.Repository.WrapperMergedMergeRequestRepository;
import main.java.DatabaseClasses.Repository.WrapperProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;


public class WrapperProjectFunctions {

    private String token = "cFzzy7QFRvHzfHGpgrr1";

    @Autowired
    private WrapperProjectRepository projectRepository;

    @Autowired
    private WrapperMergedMergeRequestRepository wrapperMergedMergeRequestRepository;

    @Autowired
    private WrapperCommitRepository wrapperCommitRepository;

    private void setToken(Map<String, String> requestBody) {
        if(requestBody.get("token") != null) {
            token = requestBody.get("token");
            System.out.println(token);
        }
        else{
            System.out.println("token is null!");
        }
    }

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

    public List<String> getProjectMember(String pId) throws IOException, ParseException {
        int projectId = Integer.parseInt(pId);
        WrapperProject project = new WrapperProject(token, projectId);
        return  project.getListOfMembers(token);
    }

    public WrapperUser getProject(String pId, String usrname)
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
