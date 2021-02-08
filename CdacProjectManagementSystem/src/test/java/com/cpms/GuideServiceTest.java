package com.cpms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cpms.pojos.Course;
import com.cpms.pojos.Guide;
import com.cpms.pojos.Project;
import com.cpms.pojos.Session;
import com.cpms.repository.GuideRepository;
import com.cpms.repository.ProjectRepository;
import com.cpms.repository.SessionRepository;
import com.cpms.services.IGuideService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuideServiceTest {

	@Autowired
	IGuideService guideService;

	@Autowired
	@MockBean
	GuideRepository guideRepository;

	@Autowired
	@MockBean
	ProjectRepository projectRepository;

	@Autowired
	@MockBean
	SessionRepository sessionRepository;

	@Test
	public void getProjectsAssignedToGuideTest() {
		List<Project> projects = new ArrayList<>();
		projects.add(new Project(1));
		projects.add(new Project(2));
		projects.add(new Project(3));

		Guide guide = new Guide(1);

		when(projectRepository.findByGuide(guide)).thenReturn(projects);

		assertEquals(3, guideService.getProjectsAssignedToGuide(1).size());

	}

	@Test
	public void getGuideListTest() {
		List<Guide> guides = new ArrayList<>();
		guides.add(new Guide(1));
		guides.add(new Guide(2));
		guides.add(new Guide(3));

		when(guideRepository.findByCourse(Course.DAC)).thenReturn(guides);

		assertEquals(3, guideService.getGuideList(Course.DAC).size());
	}

	@Test
	public void registerGuideTest() {
		Guide guide = new Guide(11);
		when(guideRepository.save(guide)).thenReturn(guide);

		assertEquals(guide, guideService.registerGuide(guide));
	}

	@Test
	public void getSessionListByProjectTest() {
		Project project = new Project(1);
		List<Session> sessions = new ArrayList<>();
		sessions.add(new Session(1, project));
		sessions.add(new Session(2, project));
		sessions.add(new Session(3, project));

		when(sessionRepository.findByProject(project)).thenReturn(sessions);
		
		System.out.println("size = " + guideService.getSessionListByProject(Integer.valueOf(1)).size());
		assertEquals(3, guideService.getSessionListByProject(Integer.valueOf(1)).size());
	}
}
