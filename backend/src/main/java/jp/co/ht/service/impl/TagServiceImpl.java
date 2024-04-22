package jp.co.ht.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ht.common.constant.CommonConst;
import jp.co.ht.common.utils.StringUtil;
import jp.co.ht.model.entity.Tag;
import jp.co.ht.model.request.tag.TagSuggestRequest;
import jp.co.ht.repository.TagRepository;
import jp.co.ht.service.TagService;

@Service
public class TagServiceImpl extends BaseServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;

	@Override
	public List<Tag> suggest(TagSuggestRequest tagSuggestRequest) {
		String searchTagParam =  StringUtil.getFullSearchLikeParam(tagSuggestRequest.getTagName());
		return tagRepository.findByTagNameAndLimit(searchTagParam, CommonConst.VALID_DATA_FLAG, CommonConst.MAX_LIMIT_SUGGEST);
	}
}
