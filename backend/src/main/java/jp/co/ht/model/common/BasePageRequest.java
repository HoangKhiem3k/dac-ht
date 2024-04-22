package jp.co.ht.model.common;

import java.io.Serializable;
import jp.co.ht.common.constant.CommonConst;
import lombok.Data;

@Data
public class BasePageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int pageNum;
    
    private int pageSize;
    
    private SortRequest sort;
    
    public void setSort(SortRequest sort) {
        // Sort property name
        String property = sort.getProperty();
        if (property.contains(CommonConst.UNDER_BAR)) {
            return;
        }
        StringBuilder strB = new StringBuilder(property);
        int count = 0;
        for (int j = 0; j < property.length(); j++) {
            // If uppercase character
            if (Character.isUpperCase(property.charAt(j))) {
                // Insert "_" before uppercase character
                strB.insert(j + count, CommonConst.UNDER_BAR);
                count++;
            }
        }
        sort.setProperty(strB.toString().toLowerCase());
        this.sort = sort;
    }
    
    @Data
    public static class SortRequest implements Serializable {
    	
    	private static final long serialVersionUID = 1L;
    	
    	private String property;
    	
    	private Boolean asc;
    }
}
