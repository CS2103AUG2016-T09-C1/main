package seedu.inbx0.model.history;

import java.util.LinkedList;

public class HistoryList<T extends HistoryState<T>> {
    
    private static final int MAX_NUM_HISTORY_STORED = 10;
    private final int maxNumOfHistoryCommands;
    private final LinkedList<T> historyList;
    
    /**
     * Default constructor
     */
    public HistoryList() {
        this(MAX_NUM_HISTORY_STORED);
    }
    
    /**
     * Creates HistoryStack which can store up to
     * a number of history item states.
     * 
     * @param maxNumOfStates max number of history states
     */
    public HistoryList(int maxNumOfHistoryCommands) {
        assert maxNumOfHistoryCommands > 0;
        this.maxNumOfHistoryCommands = maxNumOfHistoryCommands;
        historyList = new LinkedList<T>();
    }
    
    /**
     * Push a history task to be saved as a history state.
     * 
     * @param item Object to be saved
     */
    public void pushState(T item) {
        assert item != null;
        
        if (historyList.size() >= maxNumOfHistoryCommands) {
            historyList.removeFirst();
        }
        historyList.addLast(item.copyCurrentState());
    }
    
    /**
     * Pops and returns the latest history state stored.
     * 
     * @return The HistoryState object
     * @throws EmptyHistoryException if the history queue is empty
     */
    public T popState() throws EmptyHistoryException {
        if (historyList.size() <= 0)
            throw new EmptyHistoryException();
        
        return historyList.removeLast();
    }
    
    @SuppressWarnings("serial")
    public static class EmptyHistoryException extends Exception {
        protected EmptyHistoryException() {
            super("There is noting to undo.");
        }
    }
}
