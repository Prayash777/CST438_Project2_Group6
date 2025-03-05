public class User{
    private String Username;
    private String Password;
    private Int Id;
    private Boolean isAdmin;
    private String Email;

    public User(String TheUsername, String ThePassword, String TheEmail){
        Username = TheUsername;
        Password = ThePassword;
        Email = TheEmail;
        Id = 0;
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

    public String getEmail(){
        return Email;
    }

    public void setEmail(String newE){
        Email = newE;
    }

    public void makeAdmin(){
        isAdmin = true;
    }

    public void removeAdmin(){
        isAdmin = false;
    }
}