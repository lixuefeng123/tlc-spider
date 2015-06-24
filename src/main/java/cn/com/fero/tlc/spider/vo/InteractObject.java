package cn.com.fero.tlc.spider.vo;

/**
 * Created by wanghongmeng on 2015/6/24.
 */
public class InteractObject {
    private int code;
    private String message;
    private FinanceValue value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FinanceValue getValue() {
        return value;
    }

    public void setValue(FinanceValue value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InteractObject that = (InteractObject) o;

        if (code != that.code) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InteractObject{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
