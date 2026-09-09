package model;

public class Post {
    private int postId;
    private int userId;
    private String content;

    public Post(int postId, int userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

    public int getPostId() { return postId; }
    public int getUserId() { return userId; }
    public String getContent() { return content; }
}