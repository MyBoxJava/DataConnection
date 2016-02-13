package com.alberto.fernandez.mybox.dao.implementation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.alberto.fernandez.mybox.pojo.User;

public class TestUserDAO {

	private static UserDAO dao;
	private User user;
	private static String auxTime;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new UserDAO();
		auxTime = Long.toString(System.currentTimeMillis());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao = null;
		auxTime = null;
	}

	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setEmail(auxTime + "@" + auxTime + ".com");
		user.setName(auxTime);
		user.setPassword(auxTime);
		assertNotEquals(-1, dao.insert(user));
		user.setId(dao.logInUser(user.getEmail(), user.getPassword()).getId());

	}

	@After
	public void tearDown() throws Exception {
		assertTrue(dao.delete(user.getId()));
		user = null;
	}

	@Test
	public void testCheckEmail() {
		assertFalse(dao.checkEmail(user.getEmail()));
		assertTrue(dao.checkEmail(Long.toString(System.currentTimeMillis())));
	}

	@Test
	public void testSingInUser() {
		assertFalse(dao.singInUser(user.getName(), user.getPassword(),
				user.getEmail()));

		String auxTime2 = Long.toString(System.currentTimeMillis());
		User newUser = new User();
		newUser.setEmail(auxTime2 + "@" + auxTime2 + ".com");
		newUser.setName(auxTime2);
		newUser.setPassword(auxTime2);
		assertTrue(dao.singInUser(newUser.getName(), newUser.getPassword(),
				newUser.getEmail()));
		newUser = dao.logInUser(newUser.getEmail(), newUser.getPassword());
		assertTrue(dao.delete(newUser.getId()));
		newUser = null;

	}

	@Test
	public void testLogInUser() {
		User newUser = dao.logInUser(user.getEmail(), user.getPassword());

		assertEquals(user.getId(), newUser.getId());
		assertEquals(user.getEmail(), newUser.getEmail());

		newUser = null;
	}

	@Ignore
	public void testChangeUserPassword() {
		User newUser = dao.logInUser(user.getEmail(), user.getPassword());
		User newUser1 = dao.getById(user.getId());

		assertEquals(user, newUser);

		newUser = null;
		newUser1 = null;
	}

	@Test
	public void testGetById() {
		User newUser = dao.logInUser(user.getEmail(), user.getPassword());
		User newUser1 = dao.getById(user.getId());

		assertEquals(newUser, newUser1);

		newUser = null;
		newUser1 = null;
	}

	@Test
	public void testGetAll() {
		int number = dao.getAll().size();

		assertEquals(number, dao.getAll().size());

		String auxTime2 = Long.toString(System.currentTimeMillis());
		User newUser = new User();
		newUser.setEmail(auxTime2 + "@" + auxTime2 + ".com");
		newUser.setName(auxTime2);
		newUser.setPassword(auxTime2);
		assertTrue(dao.singInUser(newUser.getName(), newUser.getPassword(),
				newUser.getEmail()));
		newUser = dao.logInUser(newUser.getEmail(), newUser.getPassword());

		assertTrue(number < dao.getAll().size());

		assertTrue(dao.delete(newUser.getId()));

		assertEquals(number, dao.getAll().size());
	}

	@Test
	public void testDelete() {
		assertFalse(dao.singInUser(user.getName(), user.getPassword(),
				user.getEmail()));

		String auxTime2 = Long.toString(System.currentTimeMillis());
		User newUser = new User();
		newUser.setEmail(auxTime2 + "@" + auxTime2 + ".com");
		newUser.setName(auxTime2);
		newUser.setPassword(auxTime2);
		assertTrue(dao.singInUser(newUser.getName(), newUser.getPassword(),
				newUser.getEmail()));
		newUser = dao.logInUser(newUser.getEmail(), newUser.getPassword());
		assertTrue(dao.delete(newUser.getId()));
		newUser = null;
	}

	@Test
	public void testUpdate() {

		User newUser = dao.logInUser(user.getEmail(), user.getPassword());
		newUser.setName("a");
		newUser.setPassword(user.getPassword());
		assertTrue(dao.update(newUser));
		User newUser2 = dao.logInUser(user.getEmail(), user.getPassword());

		assertEquals(user.getId(), newUser.getId());
		assertNotEquals(user.getName(), newUser2.getEmail());

		newUser = null;
		newUser2 = null;

	}

	@Test
	public void testInsert() {
		/*
		 * assertFalse(dao.singInUser(user.getName(), user.getPassword(),
		 * user.getEmail()));
		 */

		String auxTime2 = Long.toString(System.currentTimeMillis());
		User newUser = new User();
		newUser.setEmail(auxTime2 + "@" + auxTime2 + ".com");
		newUser.setName(auxTime2);
		newUser.setPassword(auxTime2);
		newUser.setId(dao.insert(newUser));
		
		assertNotEquals(-1, newUser.getId());
		
		/*
		 * dao.singInUser(newUser.getName(), newUser.getPassword(),
		 * newUser.getEmail()));
		 */
		User newUser2 = dao.logInUser(newUser.getEmail(), newUser.getPassword());
		
		assertEquals(newUser.getName(),newUser2.getName());
		assertEquals(newUser.getEmail(),newUser2.getEmail());
		assertEquals(newUser.getId(),newUser2.getId());
		
		
		assertTrue(dao.delete(newUser.getId()));
		newUser = null;
		newUser2 = null;
	}

}
