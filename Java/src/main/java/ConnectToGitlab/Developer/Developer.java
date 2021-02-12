package main.java.ConnectToGitlab.Developer;

import main.java.ConnectToGitlab.Commit.Commit;
import main.java.ConnectToGitlab.MergeRequests.MergeRequest;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  Class hold information about the different users on gitlab used to convert JSON to an object with spring
 *  We call this class developer to not get confused between the user of our product and gitlab users. So we
 *  call gitlab users developers
 */
@Document(value = "Developer")
public class Developer {
    private int id;
    private String name;
    private String username;
    private String state;
    private String avatar_url;
    private String web_url;
    private List<String> emails;

    public Developer() {
    }

    public Developer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    //https://stackoverflow.com/questions/122105/what-is-the-best-way-to-filter-a-java-collection/1385698
    public List<Commit> getDevCommits(List<Commit> commits) {
        List<Commit> filteredCommits = commits.stream()
                .filter(p -> p.getAuthor_name().equals(this.name)).collect(Collectors.toList());
        return filteredCommits;
    }

    public List<MergeRequest> getDevMergeRequests(List<MergeRequest> mergeRequests) {
        List<MergeRequest> filteredList = mergeRequests.stream()
                .filter(mrs -> mrs.getContributors().stream()
                        .anyMatch(devs ->
                                devs.getId() == this.id))
                .collect(Collectors.toList());
        return filteredList;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", web_url='" + web_url + '\'' +
                '}';
    }
}
