import eiu.edu.vn.DataStore.DataStore;
import eiu.edu.vn.Services.UserService;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class UserServiceTests {
    UserService userService = new UserService();
    public UserServiceTests() throws NoSuchAlgorithmException {
    }

    @Test
    public void LoginTests() throws NoSuchAlgorithmException {
        this.userService.createUser("bao", "123");
        boolean test = this.userService.createUser("nam", "123");
        Assert.assertTrue(test);
    }

    @Test
    public void CreatUsers() throws NoSuchAlgorithmException {
        this.userService.createUser("bao", "123");
        boolean test = this.userService.createUser("nam", "123");
        Assert.assertTrue(test);
    }

}