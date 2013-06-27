package pl.egalit.vocab.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.egalit.vocab.client.core.exceptions.UserExistsException;
import pl.egalit.vocab.server.dao.SchoolDao;
import pl.egalit.vocab.server.dao.UserDao;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.entity.UserEntity;

import com.googlecode.objectify.Key;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private SchoolDao schoolDao;

	private static final String PASSWORD_ADMIN = "rumun105";

	@RequestMapping(value = { "/add/user/{email}/{schoolId}/{password}/{passwordAdmin}" }, method = RequestMethod.GET)
	@ResponseBody
	public long addUser(@PathVariable String email,
			@PathVariable String password, @PathVariable String passwordAdmin,
			@PathVariable Long schoolId) throws UserExistsException {
		if (PASSWORD_ADMIN.equals(passwordAdmin)) {
			UserEntity user = new UserEntity();
			user.setEmail(email);
			user.setPassword(password);
			user.setSchool(Key.create(School.class, schoolId));
			user.setRole("ROLE_ADMIN");
			return userDao.save(user).getId();
		} else {
			throw new RuntimeException("Niepoprawne haslo.");
		}

	}

	@RequestMapping(value = { "/add/school/{name}/{passwordAdmin}" }, method = RequestMethod.GET)
	@ResponseBody
	public long addSchool(@PathVariable String name,
			@PathVariable String passwordAdmin) {
		if (PASSWORD_ADMIN.equals(passwordAdmin)) {
			School school = new School();
			school.setName(name);
			return schoolDao.save(school).getId();

		} else {
			throw new RuntimeException("Niepoprawne haslo.");
		}

	}
}
