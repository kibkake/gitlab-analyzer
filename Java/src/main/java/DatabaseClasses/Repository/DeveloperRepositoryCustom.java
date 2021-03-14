package main.java.DatabaseClasses.Repository;

import main.java.Model.Developer;

/** There is a problem with saving a developer and the work around to hardcode it using mongotemplate.
 *
 */
public interface DeveloperRepositoryCustom {
    void saveDev(Developer dev);

}
