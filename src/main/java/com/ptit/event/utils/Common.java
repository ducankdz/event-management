package com.ptit.event.utils;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class Common {
    private static final ModelMapper modelMapperStrict = new ModelMapper();
    private static final ModelMapper modelMapperStrictNonNull = new ModelMapper();

    static {
        // Chỉ map khi cùng tên & cùng kiểu dữ liệu
        modelMapperStrict.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapperStrictNonNull.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static <T> T mapAllProperties(Object source, Class<T> targetClass) {
        return modelMapperStrict.map(source, targetClass);
    }

    public static <T> T mapNonNullProperties(Object source, Class<T> targetClass) {
        return modelMapperStrict.map(source, targetClass);
    }

    public static void mapNonNullProperties(Object source, Object target) {
        modelMapperStrict.map(source, target);
    }

}
