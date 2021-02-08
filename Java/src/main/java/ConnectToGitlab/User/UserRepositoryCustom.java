package main.java.ConnectToGitlab.User;

public interface UserRepositoryCustom {

    void createUserAccount(User user);

    User retrieveUserInfo(String username);
}

