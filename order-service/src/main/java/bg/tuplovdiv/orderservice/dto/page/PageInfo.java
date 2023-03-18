package bg.tuplovdiv.orderservice.dto.page;

public class PageInfo {
    private Integer size;
    private Boolean hasNext;

    public Integer getSize() {
        return size;
    }

    public PageInfo setSize(Integer size) {
        this.size = size;
        return this;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public PageInfo setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
        return this;
    }
}
