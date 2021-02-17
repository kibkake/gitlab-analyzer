package main.java.DatabaseClasses.Controller;


import main.java.ConnectToGitlab.Wrapper.*;
import main.java.DatabaseClasses.Repository.WrapperCommitRepository;
import main.java.DatabaseClasses.Repository.WrapperMergedMergeRequestRepository;
import main.java.DatabaseClasses.Repository.WrapperProjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@RestController("WrapperProjectController")
public class WProjectController {

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
    //http://localhost:8080/getuserstats/6/arahilin/1-11-2021/2-22-2021
    @CrossOrigin
    @GetMapping("getuserstats/{pId}/{username}/{fromdate}/{todate}")
    private List<String> getUserCommitPerDay(@PathVariable String pId, @PathVariable String username, @PathVariable String fromdate,
                                             @PathVariable String todate) throws IOException, ParseException {

        WrapperUser user = getProject(pId, username);

//1-15-2021
        int fromMonth = Integer.parseInt(fromdate.split("-")[0]);
        int fromDay = Integer.parseInt(fromdate.split("-")[1]);
        int fromYear = Integer.parseInt(fromdate.split("-")[2]);

        int toMonth = Integer.parseInt(todate.split("-")[0]);
        int tomDay = Integer.parseInt(todate.split("-")[1]);
        int toYear = Integer.parseInt(todate.split("-")[2]);

        LocalDate date = LocalDate.of(fromYear,fromMonth , fromDay);
        LocalDate date2 = LocalDate.of(toYear,toMonth , tomDay);


        List<String> commits = new ArrayList<>();

        for (LocalDate start = date; start.isBefore(date2); start = start.plusDays(1))
        {
            int commitNumber = 0;
            for(int i = 0; i < user.getMergedMergeRequests().size(); i++) {
                for(int j = 0; j < user.getMergedMergeRequests().get(i).getMergeRequestCommits().size(); j++){

                    if(user.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getAuthorName().equals(username)
                            &&user.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getCommitYear() == start.getYear()
                            &&user.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getCommitMonth() == start.getMonthValue()
                            &&user.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getCommitDay() == start.getDayOfMonth()){
                        commitNumber += 1;
                    }
                }
            }

            commits.add(Integer.toString(commitNumber));
        }
        return commits;
    }

    @CrossOrigin
    @GetMapping("getuserstats/{pId}/{username}/commit/{date}")
    private List<String> getUserCommitOnSpecificDay(@PathVariable String pId, @PathVariable String username, @PathVariable String date)
            throws IOException, ParseException {
        WrapperUser user = getProject(pId, username);
//1-15-2021

        int Month = Integer.parseInt(date.split("-")[0]);
        int Day = Integer.parseInt(date.split("-")[1]);
        int Year = Integer.parseInt(date.split("-")[2]);

        List<String> commits = new ArrayList<>();

        for(int i = 0; i < user.getMergedMergeRequests().size(); i++) {
            for(int j = 0; j < user.getMergedMergeRequests().get(i).getMergeRequestCommits().size(); j++){
                if(user.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getAuthorName().equals(username)
                        &&user.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getCommitYear() == Year
                        &&user.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getCommitMonth() == Month
                        &&user.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getCommitDay() == Day){
                    commits.add(user.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getId());
                }
            }
        }

        return commits;
    }

    public WrapperProject getProject2(@PathVariable String pId, @PathVariable String usrname)
            throws IOException, ParseException {
        int projectId = Integer.parseInt(pId);
        String userName = usrname;

        Optional<WrapperProject> project = projectRepository.findById(projectId);
        List<Integer> mergeRequestIds = new ArrayList<>();
        List<WrapperMergedMergeRequest> mergedMergeRequests = new ArrayList<>();

        if (project.isEmpty()) {
            System.out.println("empty");
            WrapperProject gitlabProject = new WrapperProject(token, projectId);
            List<WrapperMergedMergeRequest> gitlabMergedMergeRequests = gitlabProject.getMergedMergeRequestsFromServer
                    (token, projectId);
            List<WrapperCommit> gitlabCommits = new ArrayList<>();
            projectRepository.save(gitlabProject);
            for (int i = 0; i < gitlabMergedMergeRequests.size(); i++) {
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
        while (itr.hasNext()) {
            mergedMergeRequests.add(itr.next());
        }

        project.get().addMergedMergeRequests(mergedMergeRequests);

        for (int i = 0; i < project.get().getMergedMergeRequests().size(); i++) {
            List<WrapperCommit> commits = new ArrayList<>();
            for (int j = 0; j < project.get().getMergedMergeRequests().get(i).getmergeRequestCommitIds().size(); j++) {
                commits.add(wrapperCommitRepository.findByID(project.get().getMergedMergeRequests().get(i).getmergeRequestCommitIds().get(j)));
            }
            project.get().getMergedMergeRequests().get(i).addMergedMergeRequestsCommits(commits);
        }
        return project.get();
    }
    @CrossOrigin
    @GetMapping("getuserstats/{pId}/{username}/commitdiff/{commithash}")
    private List<WrapperCommit> getUserCommitdiff(@PathVariable String pId, @PathVariable String username,
                                                    @PathVariable String commithash) throws IOException, ParseException {

        WrapperProject project = getProject2(pId, username);
        List<WrapperCommit> commitdiff = new ArrayList<>();

        for(int i = 0; i < project.getMergedMergeRequests().size(); i++) {
            for(int j = 0; j < project.getMergedMergeRequests().get(i).getMergeRequestCommits().size(); j++){
                if(project.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j).getId().equals(commithash)){
                    commitdiff.add(project.getMergedMergeRequests().get(i).getMergeRequestCommits().get(j));
                    return commitdiff;
                }
            }
        }

        return commitdiff;
    }
}

