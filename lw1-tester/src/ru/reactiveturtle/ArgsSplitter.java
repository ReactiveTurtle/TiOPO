package ru.reactiveturtle;

import java.util.ArrayList;
import java.util.List;

public final class ArgsSplitter {
    private ArgsSplitter() {
    }

    public static String[] parse(String argumentString) {
        argumentString = argumentString.trim();
        List<String> argumentList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean isInComma = false;
        for (int i = 0; i < argumentString.length(); i++) {
            char ch = argumentString.charAt(i);
            if (ch == '\"') {
                isInComma = !isInComma;
            } else if (!isInComma && ch == ' ') {
                if (argumentString.charAt(i - 1) != ' ') {
                    argumentList.add(builder.toString());
                    builder.delete(0, builder.length());
                }
            } else {
                builder.append(ch);
            }
        }
        if (builder.length() > 0) {
            argumentList.add(builder.toString());
        }
        return argumentList.toArray(new String[0]);
    }
}
