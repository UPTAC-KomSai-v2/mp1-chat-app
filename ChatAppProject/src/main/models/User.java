package main.models;

public class User {
    private int user_id;
    private String username;
    private String email;
    private String password_hash;
    private String profile_picture;
    private String role;
    private String last_login;

    // REMOVE USER_ID AFTER DECISION ON HOW TO HANDLE IT (e.g., make auto-increment)
    public User(int user_id, String username, String email, String password_hash){
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
    }

    // Getters and Setters
    public int getUserID(){
        return user_id;
    }

    public void setUserID(){
        this.user_id = user_id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPasswordHash(){
        return password_hash;
    }

    public void setPasswordHash(String password_hash){
        this.password_hash= password_hash;
    }

    // profile_picture, role, and last_login are not initialized on registration
    public String getProfilePicture(){
        return profile_picture;
    }

    public void setProfilePicture(String profile_picture){
        this.profile_picture= profile_picture;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role= role;
    }

    public String getLastLogin(){
        return last_login;
    }

    public void setLastLogin(String last_login){
        this.last_login= last_login;
    }

}
