package main.java.DatabaseClasses.controller;


import main.java.ConnectToGitlab.Wrapper.WrapperProject;
import main.java.DatabaseClasses.repository.WrapperProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.text.ParseException;


@RestController("WrapperProjectController")
//@RequestMapping(path="projects")
public class WrapperProjectController {


    @Autowired
    private WrapperProjectRepository projectRepository;


    @GetMapping("addproject")
    public void saveProject() throws IOException, ParseException {
        WrapperProject project = new WrapperProject ("cFzzy7QFRvHzfHGpgrr1", 6);
        projectRepository.save(project);
    }

}

