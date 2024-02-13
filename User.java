package Main;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;
    private boolean firstPurchase;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.firstPurchase=true;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstPurchase(){
        return firstPurchase;
    }

    public void setFirstPurchase(boolean firstPurchase){
        this.firstPurchase=firstPurchase;
    }

}
