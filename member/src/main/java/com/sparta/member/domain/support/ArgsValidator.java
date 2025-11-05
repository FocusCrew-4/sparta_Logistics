package com.sparta.member.domain.support;

public final class ArgsValidator {

    private ArgsValidator() {}

    public static void requireAllNonNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                throw new IllegalArgumentException("argument must not be null");
            }
        }
    }
}
