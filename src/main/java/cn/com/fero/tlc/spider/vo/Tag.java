package cn.com.fero.tlc.spider.vo;

/**
 * Created by wanghongmeng on 2015/7/30.
 */
public class Tag {
    private String name;
    private String tip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
        if (tip != null ? !tip.equals(tag.tip) : tag.tip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tip != null ? tip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("name='").append(name).append('\'');
        sb.append(", tip='").append(tip).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
