import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String email;
    private int password;
    private UUID userID;
    private ArrayList<Subreddit> joinedSubreddits;
    private ArrayList<Subreddit> mySubreddits;
    private ArrayList<Post> allMyPosts;

    public User(String email, String password) {
        this.email = email;
        this.password = password.hashCode();
        this.userID = UUID.randomUUID();
        this.joinedSubreddits = new ArrayList<>();
        this.mySubreddits = new ArrayList<>();
        this.allMyPosts = new ArrayList<>();
        Reddit.addUser(this);
    }

    public void runUserMenu(){
        while (true) {
            System.out.println("Your Dashboard : ");
            System.out.println(" - Change Email                    - Change Password ");
            System.out.println(" - Create A Subreddit              - Join A Subreddit ");
            System.out.println(" - Your Subreddits                 - Timeline ");
            System.out.println(" - Log out ");
            System.out.println("Please choose a number : ");

            Scanner In = new Scanner(System.in);
            int enteredNumber = In.nextInt();

            switch (enteredNumber) {
                case 1:
                    Scanner in1 = new Scanner(System.in);
                    System.out.println("please enter new email address: ");
                    String newEmail =  in1.nextLine();
                    this.changeEmail(newEmail);
                    break;


                case 2:
                    Scanner in2 = new Scanner(System.in);
                    System.out.println("please enter new password: ");
                    String newPassword =  in2.nextLine();
                    this.changePassword(newPassword);
                    break;


                case 3:
                    Scanner in3 = new Scanner(System.in);
                    System.out.println("please enter name of your subreddit: ");
                    String subredditName =  in3.nextLine();
                    if (!Reddit.IsSubreddit(subredditName)){
                        this.createSubreddit(subredditName);
                    }
                    else System.out.println("A subreddit with this name has already existed!");
                    break;


                case 4:
                    break;


                case 5:
                    break;

                    
            }
            if (enteredNumber == ) break;
        }
    }


    public static boolean validateEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,3}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

    public void changeEmail(String newEmail){
        this.email = newEmail;
    }

    public void changePassword(String newPassword){
        this.password = newPassword.hashCode();
    }

    public boolean validatePassword(String enteredPassword) {
        if (enteredPassword.hashCode() == this.password){
            return true;
        }
        return false;
    }

    public void createSubreddit(String subredditName){
        Subreddit newSubreddit = new Subreddit(subredditName, this);
        this.mySubreddits.add(newSubreddit);
    }

    public String getEmail() {
        return email;
    }
}