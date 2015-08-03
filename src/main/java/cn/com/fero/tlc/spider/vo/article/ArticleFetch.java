package cn.com.fero.tlc.spider.vo.article;

/**
 * Created by wanghongmeng on 2015/8/3.
 */
public class ArticleFetch {
    private String article_source_id;
    private String title;
    private String url;
    private String article_cd;
    private String post_at;

    public String getArticle_source_id() {
        return article_source_id;
    }

    public void setArticle_source_id(String article_source_id) {
        this.article_source_id = article_source_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArticle_cd() {
        return article_cd;
    }

    public void setArticle_cd(String article_cd) {
        this.article_cd = article_cd;
    }

    public String getPost_at() {
        return post_at;
    }

    public void setPost_at(String post_at) {
        this.post_at = post_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleFetch that = (ArticleFetch) o;

        if (article_cd != null ? !article_cd.equals(that.article_cd) : that.article_cd != null) return false;
        if (article_source_id != null ? !article_source_id.equals(that.article_source_id) : that.article_source_id != null)
            return false;
        if (post_at != null ? !post_at.equals(that.post_at) : that.post_at != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = article_source_id != null ? article_source_id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (article_cd != null ? article_cd.hashCode() : 0);
        result = 31 * result + (post_at != null ? post_at.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ArticleFetch{");
        sb.append("article_source_id='").append(article_source_id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", article_cd='").append(article_cd).append('\'');
        sb.append(", post_at='").append(post_at).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
