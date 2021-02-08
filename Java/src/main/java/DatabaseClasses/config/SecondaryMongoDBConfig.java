//package DatabaseClasses.config;
//
//import DatabaseClasses.model.Member;
//import DatabaseClasses.model.Projects;
//import DatabaseClasses.repository.MemberRepository;
//import DatabaseClasses.repository.ProjectsRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//@EnableMongoRepositories(basePackageClasses = MemberRepository.class)
//public class SecondaryMongoDBConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(MemberRepository memberRepository) {
//        return strings -> {
//
//            memberRepository.save(new Member(1, "yka87"));
//            System.out.println("hello, member");
//
//            System.out.println(memberRepository.findAll());
//
//            // save methods for other data from api could be added more
//        };
//    }
//}
