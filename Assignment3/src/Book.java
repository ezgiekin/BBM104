import java.text.SimpleDateFormat;
import java.util.Date;

public class Book extends LibrarySystem {

    private int bookID;

    private String borrowSituation = "";

    private String readInLibrarySituation = "";

    private Date dueDate;

    private Date borrowDate;

    private int borrowedTo;

    private int isExtended = 0;


    public Book(int bookID) {
        this.bookID = bookID;
    }


    public int getBookID() {
        return bookID;
    }

    public String getBorrowSituation() {
        return borrowSituation;
    }

    public void setBorrowSituation(String borrowSituation) {
        this.borrowSituation = borrowSituation;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


    public int getIsExtended() {
        return isExtended;
    }

    public void setIsExtended(int isExtended) {
        this.isExtended = isExtended;
    }

    public String getReadInLibrarySituation() {
        return readInLibrarySituation;
    }

    public void setReadInLibrarySituation(String readInLibrarySituation) {
        this.readInLibrarySituation = readInLibrarySituation;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getStrBorrowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(borrowDate);
    }

    public int getBorrowedTo() {
        return borrowedTo;
    }

    public void setBorrowedTo(int borrowedTo) {
        this.borrowedTo = borrowedTo;
    }
}
