package main.java.DatabaseClasses.Repository.Developer;

import main.java.Collections.Developer;

/** There is a problem with saving a developer and the work around to hardcode it using mongotemplate.
 *
 */
public interface DeveloperRepositoryCustom {
    void saveDev(Developer dev);

}
