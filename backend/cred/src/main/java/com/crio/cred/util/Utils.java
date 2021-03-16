package com.crio.cred.util;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Utils.
 *
 * @author nithesh.tarani
 * @author harikesh.pallantla
 */
public class Utils {

    /**
     * Utility method to map list of elements to a particular class.
     *
     * @param <S>         the generic type of source class
     * @param <T>         the generic type target class
     * @param modelMapper the model mapper
     * @param source      the source
     * @param targetClass the target class
     * @return the list
     */
    public static <S, T> List<T> mapList(ModelMapper modelMapper, List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
