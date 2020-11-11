package FlashCard.service;

import FlashCard.pojos.User;
import FlashCard.Dao.UserDao;
import FlashCard.Dao.UserDaoPostgres;

public class UserServiceFullStack implements UserService {

	UserDao userDao = new UserDaoPostgres();
	@Override
	public void createUser(User user) {
		userDao.createUser(user);
	}

	@Override
	public String readUserCourses(int userId) {
		return userDao.readUserCourses(userId);
	}

	@Override
	public String readUserName(int userId) {
		return userDao.readUserName(userId);
	}

	@Override
	public void renameUser(int userId, String newName) {
		userDao.renameUser(userId, newName);
	}

	@Override
	public void assignCourseToUser(int userId, int courseId) {
		userDao.assignCourseToUser(userId, courseId);
	}

	@Override
	public void deleteUser(int userId) {
		userDao.deleteUser(userId);
	}

}
