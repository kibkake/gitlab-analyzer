package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.DateScore;

import java.util.List;

public interface MergeRequestRepositoryCustom {

    //https://stackoverflow.com/questions/19583540/spring-data-jpa-no-property-found-for-type-exception
    // Spring tries to auto generate quires based of fucntion name =, nned to mkae sure we are not following any of these
    // when creating a custom function
    List<DateScore> devsMrsADay(int projectId, String devUserName);

}
