package com.CG.CookGame.bean;

import com.CG.CookGame.Models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class HttpSession {
    @Getter
    @Setter
    private User user;
    public boolean isPresent() {
        return user != null;
    }
    public void clear() {
        clearUser();
    }
    public void clearUser() {
        user = null;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}

