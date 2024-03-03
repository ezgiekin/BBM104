import java.util.ArrayList;

public abstract class Member extends LibrarySystem {
    private int memberID;

    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public Member(int memberID) {
        this.memberID = memberID;
    }


    public int getMemberID() {
        return memberID;
    }

    public abstract int getMaxBookNum();

    public abstract long getTimeLimit();

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBooks(Book book) {
        borrowedBooks.add(book);
    }
}
