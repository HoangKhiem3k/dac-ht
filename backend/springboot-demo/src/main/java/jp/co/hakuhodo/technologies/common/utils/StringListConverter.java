package jp.co.hakuhodo.technologies.common.utils;

import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String[]> {
      @Override
      public String[] convertToDatabaseColumn(List<String> list) {
        return list != null ? list.toArray(new String[0]) : null;
      }

      @Override
      public List<String> convertToEntityAttribute(String[] strings) {
        return strings != null ? Arrays.asList(strings) : null;
      }
}
