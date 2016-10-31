package seedu.inbx0.model.history;

//@@author A0139481Y
/**
 * An object that can be reverted to past states.
 * 
 * @param <T> The class of the item
 */
public interface HistoryState<T> {
    T copyCurrentState();
}