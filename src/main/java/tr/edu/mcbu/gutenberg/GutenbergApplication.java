package tr.edu.mcbu.gutenberg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
        basePackages = "tr.edu.mcbu.gutenberg.repository" )
@EnableJpaAuditing
@EnableScheduling
public class GutenbergApplication {

    /*
    * @Author Özer Utku Altun
    * This project was written by Celal Bayar University
    * 4th Year Student Özer Utku Altun for the Software Engineering
    * course term project.
    * Any part of this project cannot be shared without reference.
    *
    * With all my respects.
    * */

    public static void main(String[] args) {
        SpringApplication.run(GutenbergApplication.class, args);
    }

}
