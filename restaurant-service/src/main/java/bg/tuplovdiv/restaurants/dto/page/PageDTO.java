package bg.tuplovdiv.restaurants.dto.page;

import java.util.Collection;

public class PageDTO<T> {
    private Collection<T> content;
    private PageInfo pageInfo;

    public Collection<T> getContent() {
        return content;
    }

    public PageDTO<T> setContent(Collection<T> content) {
        this.content = content;
        return this;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public PageDTO<T> setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }
}
