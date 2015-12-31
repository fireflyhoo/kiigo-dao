package net.kiigo.core.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页容器
 * 
 * @author Coollf
 * 
 */
public class PageCollection<T> implements java.io.Serializable {

    /**
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).
     */
    private static final long serialVersionUID = 7537874574064808177L;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final static PageCollection EMPTY = new PageCollection(new ArrayList(), 0, 0);

    private List<T> datas;

    /**
     * 当前页记录便宜量
     */
    private long offset;

    /**
     * 每一页记录数大小
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private long totalSize;

    public long getTotalSize() {
        return totalSize;
    }

    public PageCollection<T> setTotalSize(long totalSize) {
        this.totalSize = totalSize;
        return this;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PageCollection(List<T> datas, long offset, int pageSize) {
        this.datas = datas;
        this.offset = offset;
        this.pageSize = pageSize;
    }

    /**
     * 获取总页数
     * 
     * @return
     */
    public int getPageCount() {
        if (totalSize != 0 && pageSize != 0) {
            return (int) (totalSize / pageSize + (totalSize % pageSize == 0 ? 0 : 1));
        }
        return 0;
    }

    public int getCurrentPage() {
        if (offset != 0 && pageSize != 0) {
            return (int) (offset/pageSize) + 1;
        }
        return 1;
    }

    public int getDatasCount() {
        if (this.datas == null) {
            return 0;
        }
        return this.datas.size();
    }
}
