package com.CG.CookGame.Services;

import com.CG.CookGame.Models.User;
import com.CG.CookGame.Models.UserDetails;
import com.CG.CookGame.Repositorys.UserDetailsRepository;
import com.CG.CookGame.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public  boolean isValidPassword(String password)
    {
        if (password == null) {
            return false;
        }


        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(password);

        return m.matches();
    }
    public boolean isValidEmail(String Ugmail) {
        if (Ugmail == null) {
            return false;
        }

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher m = pattern.matcher(Ugmail);

        return m.matches();
    }
    public boolean isEmailUnique(String Email){
        UserDetails userDetails=userDetailsRepository.findByEmail(Email);
        if(userDetails!=null){
            return false;
        }
        else return true;
    }
    public boolean isLoginValid(String login){
        if (login == null) {
            return false;
        }

        String regex="^[a-zA-Z0-9._-]{3,}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher m = pattern.matcher(login);

        return m.matches();
    }
    public boolean isLoginUnique(String login){
        User user=userRepository.findByLogin(login);
        if(user!=null){
            return false;
        }
        else return true;
    }
}
