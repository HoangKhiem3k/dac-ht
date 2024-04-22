package jp.co.ht.service;

import java.util.List;
import jp.co.ht.model.entity.Tag;
import jp.co.ht.model.request.tag.TagSuggestRequest;

public interface TagService {

	List<Tag> suggest(TagSuggestRequest tagSuggestRequest);
}
