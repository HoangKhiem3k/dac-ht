package jp.co.ht.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jp.co.ht.model.common.CommonResult;
import jp.co.ht.model.entity.Tag;
import jp.co.ht.model.request.tag.TagSuggestRequest;
import jp.co.ht.service.TagService;

@RestController
@RequestMapping("/api/tag")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    @PostMapping(value="/suggest")
    public CommonResult<List<Tag>> search(@RequestBody TagSuggestRequest tagSuggestRequest) {
    	List<Tag> listTag = tagService.suggest(tagSuggestRequest);
        return CommonResult.success(listTag);
    }
}
