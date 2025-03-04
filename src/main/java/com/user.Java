public class User{
    String Username;
    String Password;
    Boolean isAdmin;

    public User(String TheUsername, String ThePassword){
        Username = TheUsername;
        Password = ThePassword;
        isAdmin = false;
    }

    public String getUsername(){
        return Username;
    }

    public void setUsername(String newU){
        Username = newU;
    }

    public String getPassword(){
        return Password;
    }

    public void setPassword(String newP){
        Password = newP;
    }

    public void makeAdmin(){
        isAdmin = true;
    }

    public void removeAdmin(){
        isAdmin = false;
    }
}