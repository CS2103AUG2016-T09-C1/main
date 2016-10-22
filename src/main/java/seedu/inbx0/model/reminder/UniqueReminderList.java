package seedu.inbx0.model.reminder;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inbx0.commons.exceptions.DuplicateDataException;
import seedu.inbx0.commons.util.CollectionUtil;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;
import seedu.inbx0.model.tag.UniqueTagList.DuplicateTagException;
import seedu.inbx0.model.task.UniqueTaskList.DuplicateTaskException;
/**
 * A list of reminders that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see ReminderTask#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueReminderList implements Iterable<ReminderTask> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateReminderException extends DuplicateDataException {
        protected DuplicateReminderException() {
            super("Operation would result in duplicate reminders");
        }
    }
    
    /**
     * Signals that an operation targeting a specified reminder in the list would fail because
     * there is no such matching reminder in the list.
     */
    public static class ReminderNotFoundException extends Exception {}
    
    private final ObservableList<ReminderTask> internalList = FXCollections.observableArrayList();
    
    /**
     * Constructs empty ReminderList.
     */
    public UniqueReminderList() {}
    
    /**
     * java collections constructor, enforces no null or duplicate elements.
     */
    public UniqueReminderList(Collection<ReminderTask> reminders) throws DuplicateReminderException {
        CollectionUtil.assertNoNullElements(reminders);
        if (!CollectionUtil.elementsAreUnique(reminders)) {
            throw new DuplicateReminderException();
        }
        internalList.addAll(reminders);
    }
    /**
     * Varargs/array constructor, enforces no nulls or duplicates.
     */
    public UniqueReminderList(ReminderTask... reminders) throws DuplicateReminderException {
        assert !CollectionUtil.isAnyNull((Object[]) reminders);
        final List<ReminderTask> initialReminders = Arrays.asList(reminders);
        if (!CollectionUtil.elementsAreUnique(initialReminders)) {
            throw new DuplicateReminderException();
        }
        internalList.addAll(initialReminders);
    }
    
    /**
     * Copy constructor, insulates from changes in source.
     */
    public UniqueReminderList(UniqueReminderList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }
    
    /**
     * java set constructor, enforces no nulls.
     */
    public UniqueReminderList(Set<ReminderTask> reminders) {
        CollectionUtil.assertNoNullElements(reminders);
        internalList.addAll(reminders);
    }
    /**
     * All reminders in this list as a Set. This set is mutable and change-insulated against the internal list.
     */
    public Set<ReminderTask> toSet() {
        return new HashSet<>(internalList);
    }
    
    
    /**
     * Adds a Reminder to the list.
     *
     * @throws DuplicateReminderException if the reminder to add is a duplicate of an existing reminder in the list.
     */
    public void add(ReminderTask toAdd) throws DuplicateReminderException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateReminderException();
        }
        internalList.add(toAdd);
    }
    
    
    /**
     * Adds every reminder from the argument list that does not yet exist in this list.
     */
    public void mergeFrom(UniqueReminderList reminders) {
        final Set<ReminderTask> alreadyInside = this.toSet();
        for (ReminderTask reminder : reminders) {
            if (!alreadyInside.contains(reminder)) {
                internalList.add(reminder);
            }
        }
    }
    
    /**
     * Returns true if the list contains an equivalent reminder as the given argument.
     */
    public boolean contains(ReminderTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }
    
    /**
     * Removes the equivalent Reminder from the list.
     *
     * @throws ReminderNotFoundException if no such reminder could be found in the list.
     */
    public boolean remove(ReminderTask toRemove) throws ReminderNotFoundException {
        assert toRemove != null;
        final boolean reminderFoundAndDeleted = internalList.remove(toRemove);
        if (!reminderFoundAndDeleted) {
            throw new ReminderNotFoundException();
        }
        return reminderFoundAndDeleted;
    }
    
    public ObservableList<ReminderTask> getInternalList() {
        return internalList;
    }
    
    @Override
    public Iterator<ReminderTask> iterator() {
        return internalList.iterator();
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueReminderList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueReminderList) other).internalList));
    }
    
    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
