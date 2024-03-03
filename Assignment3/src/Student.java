public class Student extends Member {

    private final int maxBook = 2;

    private final long timeLimit = 7 * 24 * 60 * 60 * 1000;

    public Student(int memberID) {
        super(memberID);
    }

    public int getMaxBookNum() {
        return maxBook;
    }

    public long getTimeLimit() {
        return timeLimit;
    }
}
