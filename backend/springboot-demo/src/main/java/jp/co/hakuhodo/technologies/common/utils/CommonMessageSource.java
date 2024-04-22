package jp.co.hakuhodo.technologies.common.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CommonMessageSource {
	@Autowired
	private MessageSource messageSource;

	public String getMessage(String code, @Nullable Object...args) throws NoSuchMessageException {
		return messageSource.getMessage(code, args, Locale.getDefault());
	}
}
