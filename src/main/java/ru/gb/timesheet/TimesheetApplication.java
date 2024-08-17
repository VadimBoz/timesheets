package ru.gb.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCrypt;
import ru.gb.timesheet.aspect.Recover;
import ru.gb.timesheet.model.Person;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Role;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.PersonRepository;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.RoleRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class TimesheetApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(TimesheetApplication.class, args);

//		JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
//		jdbcTemplate.execute("delete from project");

		ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);
		PersonRepository personRepo = ctx.getBean(PersonRepository.class);
		TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);
		RoleRepository roleRepo = ctx.getBean(RoleRepository.class);

		timesheetRepo.deleteAll();
		personRepo.deleteAll();
		projectRepo.deleteAll();
//		projectRepo.findAll().forEach(System.out::println);


		Role adminRole = new Role();
		adminRole.setName("admin");
		Role userRole = new Role();
		userRole.setName("user");
		Role restRole = new Role();
		restRole.setName("rest");
		roleRepo.save(restRole);
		roleRepo.save(adminRole);
		roleRepo.save(userRole);


		for (int i = 1; i <= 5; i++) {
			Project project = new Project();
			project.setName("Project " + i);
			projectRepo.save(project);
		}




		for (int i = 1; i <= 6; i++) {
			Person person = new Person();
			person.setName("Person " + i);
			person.setPost("Post " + i);
			person.setSurname("Surname " + i);
			person.setPassword(BCrypt.hashpw(String.valueOf(i), BCrypt.gensalt()));
			personRepo.save(person);
			Person person2  = personRepo.findByName(person.getName()).orElseThrow();

			if (i  == 1) {
				Role roleAdmin = roleRepo.findByName("admin").orElseThrow();
				Role roleUser = roleRepo.findByName("user").orElseThrow();
				roleAdmin.addPerson(person2);
				roleUser.addPerson(person2);
				roleRepo.save(roleAdmin);
				roleRepo.save(roleUser);
				person2.addRole(roleAdmin);
				person2.addRole(roleUser);
				personRepo.save(person2);
			}
			else if (i == 2 || i == 3)  {
				Role roleUser = roleRepo.findByName("user").orElseThrow();
				roleUser.addPerson(person2);
				roleRepo.save(roleUser);
				person2.addRole(roleUser);
				personRepo.save(person2);
			}
			else {
				Role roleRest = roleRepo.findByName("rest").orElseThrow();
				roleRest.addPerson(person2);
				roleRepo.save(roleRest);
				person2.addRole(roleRest);
				personRepo.save(person2);

			}


		}

		LocalDateTime createdAt = LocalDateTime.now();
		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			Project project = projectRepo.findById(ThreadLocalRandom.current().nextLong(1, 6)).get();

			timesheet.setProject(project);
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
			int countPersons = 4;
			for (int j = 0; j <= countPersons; j++) {
				Person person = personRepo.findById(ThreadLocalRandom.current().nextLong(1, 6)).get();
				person.addTimesheet(timesheet);
				timesheet.addPerson(person);
				personRepo.save(person);
				timesheetRepo.save(timesheet);

			}
		}
		System.out.println("----------------------------------------------------------");
//		timesheetRepo.findAll().forEach(u -> System.out.println(u.getPersonsList().size()));
		System.out.println("----------------------------------------------------------");
		roleRepo.findPersonById(1L).forEach(p -> System.out.println(p));
		roleRepo.findPersonById(0L).forEach(p -> System.out.println(p));
		roleRepo.findPersonById(2L).forEach(p -> System.out.println(p));
//		System.out.println(personRepo.findById(1L).get());

	}






}
