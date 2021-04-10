package main.java.DatabaseClasses.Controller;

import main.java.Collections.Commit;
import main.java.DatabaseClasses.Repository.MergeRequest.MergeRequestRepository;
import main.java.DatabaseClasses.Service.MergeRequestService;
import main.java.Collections.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v2/")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;
    private final MergeRequestRepository mergeRequestRepository;
    private String isoEnding = "T00:00:00.000Z";
    
    @Autowired
    public MergeRequestController(MergeRequestService mergeRequestService, MergeRequestRepository mergeRequestRepository) {
        this.mergeRequestService = mergeRequestService;
        this.mergeRequestRepository = mergeRequestRepository;
    }

    @GetMapping("projects/{projectId}/MergeRequest/save")
    public void saveProjectMergeRequests(@PathVariable int projectId) {
        mergeRequestService.saveProjectMergeRequests(projectId);
    }

    @GetMapping("projects/{projectId}/mergeRequest/{mrId}")
    public MergeRequest getSingleMergeRequest(@PathVariable int mrId, @PathVariable int projectId) {
        return mergeRequestService.getMergeRequest(projectId, mrId);
    }

    @GetMapping("projects/{projectId}/comms/{username}/{name}/{sd}/{end}")
    public List<Commit> getC(@PathVariable int mrId, @PathVariable int projectId, @PathVariable String end, @PathVariable String sd, @PathVariable String username, @PathVariable String name) {
        LocalDate ss = LocalDate.parse(sd);
        LocalDate dd = LocalDate.parse(end);

        return mergeRequestRepository.getDevCommits(projectId, username, name, ss, dd);
    }

}
