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
    private ArrayList<Post> timeline;

    public User(String email, String password) {
        this.email = email;
        this.password = password.hashCode();
        this.userID = UUID.randomUUID();
        this.joinedSubreddits = new ArrayList<>();
        this.mySubreddits = new ArrayList<>();
        this.allMyPosts = new ArrayList<>();
        this.timeline = new ArrayList<>();
        Reddit.addUser(this);
    }

    public void runUserMenu(){
        while (true) {
            System.out.println("Your Dashboard : ");
            System.out.println("1 - Change Email                         2 - Change Password ");
            System.out.println("3 - Create A Subreddit                   4 - Join A Subreddit ");
            System.out.println("5 - Access Your Own Subreddits           6 - Access Subreddits You Have Joined ");
            System.out.println("7 - View Timeline                        8 - Search ");
            System.out.println("9 - Log out ");
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
                    System.out.println("please enter name of your new subreddit: ");
                    String newSubredditName =  in3.nextLine();
                    if (Reddit.findSubreddit(newSubredditName) == null){
                        this.createSubreddit(newSubredditName);
                        System.out.println("You created the subreddit successfully!");
                    }
                    else System.out.println("A subreddit with this name has already existed!");
                    break;


                case 4:
                    Scanner in4 = new Scanner(System.in);
                    System.out.println("please enter name of subreddit you wanna join: ");
                    String subredditName =  in4.nextLine();
                    if (Reddit.findSubreddit(subredditName) != null){
                        Reddit.findSubreddit(subredditName).addUser(this);
                        this.joinedSubreddits.add(Reddit.findSubreddit(subredditName));
                        System.out.println("You joined the subreddit successfully!");
                    }
                    else System.out.println("There is not any subreddit with this name!");
                    break;


                case 5:
                    System.out.println("These are subreddits that you are admin of them :");
                    System.out.println("_____________________________________________________");
                    showListOfMySubreddits();
                    System.out.println("please enter name of subreddit you wanna manage or enter 'EX' if you wanna exit: ");
                    Scanner in5 = new Scanner(System.in);
                    String nameOfSubreddit =  in5.nextLine();
                    if (nameOfSubreddit.equals("EX") == false) {
                        if (this.findMySubreddit(nameOfSubreddit) != null) {
                            this.findMySubreddit(nameOfSubreddit).runAdminMenu(this);
                        }
                        else System.out.println("You are not admin of this subreddit!");
                    }
                    break;


                case 6:
                    System.out.println("These are subreddits you have joined :");
                    System.out.println("_____________________________________________________");
                    showListOfJoinedSubreddits();
                    System.out.println("please enter name of subreddit you wanna access or enter 'EX' if you wanna exit: ");
                    Scanner in6 = new Scanner(System.in);
                    String enteredSubreddit =  in6.nextLine();
                    if (enteredSubreddit.equals("EX") == false) {
                        if (this.findJoinedSubreddit(enteredSubreddit) != null) {
                            this.findJoinedSubreddit(enteredSubreddit).accessSubreddit(this);
                        }
                        else System.out.println("You are not joined to this subreddit!");
                    }
                    break;


                case 7:
                    this.showTimeline();
                    break;


                case 8:
                    Scanner in8 = new Scanner(System.in);
                    System.out.println("please enter user email address or subreddit name(add ‘r/’ to the subreddit name and ‘u/’ to the user email: ");
                    String searchedString =  in8.nextLine();
                    if (searchedString.startsWith("u/")){
                       if (Reddit.findUser(searchedString.substring(2)) != null){
                           Reddit.findUser(searchedString.substring(2)).showProfile();
                        }
                       else System.out.println("There isn't any user with this email address!");
                    }
                    if (searchedString.startsWith("r/")){
                        if (Reddit.findSubreddit(searchedString.substring(2)) != null){
                            Reddit.findSubreddit(searchedString.substring(2)).showSubreddit();
                        }
                        else System.out.println("There isn't any subreddit with this name!");
                    }
                    break;

                default:
                    break;

            }
            if (enteredNumber == 9) break;
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
        this.joinedSubreddits.add(newSubreddit);
    }

    public void showListOfMySubreddits(){
        for (Subreddit sub : this.mySubreddits){
            System.out.println("Name : " + sub.getName() + "              Members : " + sub.getUserNumber());
            System.out.println("          *********************************************            ");
        }
    }

    public void showListOfJoinedSubreddits(){
        for (Subreddit sub : this.joinedSubreddits){
            System.out.println("Name : " + sub.getName() + "              Members : " + sub.getUserNumber());
            System.out.println("          *********************************************            ");
        }
    }
    public void showAllMyPosts(){
        for (Post p : this.allMyPosts){
            System.out.println("Title : " + p.getTitle());
            System.out.println("Related Subreddit : " + p.getBelongsTo());
            System.out.println("___________________________________________________ " );
        }
    }
    public void showTimeline(){
        for (Post p : this.timeline){
            p.showPost();
        }
    }
    public void showProfile(){
        System.out.println("User Email : " + this.getEmail());
        System.out.println("**********************************************************");
        System.out.println("List of subreddits:");
        System.out.println(" ");
        showListOfJoinedSubreddits();
        System.out.println(" ");
        System.out.println("List of posts:");
        System.out.println(" ");
        showAllMyPosts();
    }
    public Subreddit findMySubreddit(String subredditName){
        for (Subreddit sub : this.mySubreddits){
            if (sub.getName().equals(subredditName)){
                return sub;
            }
        }
        return null;
    }

    public Subreddit findJoinedSubreddit(String subredditName){
        for (Subreddit sub : this.joinedSubreddits){
            if (sub.getName().equals(subredditName)){
                return sub;
            }
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Subreddit> getJoinedSubreddits() {
        return joinedSubreddits;
    }

    public ArrayList<Subreddit> getMySubreddits() {
        return mySubreddits;
    }

    public ArrayList<Post> getAllMyPosts() {
        return allMyPosts;
    }

    public ArrayList<Post> getTimeline() {
        return timeline;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}