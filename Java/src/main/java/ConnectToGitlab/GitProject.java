package main.java.ConnectToGitlab;

import org.gitlab.api.models.GitlabCommit;
import org.gitlab.api.models.GitlabMergeRequest;
import org.gitlab.api.models.GitlabProjectMember;
import java.util.List;

public class GitProject {

    public GitProject gitProject;
    public List<GitlabProjectMember> gitlabProjectMembers;
    public List<GitlabMergeRequest> gitlabMergeRequests;
    public List<GitlabCommit> gitlabAllCommits;
    public List<GitlabCommit> gitAllMergeCommits;

}
