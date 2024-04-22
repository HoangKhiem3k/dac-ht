package jp.co.ht.model.common;

import java.util.List;
import org.springframework.data.domain.Page;

public class CommonPage<T> {
	
    private int pageNum;
    
    private int pageSize;
    
    private long total;
    
    private List<T> list;

    public static <T> CommonPage<T> restPage(Page<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(pageResult.getNumber());
        result.setPageSize(pageResult.getSize());
        result.setTotal(pageResult.getTotalElements());
        result.setList(pageResult.getContent());
        return result;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
