package seedu.inbx0.model.history;

/**
 * An object that can be reverted to past states..
 * 
 * @param <T> The class of the item
 */
//@@author A0139481Y
public interface HistoryState<T> {
    T copyCurrentState();
}