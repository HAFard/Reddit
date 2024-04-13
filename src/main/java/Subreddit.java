import java.util.ArrayList;
import java.util.Scanner;

public class Subreddit {
    private String name;
    private int userNumber;
    private ArrayList<User> listOfAdmins;
    private ArrayList<User> listOfUsers;
    private ArrayList<Post> listOfPosts;

    public Subreddit(String name , User creator) {
        this.name = name;
        this.userNumber = 1;
        this.listOfAdmins = new ArrayList<>();
        this.listOfUsers = new ArrayList<>();
        this.listOfPosts = new ArrayList<>();

        //Set relations ...
        Reddit.addSubreddit(this);
        this.listOfAdmins.add(creator);
        this.listOfUsers.add(creator);
    }

    public void runAdminMenu(User admin){
        while (true) {
            System.out.println("Admin Menu : ");
            System.out.println("1 - Add Admin                     2 - View All Posts ");
            System.out.println("3 - Add A Post                    4 - Delete A Post ");
            System.out.println("5 - Exit Menu ");
            System.out.println("Please choose a number : ");

            Scanner In = new Scanner(System.in);
            int enteredNumber = In.nextInt();

            switch (enteredNumber) {
                case 1:
                    Scanner in1 = new Scanner(System.in);
                    System.out.println("please enter email address of new admin: ");
                    String newAdminEmail =  in1.nextLine();
                    if (Reddit.findUser(newAdminEmail) != null){
                        addAdmin(Reddit.findUser(newAdminEmail));
                        System.out.println("You added the admin successfully! ");
                    }
                    else System.out.println("The user is not found!");
                    break;

                case 2:
                    showAllPosts();
                    break;

                case 3:
                    Scanner in3 = new Scanner(System.in);
                    System.out.println("please enter title of the post: ");
                    String postTitle =  in3.nextLine();
                    System.out.println("please enter text of the post in a line: ");
                    String postText =  in3.nextLine();
                    this.createPost(postTitle,postText,admin);
                    break;

                case 4:
                    Scanner in4 = new Scanner(System.in);
                    System.out.println("please enter title of the post you wanna delete: ");
                    String deletedPostTitle =  in4.nextLine();
                    if (findPost(deletedPostTitle) != null){
                        findPost(deletedPostTitle).getCreator().getAllMyPosts().remove(findPost(deletedPostTitle));      //delete from user's list of posts
                        this.listOfPosts.remove(findPost(deletedPostTitle));                                             //delete from subreddit's list of posts
                    }
                    else System.out.println("There isn't any post with this title in this subreddit! ");
                    break;

                default:
                    break;
            }

            if (enteredNumber == 5) break;

        }
    }

    public void accessSubreddit(User joinedUser){
        Scanner in = new Scanner(System.in);
        System.out.println("please enter 'C' to create a new post or enter 'V' to view all the posts...or ");
        System.out.println("enter anything else to exit: ");
        String input =  in.nextLine();
        switch (input){
            case "C":
                Scanner in1 = new Scanner(System.in);
                System.out.println("please enter title of the post: ");
                String postTitle =  in1.nextLine();
                System.out.println("please enter text of the post in a line: ");
                String postText =  in1.nextLine();
                this.createPost(postTitle,postText,joinedUser);
                break;

            case "V":
                this.showAllPosts();
                break;

            default:
                break;
        }
    }
    public void showSubreddit(){
        System.out.println("Name : " + this.getName() + "              Members : " + this.getUserNumber());
        System.out.println("==============================================================================");
        System.out.println("List Of Posts :");
        System.out.println(" ");
        showAllPosts();
    }
    public void showAllPosts(){
        for (Post p : this.listOfPosts){                                                                                 //show all posts
            System.out.println("Title : " + p.getTitle() + "              Creator : " + p.getCreator());
            System.out.println("***************************************************************************");
        }
        Scanner in = new Scanner(System.in);                                                                             //get title of the post to show more detail
        System.out.println("please enter title of the post to show more detail or enter 'EX' to exit: ");
        String postTitle =  in.nextLine();
        if (postTitle.equals("EX") == false) {
            if (findPost(postTitle) != null) {
                findPost(postTitle).showPost();
            } else System.out.println("There isn't any post with this title in this subreddit! ");
        }
    }

    public void addUser(User joinedUser){
        this.listOfUsers.add(joinedUser);
        this.userNumber += 1;
    }

    public void addAdmin(User newAdmin){
        this.listOfAdmins.add(newAdmin);
        newAdmin.getMySubreddits().add(this);
        newAdmin.getJoinedSubreddits().add(this);
    }

    public void createPost(String title, String text ,User admin){
        Post newPost = new Post(title,text,admin,this);
        this.listOfPosts.add(newPost);
        admin.getAllMyPosts().add(newPost);
        for (User joinedUser : this.listOfUsers){
            joinedUser.getTimeline().add(0,newPost);
        }
        System.out.println("You added the post successfully! ");
    }
    public Post findPost(String postTitle){
        for (Post p : this.listOfPosts){
            if (p.getTitle().equals(postTitle)){
                return p;
            }
        }
        return null;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Subreddit:" +
                "Name ='" + name + "               " + " Number Of Members =" + userNumber ;
    }
}