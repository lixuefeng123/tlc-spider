package cn.com.fero.tlc.spider.vo.article;

/**
 * Created by gizmo on 15/8/3.
 */
public class ArticleSource {
    private String id;
    private String created_at;
    private String last_artilce_at;
    private String last_spider_at;
    private String name;
    private String site_type;
    private String status;
    private String updated_at;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLast_artilce_at() {
        return last_artilce_at;
    }

    public void setLast_artilce_at(String last_artilce_at) {
        this.last_artilce_at = last_artilce_at;
    }

    public String getLast_spider_at() {
        return last_spider_at;
    }

    public void setLast_spider_at(String last_spider_at) {
        this.last_spider_at = last_spider_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite_type() {
        return site_type;
    }

    public void setSite_type(String site_type) {
        this.site_type = site_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleSource that = (ArticleSource) o;

        if (created_at != null ? !created_at.equals(that.created_at) : that.created_at != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (last_artilce_at != null ? !last_artilce_at.equals(that.last_artilce_at) : that.last_artilce_at != null)
            return false;
        if (last_spider_at != null ? !last_spider_at.equals(that.last_spider_at) : that.last_spider_at != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (site_type != null ? !site_type.equals(that.site_type) : that.site_type != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (updated_at != null ? !updated_at.equals(that.updated_at) : that.updated_at != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
        result = 31 * result + (last_artilce_at != null ? last_artilce_at.hashCode() : 0);
        result = 31 * result + (last_spider_at != null ? last_spider_at.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (site_type != null ? site_type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ArticleSource{");
        sb.append("id='").append(id).append('\'');
        sb.append(", created_at='").append(created_at).append('\'');
        sb.append(", last_artilce_at='").append(last_artilce_at).append('\'');
        sb.append(", last_spider_at='").append(last_spider_at).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", site_type='").append(site_type).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", updated_at='").append(updated_at).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
