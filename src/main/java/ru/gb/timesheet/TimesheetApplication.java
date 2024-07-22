package ru.gb.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.timesheet.model.Person;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.PersonRepository;
import ru.gb.timesheet.repository.ProjectRepository;
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

		timesheetRepo.deleteAll();
		personRepo.deleteAll();
		projectRepo.deleteAll();
//		projectRepo.findAll().forEach(System.out::println);

		for (int i = 1; i <= 5; i++) {
			Project project = new Project();
			project.setName("Project #" + i);
			projectRepo.save(project);
		}

		for (int i = 1; i <= 6; i++) {
			Person person = new Person();
			person.setName("Person #" + i);
			person.setPost("Post #" + i);
			person.setSurname("Surname #" + i);
			personRepo.save(person);
		}


		LocalDateTime createdAt = LocalDateTime.now();
		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			Project project = projectRepo.findById(ThreadLocalRandom.current().nextLong(1, 6)).get();

			timesheet.setProject(project);
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
//			int countPersons = (int) (Math.random() * 5) + 1;
			int countPersons = 4;
			for (int j = 0; j <= countPersons; j++) {
				Person person = personRepo.findById(ThreadLocalRandom.current().nextLong(1, 6)).get();
				person.addTimesheet(timesheet);
				timesheet.addPerson(person);
				personRepo.save(person);
				timesheetRepo.save(timesheet);
//				timesheetRepo.wait(100);
//				System.out.println(personRepo.findById(person.getId()).get());
			}

//			System.out.println(timesheet);
		}
		System.out.println("----------------------------------------------------------");
		timesheetRepo.findAll().forEach(u -> System.out.println(u.getPersonsList().size()));
		System.out.println("----------------------------------------------------------");
	}






}
