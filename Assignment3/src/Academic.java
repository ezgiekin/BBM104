public class Academic extends Member {

    private final int maxBook = 4;

    private final long timeLimit = 2 * 7 * 24 * 60 * 60 * 1000;

    public Academic(int memberID) {
        super(memberID);
    }

    public int getMaxBookNum() {
        return maxBook;
    }

    public long getTimeLimit() {
        return timeLimit;
    }
}
