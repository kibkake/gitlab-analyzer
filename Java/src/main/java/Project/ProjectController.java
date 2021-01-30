package main.java.Project;

import main.java.Project.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProjectController {

/*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    public static void getProjects() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Project[]> responseEntity= restTemplate.getForEntity("https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects?simple=true", Project[].class);
        Project[] projects = responseEntity.getBody();
        for(int i = 0; i < projects.length; i ++) {
            System.out.println(projects[i]);
        }
    }
}
