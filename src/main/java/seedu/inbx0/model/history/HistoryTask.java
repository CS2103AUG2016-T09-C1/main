package seedu.inbx0.model.history;

/**
 * An object that can be reverted to past states..
 * 
 * @param <T> The class of the item
 */
public interface HistoryTask<T> {
    T copyCurrentList();
}

