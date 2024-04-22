package jp.co.hakuhodo.technologies.dto.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort.Order;
import lombok.Data;

@Data
public class BasePageInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private List<Order> orders;

    public void setOrders(List<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            // Sort property name
            String property = orders.get(i).getProperty();
            if (property.contains("_")) {
                continue;
            }
            StringBuilder strB = new StringBuilder(property);
            int count = 0;
            for (int j = 0; j < property.length(); j++) {
                // If uppercase character
                if (Character.isUpperCase(property.charAt(j))) {
                    // Insert "_" before uppercase character
                    strB.insert(j + count, "_");
                    count++;
                }
            }
            orders.get(i).withProperty(strB.toString().toUpperCase());
        }
        this.orders = orders;
    }
}
