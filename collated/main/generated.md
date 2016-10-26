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
    /**
     * Parses arguments in the context of the save as command.
     * 
     * @param full command args string
     * @return the prepared command
     */
```
