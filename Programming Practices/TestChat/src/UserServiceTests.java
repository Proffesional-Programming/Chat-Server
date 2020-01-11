import eiu.edu.vn.DataStore.DataStore;
import eiu.edu.vn.Services.UserService;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class UserServiceTests {
    DataStore store = new DataStore();
    UserService userService;

    public UserServiceTests() {
        this.userService = new UserService(this.store);
    }

    @Test
    public void LoginTests() throws NoSuchAlgorithmException {
        this.userService.createUser("bao", "123");
        boolean test = this.userService.createUser("nam", "123");
        Assert.assertTrue(test);
    }
}