import java.util.ArrayList;

public class Subreddit {
    private String name;
    private ArrayList<User> listOfAdmins;
    private ArrayList<User> listOfUsers;
    private ArrayList<Post> listOfPosts;

    public Subreddit(String name , User creator) {
        this.name = name;
        this.listOfAdmins = new ArrayList<>();
        this.listOfUsers = new ArrayList<>();
        this.listOfPosts = new ArrayList<>();

        //Set relations ...
        Reddit.addSubreddit(this);
        this.listOfAdmins.add(creator);
        this.listOfUsers.add(creator);
    }

    public String getName() {
        return name;
    }
}