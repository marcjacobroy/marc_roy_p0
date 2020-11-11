package FlashCard.service;

import FlashCard.pojos.User;
import org.apache.log4j.Logger;
import FlashCard.Dao.UserDao;
import FlashCard.Dao.UserDaoPostgres;

public class UserServiceFullStack implements UserService {
	
	private static Logger log = Logger.getRootLogger();

	UserDao userDao = new UserDaoPostgres();
	
	@Override
	public void createUser(User user) {
		log.trace("Calling createUser in UserServiceFullStack on " + user.toString());
		userDao.createUser(user);
	}

	@Override
	public String readUserCourses(int userId) {
		log.trace("Calling readUserCourses in UserServiceFullStack on " + userId);
		return userDao.readUserCourses(userId);
	}

	@Override
	public String readUserName(int userId) {
		log.trace("Calling readUserName in UserServiceFullStack on " + userId);
		return userDao.readUserName(userId);
	}

	@Override
	public void renameUser(int userId, String newName) {
		log.trace("Calling renameUser in UserServiceFullStack on " + userId + " " + newName);
		userDao.renameUser(userId, newName);
	}

	@Override
	public void assignUserToCourse(int userId, int courseId) {
		log.trace("Calling assignUserToCourse in UserServiceFullStack on " + userId + " " + courseId);
		userDao.assignUserToCourse(userId, courseId);
	}

	@Override
	public void deleteUser(int userId) {
		log.trace("Calling deleteUser in UserServiceFullStack on " + userId);
		userDao.deleteUser(userId);
	}

}
