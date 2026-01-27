package app.posts.dto;

import java.util.List;

/**
 * DTO para respuestas de listados de posts
 */
public class PostListResponse {
    private List<PostSummaryDTO> posts;
    private Integer total;

    public PostListResponse() {}

    public PostListResponse(List<PostSummaryDTO> posts) {
        this.posts = posts;
        this.total = posts != null ? posts.size() : 0;
    }

    public List<PostSummaryDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostSummaryDTO> posts) {
        this.posts = posts;
        this.total = posts != null ? posts.size() : 0;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
