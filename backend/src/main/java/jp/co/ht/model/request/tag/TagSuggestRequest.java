package jp.co.ht.model.request.tag;

import java.io.Serializable;
import lombok.Data;

@Data
public class TagSuggestRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tagName;
}
