package eiu.edu.vn.Services;

import eiu.edu.vn.DataStore.DataStore;
import eiu.edu.vn.Models.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UserService {
    private DataStore store;

    public UserService(DataStore store) {
        this.store = store;
    }


    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHashPassword(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public User Login(String userName, String password) throws NoSuchAlgorithmException {
        var hashdedPassword = toHashPassword(getSHA(password));
        User findUser = store.User.stream().filter(user -> userName.equals(user.getUserName())&& hashdedPassword.equals(user.getPassword()))
                .findAny()
                .orElse(null);
        return findUser;
    }

    public boolean createUser (String userName, String password ) throws NoSuchAlgorithmException {
        String hash = toHashPassword(getSHA(password));
        boolean result=false;
        User user =new User(UUID.randomUUID(),userName,hash);
        if(store.User.stream().filter(x->x.getUserName().equals(userName)).findAny().orElse(null)==null){
             result= store.User.add(user);
        }
        return result;
    }
}
