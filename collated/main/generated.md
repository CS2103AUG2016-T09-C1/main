# generated
###### \java\seedu\inbx0\logic\parser\Parser.java
``` java
    private Command prepareDelete(final String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
```
###### \java\seedu\inbx0\logic\parser\Parser.java
``` java
    private Command prepareSelect(final String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<Integer> parseIndex(final String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(final String args) {
        final Matcher matcher1 = NORMAL_KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher2 = LOGIC_KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        boolean logicRelation = false;
        String[] keywords = null;
        List<String> keywordSet = new ArrayList<String>();
        if (!matcher1.matches() && !matcher2.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        if (matcher2.matches()) {
            logicRelation = true;
            String arguments = matcher2.group("arguments").trim();
            System.out.println(arguments);
            if (INVALID_LOGIC_SEARCH_ARGS1.matcher(arguments).matches()
                    || INVALID_LOGIC_SEARCH_ARGS2.matcher(arguments).matches()
                    || INVALID_LOGIC_SEARCH_ARGS3.matcher(arguments).matches()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
            }
            String singleChar;
            String stackChar = "";
            boolean foundLeftBracket;
            Stack<String> expressionStack = new Stack<String>();
            Stack<String> keywordStack = new Stack<String>();
            for (int index = 0; index < arguments.length(); index++) {
                singleChar = arguments.substring(index, index + 1);
                foundLeftBracket = false;
                if (singleChar.matches("[(]")) {
                    expressionStack.push(singleChar);
                    keywordSet.add(singleChar);
                } else if (singleChar.matches("[)]")) {
                    while (!expressionStack.empty()) {
                        if (expressionStack.peek().matches("[(]")) {
                            while (!keywordStack.empty()) {
                                stackChar = stackChar.concat(keywordStack.pop());
                            }
                            try {
                                if (!stackChar.matches("\\s+")) {
                                    keywordSet.add((convertKeywordsIntoDefinedFormat(stackChar)));
                                }
                            } catch (IllegalValueException ive) {
                                return new IncorrectCommand(ive.getMessage());
                            }
                            keywordSet.add(singleChar);
                            expressionStack.pop();
                            stackChar = "";
                            foundLeftBracket = true;
                            break;
                        } else {
                            keywordStack.push(expressionStack.pop());
                        }
                    }
                    if (!foundLeftBracket) {
                        return new IncorrectCommand(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_BRACKET_USAGE));
                    }
                } else if (singleChar.matches("[|&]")) {
                    while (!expressionStack.empty() && !expressionStack.peek().matches("[(]")) {
                        keywordStack.push(expressionStack.pop());
                    }
                    while (!keywordStack.empty()) {
                        stackChar = stackChar.concat(keywordStack.pop());
                    }
                    try {
                        if (!stackChar.matches("\\s+")) {
                            keywordSet.add((convertKeywordsIntoDefinedFormat(stackChar)));
                        }
                    } catch (IllegalValueException ive) {
                        return new IncorrectCommand(ive.getMessage());
                    }
                    keywordSet.add(singleChar);
                    stackChar = "";
                } else {
                    expressionStack.push(singleChar);
                }
            }
            while (!expressionStack.empty()) {
                keywordStack.push(expressionStack.pop());
            }
            while (!keywordStack.empty()) {
                stackChar = stackChar.concat(keywordStack.pop());
            }
            if (stackChar.contains("(")) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_BRACKET_USAGE));
            }
            try {
                keywordSet.add((convertKeywordsIntoDefinedFormat(stackChar)));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else {
            logicRelation = false;
            keywords = matcher1.group("keywords").split("\\s+");
            try {
                for (String keyword : keywords) {
                    keywordSet.add(convertKeywordsIntoDefinedFormat(keyword));
                }
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }
        try {
            return new FindCommand(logicRelation, keywordSet);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
    /*
     * // keywords delimited by whitespace final String[] keywords =
     * matcher.group("keywords").split("\\s+"); final int type;
     * if(keywords[0].contains(START_DATE)) { type = 1; keywords[0] =
     * keywords[0].replace(START_DATE, "").replace("'"," "); } else
     * if(keywords[0].contains(END_DATE)) { type = 2; keywords[0] =
     * keywords[0].replace(END_DATE, "").replace("'"," "); } else
     * if(keywords[0].contains(IMPORTANCE)) { type = 3; keywords[0] =
     * keywords[0].replace(IMPORTANCE, ""); } else if(keywords[0].contains(TAG))
     * { type = 4; keywords[0] = keywords[0].replace(TAG, ""); } else { type =
     * 0; } final Set<String> keywordSet = new
     * HashSet<>(Arrays.asList(keywords)); try { return new FindCommand(type,
     * keywordSet); } catch (IllegalValueException ive) { return new
     * IncorrectCommand(ive.getMessage()); } }
     */

    private String convertKeywordsIntoDefinedFormat(final String keyword) throws IllegalValueException {
        String convertedKeyword = null;
        if (keyword.contains(NAME)) {
            convertedKeyword = keyword.replace(NAME, " Name: ").trim().replace("'", " ");
        } else if (keyword.contains(START_DATE)) {
            convertedKeyword = keyword.replace(START_DATE, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("date", convertedKeyword);
            convertedKeyword = " Start Date: " + convertedKeyword;
        } else if (keyword.contains(START_TIME)) {
            convertedKeyword = keyword.replace(START_TIME, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("time", convertedKeyword);
            convertedKeyword = " Start Time: " + convertedKeyword;
        } else if (keyword.contains(END_DATE)) {
            convertedKeyword = keyword.replace(END_DATE, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("date", convertedKeyword);
            convertedKeyword = " End Date: " + convertedKeyword;
        } else if (keyword.contains(END_TIME)) {
            convertedKeyword = keyword.replace(END_TIME, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("time", convertedKeyword);
            convertedKeyword = " End Time: " + convertedKeyword;
        } else if (keyword.contains(IMPORTANCE)) {
            convertedKeyword = keyword.replace(IMPORTANCE, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("importance", convertedKeyword);
            convertedKeyword = " Importance: " + convertedKeyword;
        } else if (keyword.contains(TAG)) {
            convertedKeyword = keyword.replace(TAG, "").trim().replace("'", " ");
            convertedKeyword = " Tags: [" + convertedKeyword + "]";
        } else {
            convertedKeyword = keyword.trim().replace("'", " ");
        }
        return convertedKeyword;
    }

    private Command prepareShow(final String arguments) {
        String keyword = arguments.trim();
        if (keyword.equalsIgnoreCase("today") || keyword.equalsIgnoreCase("tod")) {
            return new ShowCommand("today");
        } else if (keyword.equalsIgnoreCase("Monday") || keyword.equalsIgnoreCase("Mon")) {
            return new ShowCommand("Monday");
        } else if (keyword.equalsIgnoreCase("Tuesday") || keyword.equalsIgnoreCase("Tue")) {
            return new ShowCommand("Tuesday");
        } else if (keyword.equalsIgnoreCase("Wednesday") || keyword.equalsIgnoreCase("Wed")) {
            return new ShowCommand("Wednesday");
        } else if (keyword.equalsIgnoreCase("Thursday") || keyword.equalsIgnoreCase("Thu")) {
            return new ShowCommand("Thursday");
        } else if (keyword.equalsIgnoreCase("Friday") || keyword.equalsIgnoreCase("Fri")) {
            return new ShowCommand("Friday");
        } else if (keyword.equalsIgnoreCase("Saturday") || keyword.equalsIgnoreCase("Sat")) {
            return new ShowCommand("Saturday");
        } else if (keyword.equalsIgnoreCase("Sunday") || keyword.equalsIgnoreCase("Sun")) {
            return new ShowCommand("Sunday");
        } else if (keyword.equalsIgnoreCase("Event") || keyword.equalsIgnoreCase("Eve")) {
            return new ShowCommand("Event");
        } else if (keyword.equalsIgnoreCase("Floating") || keyword.equalsIgnoreCase("Flo")) {
            return new ShowCommand("Floating");
        } else if (keyword.equalsIgnoreCase("Deadline") || keyword.equalsIgnoreCase("Dea")) {
            return new ShowCommand("Deadline");
        } else if (keyword.equalsIgnoreCase("Red")) {
            return new ShowCommand("Red");
        } else if (keyword.equalsIgnoreCase("Yellow") || keyword.equalsIgnoreCase("Yel")) {
            return new ShowCommand("Yellow");
        } else if (keyword.equalsIgnoreCase("Green") || keyword.equalsIgnoreCase("Gre")) {
            return new ShowCommand("Green");
        } else if (keyword.equalsIgnoreCase("None") || keyword.equalsIgnoreCase("Non")) {
            return new ShowCommand("None");
        } else if (keyword.equalsIgnoreCase("Complete") || keyword.equalsIgnoreCase("Com")) {
            return new ShowCommand("Complete");
        } else if (keyword.equalsIgnoreCase("Incomplete") || keyword.equalsIgnoreCase("Inc")) {
            return new ShowCommand("Incomplete");
        } else if (keyword.equalsIgnoreCase("Expire") || keyword.equalsIgnoreCase("Exp")) {
            return new ShowCommand("Expire");
        } else if (keyword.equalsIgnoreCase("Valid") || keyword.equalsIgnoreCase("Val")) {
            return new ShowCommand("Valid");
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    /**
     * Parses arguments in the context of the list task command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
```
###### \java\seedu\inbx0\logic\parser\Parser.java
``` java
    private Command prepareList(final String arguments) {
        if (arguments.length() == 0) {
            return new ListCommand();
        } else {
            try {
                return new ListCommand(arguments);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        }
    }

}
```
