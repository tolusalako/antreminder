package net.csthings.antreminder.service.reminder;

public class ResultDto<T> extends EmptyResultDto {

    private T item;

    public ResultDto() {
    }

    public ResultDto(T t, String status) {
        super();
        this.item = t;
        this.setStatus(status);
    }

    public ResultDto(T t, String status, String error, String msg) {
        super(status, error, msg);
        this.item = t;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((item == null) ? 0 : item.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResultDto other = (ResultDto) obj;
        if (item == null) {
            if (other.item != null)
                return false;
        }
        else if (!item.equals(other.item))
            return false;
        return true;
    }
}
