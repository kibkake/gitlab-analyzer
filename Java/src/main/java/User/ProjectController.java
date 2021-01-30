package User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProjectController {

/*  TODO change to autowired this is the proper way using beans
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
