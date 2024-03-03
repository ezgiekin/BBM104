import java.util.ArrayList;

public class SearchBookAndMember {
    public static Member lookupMember(int memberID, ArrayList<Member> members) {
        for (Member member : members) {
            if (member.getMemberID() == memberID) {
                return member;
            }
        }
        return null;
    }

    public static Book lookupBook(int bookID, ArrayList<Book> books) {
        for (Book book : books) {
            if (book.getBookID() == bookID) {
                return book;
            }
        }
        return null;
    }
}
