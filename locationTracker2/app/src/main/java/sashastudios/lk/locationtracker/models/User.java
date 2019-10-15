package sashastudios.lk.locationtracker.models;

public class User {
    public int UserId;
    public String UserName;
    public int UserType;
    public String PassWord;
    public String Email;
    public String FirstName;
    public String LastName;
    public String DOB;

    public User(int userId, String firstName, String lastName){
        this.UserId = userId;
        this.FirstName = firstName;
        this.LastName = lastName;
    }
    public User(int userId, String userName, int userType, String param){
        this.UserId = userId;
        this.UserName = userName;
        this.UserType = userType;
    }

    public User (int userId, String userName,
                 int userType, String passWord, String email, String firstName, String lastName, String dob ){
        this.UserId = userId;
        this.UserName = userName;
        this.UserType = userType;
        this.PassWord = passWord;
        this.Email = email;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.DOB = dob;
    }

}
