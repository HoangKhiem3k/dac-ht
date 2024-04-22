package jp.co.hakuhodo.technologies.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.hakuhodo.technologies.common.annotation.validator.CommonValidator;

/**
 * ベースントローラー
 *
 */
@Component
public class BaseController {
    protected static final Logger log = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected CommonValidator validator;
}
