public class Post {
    private String title;
    private String text;
    private User creator;
    private Subreddit belongsTo;

    public Post(String title, String text, User creator, Subreddit belongsTo) {
        this.title = title;
        this.text = text;
        this.creator = creator;
        this.belongsTo = belongsTo;
    }
    public void showPost(){
        System.out.println("Title : " + this.title + "              Creator : " + this.creator );
        System.out.println("**************************************************************************");
        System.out.println(" ");
        System.out.println(this.text);
        System.out.println(" ");
        System.out.println("___________________________________________________________________");
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public User getCreator() {
        return creator;
    }

    public Subreddit getBelongsTo() {
        return belongsTo;
    }
}