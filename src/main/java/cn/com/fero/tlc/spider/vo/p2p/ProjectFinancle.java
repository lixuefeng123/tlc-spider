package cn.com.fero.tlc.spider.vo.p2p;

/**
 * Created by wanghongmeng on 2015/6/25.
 */
public class ProjectFinancle {
    private String id;
    private String projectId;
    private String financleAmount;
    private String createTime;
    private String updateTime;

    public ProjectFinancle() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getFinancleAmount() {
        return financleAmount;
    }

    public void setFinancleAmount(String financleAmount) {
        this.financleAmount = financleAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectFinancle that = (ProjectFinancle) o;

        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (financleAmount != null ? !financleAmount.equals(that.financleAmount) : that.financleAmount != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (financleAmount != null ? financleAmount.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProjectFinancle{");
        sb.append("id='").append(id).append('\'');
        sb.append(", projectId='").append(projectId).append('\'');
        sb.append(", financleAmount='").append(financleAmount).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
