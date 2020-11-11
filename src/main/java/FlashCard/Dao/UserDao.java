package FlashCard.Dao;
import FlashCard.pojos.User;

public interface UserDao {
	
	public void createUser(User user);
	
	public String readUserCourses(int userId);
	
	public String readUserName(int userId);
	
	public void renameUser(int userId, String newName);
	
	public void assignCourseToUser(int userId, int courseId);
	
	public void deleteUser(int userId);
	
	
}
