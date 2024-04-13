import java.util.ArrayList;

public class Reddit {
    private static ArrayList<Subreddit> allSubreddits = new ArrayList<>();
    private static ArrayList<User> allUsers = new ArrayList<>();



    public static User userLogin(String userEmail,String userPassword){
        for (User user : allUsers){
            if (user.getEmail().equals(userEmail)){
                if(user.validatePassword(userPassword)){
                    return user;
                }
            }
        }
        System.out.println("Email or password is not correct!");
        return null;
    }

    public static void addUser(User newUser){
        allUsers.add(newUser);
    }

    public static void addSubreddit(Subreddit newSubreddit){
        allSubreddits.add(newSubreddit);
    }

    public static User findUser(String email){
        for (User user : allUsers){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    public static Subreddit findSubreddit(String subredditName){
        for (Subreddit sub : allSubreddits){
            if (sub.getName().equals(subredditName)){
                return sub;
            }
        }
        return null;
    }
}