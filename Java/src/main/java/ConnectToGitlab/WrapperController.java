package main.java.ConnectToGitlab;

import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import main.java.DatabaseClasses.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/projects/")
public class WrapperController {

    @Autowired
    private RestTemplate restTemplate;

    private final WrapperProjectService wrapperProjectService;

    public WrapperController(WrapperProjectService wrapperProjectService) {
        this.wrapperProjectService = wrapperProjectService;
    }


    @GetMapping("{projectId}")
    public List<WrapperProject> getProject(@PathVariable("projectId") Integer projectId) throws IOException, ParseException {
        User user = User.getInstance();

        if(wrapperProjectService.getProject().isEmpty()) {
            List<WrapperProject> project = (List<WrapperProject>) ConnectToGitlab.connectGitlab(user.getToken());
            wrapperProjectService.addProject(project);
            return project;
        }
        System.out.println("not in iff");
        return wrapperProjectService.getProject();
    }
}
