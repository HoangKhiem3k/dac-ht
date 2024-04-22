package jp.co.ht.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jp.co.ht.common.annotation.validator.CommonValidator;

@Component
public class BaseController {
	
    protected static final Logger log = LoggerFactory.getLogger(BaseController.class);
    
    @Autowired
    protected HttpServletRequest request;
    
    @Autowired
    protected CommonValidator validator;
}
