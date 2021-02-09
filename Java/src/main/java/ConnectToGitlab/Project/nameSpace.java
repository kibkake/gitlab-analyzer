package main.java.ConnectToGitlab.Project;

/**
 * Holds the info about the name of a project
 */
public class nameSpace {
    private int id;
    private String name;
    private String path;
    private String kind;
    private String full_path;
    private String parent_id;
    private String avatar_url;
    private String web_url;

    public nameSpace() {
    }

    public nameSpace(int id, String name, String path, String kind, String full_path, String parent_id, String avatar_url, String web_url) {

        this.id = id;
        this.name = name;
        this.path = path;
        this.kind = kind;
        this.full_path = full_path;
        this.parent_id = parent_id;
        this.avatar_url = avatar_url;
        this.web_url = web_url;
    }

}