import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LibrarySystem {

    private ArrayList<Member> members = new ArrayList<>();

    private ArrayList<Book> books = new ArrayList<>();

    private void addBook(String bookType, String fileName) throws IOException, LibrarySystemException {
        if (bookType.equals("H")) {
            books.add(new Handwritten(books.size() + 1));
        } else if (bookType.equals("P")) {
            books.add(new Printed(books.size() + 1));
        } else {
            throw new LibrarySystemException("There is no such type of book");
        }
        FileIO.writeToFile(String.format("Created new book: %s [id: %d]", books.get(books.size() - 1).getClass().getSimpleName(), books.size()), fileName);
    }

    private void addMember(String memberType, String fileName) throws IOException, LibrarySystemException {
        if (memberType.equals("A")) {
            members.add(new Academic(members.size() + 1));
        } else if (memberType.equals("S")) {
            members.add(new Student(members.size() + 1));
        } else {
            throw new LibrarySystemException("There is no such type of member");
        }
        FileIO.writeToFile(String.format("Created new member: %s [id: %d]", members.get(members.size() - 1).getClass().getSimpleName(), members.size()), fileName);

    }

    private void borrowBook(int bookID, int memberID, Date bookDate, String fileName) throws LibrarySystemException, IOException {
        Book book = SearchBookAndMember.lookupBook(bookID, books);
        Member member = SearchBookAndMember.lookupMember(memberID, members);
        if (book != null) {
            if (book instanceof Handwritten) {
                throw new LibrarySystemException("You cannot borrow this book.");
            } else {
                if (member != null) {
                    if (book.getBorrowSituation().equals("Borrowed")) {
                        throw new LibrarySystemException("You cannot borrow this book.");
                    } else {
                        if (member.getBorrowedBooks().size() < member.getMaxBookNum()) { // lent book if its free
                            member.addBooks(book);
                            book.setBorrowDate(bookDate);
                            book.setBorrowedTo(memberID);
                            book.setBorrowSituation("Borrowed");
                            book.setDueDate(new Date(bookDate.getTime() + member.getTimeLimit()));
                            FileIO.writeToFile(String.format("The book [%d] was borrowed by member [%d] at %s",
                                    book.getBookID(), member.getMemberID(), book.getStrBorrowDate()), fileName);
                        } else {
                            throw new LibrarySystemException("You have exceeded the borrowing limit!");
                        }
                    }
                }
            }


        }


    }

    private void extendBook(int bookID, int memberID, Date currentDate, String fileName) throws LibrarySystemException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Book book = SearchBookAndMember.lookupBook(bookID, books);
        Member member = SearchBookAndMember.lookupMember(memberID, members);
        if (book != null && member != null) {
            if (book.getIsExtended() == 1) {
                throw new LibrarySystemException("You cannot extend the deadline!");
            } else {
                Date newDueDate = new Date(book.getDueDate().getTime() + member.getTimeLimit()); // calculate new due date
                book.setDueDate(newDueDate);
                book.setIsExtended(1);
                FileIO.writeToFile(String.format("The deadline of book [%d] was extended by member [%d] at %s", bookID, memberID, formatter.format(currentDate)), fileName);
                FileIO.writeToFile(String.format("New deadline of book [%d] is %s", bookID, formatter.format(newDueDate)), fileName);
            }
        }

    }

    private void readInLibrary(int bookID, int memberID, Date currentDate, String fileName) throws LibrarySystemException, IOException {
        Book book = SearchBookAndMember.lookupBook(bookID, books);
        Member member = SearchBookAndMember.lookupMember(memberID, members);
        if (book != null) {
            if (book.getBorrowSituation().equals("Borrowed") || book.getReadInLibrarySituation().equals("Borrowed")) {
                throw new LibrarySystemException("You can not read this book!");
            } else if (book instanceof Handwritten && member instanceof Student) {
                throw new LibrarySystemException("Students can not read handwritten books!");
            } else {
                if (member != null) {
                    book.setReadInLibrarySituation("Borrowed");
                    book.setBorrowDate(currentDate);
                    book.setBorrowedTo(memberID);
                    FileIO.writeToFile(String.format("The book [%d] was read in library by member [%d] at %s",
                            book.getBookID(), member.getMemberID(), book.getStrBorrowDate()), fileName);
                }

            }
        }


    }

    private void returnBook(int bookID, int memberID, Date returnDate, String fileName) throws LibrarySystemException, IOException {
        Book book = SearchBookAndMember.lookupBook(bookID, books);
        Member member = SearchBookAndMember.lookupMember(memberID, members);
        if (book != null && member != null) {
            if (book.getBorrowSituation().equals("") && book.getReadInLibrarySituation().equals("")) {
                throw new LibrarySystemException("This book is not borrowed");
            } else {
                int fee = 0;
                if (book.getDueDate() != null) {
                    if (book.getDueDate().before(returnDate)) { // calculate fee if the due date has passed
                        fee = (int) ((returnDate.getTime() - book.getDueDate().getTime())) / (1000 * 60 * 60 * 24);
                    }
                }
                book.setDueDate(null);
                book.setIsExtended(0);
                book.setBorrowSituation("");
                book.setReadInLibrarySituation("");
                member.getBorrowedBooks().remove(book);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = formatter.format(returnDate);
                FileIO.writeToFile(String.format("The book [%d] was returned by member [%d] at %s Fee: %d",
                        book.getBookID(), member.getMemberID(), strDate, fee), fileName);
            }
        }


    }

    private void getTheHistory(String fileName) throws IOException {
        FileIO.writeToFile("History of library:\n", fileName);
        List<Member> student = members.stream().filter(member -> member instanceof Student).collect(Collectors.toList());
        FileIO.writeToFile("Number of students: " + student.size(), fileName);
        for (Member s : student) {
            FileIO.writeToFile(String.format("Student [id: %d]", s.getMemberID()), fileName);
        }
        FileIO.writeToFile("", fileName);
        List<Member> academic = members.stream().filter(member -> member instanceof Academic).collect(Collectors.toList());
        FileIO.writeToFile("Number of academics: " + academic.size(), fileName);
        for (Member a : academic) {
            FileIO.writeToFile(String.format("Academic [id: %d]", a.getMemberID()), fileName);
        }
        FileIO.writeToFile("", fileName);
        List<Book> printed = books.stream().filter(book -> book instanceof Printed).collect(Collectors.toList());
        FileIO.writeToFile("Number of printed books: " + printed.size(), fileName);
        for (Book p : printed) {
            FileIO.writeToFile(String.format("Printed [id: %d]", p.getBookID()), fileName);
        }
        FileIO.writeToFile("", fileName);
        List<Book> handwritten = books.stream().filter(book -> book instanceof Handwritten).collect(Collectors.toList());
        FileIO.writeToFile("Number of handwritten books: " + handwritten.size(), fileName);
        for (Book h : handwritten) {
            FileIO.writeToFile(String.format("Handwritten [id: %d]", h.getBookID()), fileName);
        }
        FileIO.writeToFile("", fileName);
        List<Book> borrowed = books.stream().filter(book -> book.getBorrowSituation().equals("Borrowed")).collect(Collectors.toList());
        FileIO.writeToFile("Number of borrowed books: " + borrowed.size(), fileName);
        for (Book b : borrowed) {
            FileIO.writeToFile(String.format("The book [%d] was borrowed by member [%d] at %s", b.getBookID(), b.getBorrowedTo(), b.getStrBorrowDate()), fileName);
        }
        FileIO.writeToFile("", fileName);
        List<Book> readInLibrary = books.stream().filter(book -> book.getReadInLibrarySituation().equals("Borrowed")).collect(Collectors.toList());
        FileIO.writeToFile("Number of books read in library: " + readInLibrary.size(), fileName);
        for (Book b : readInLibrary) {
            FileIO.writeToFile(String.format("The book [%d] was read in library by member [%d] at %s", b.getBookID(), b.getBorrowedTo(), b.getStrBorrowDate()), fileName);
        }
    }

    public void executeCommands(String[] args) throws Exception {
        ArrayList<String> lines = FileIO.readFromFile(args[0]);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for (String line : lines) {
            try {
                String[] elements = line.split("\t");
                switch (elements[0]) {
                    case "addBook":
                        addBook(elements[1], args[1]);
                        break;
                    case "addMember":
                        addMember(elements[1], args[1]);
                        break;
                    case "borrowBook":
                        borrowBook(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), formatter.parse(elements[3]), args[1]);
                        break;
                    case "returnBook":
                        returnBook(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), formatter.parse(elements[3]), args[1]);
                        break;
                    case "extendBook":
                        extendBook(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), formatter.parse(elements[3]), args[1]);
                        break;
                    case "readInLibrary":
                        readInLibrary(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), formatter.parse(elements[3]), args[1]);
                        break;
                    case "getTheHistory":
                        getTheHistory(args[1]);
                        break;
                    default:
                        FileIO.writeToFile("There is no such command", args[1]);
                }
            } catch (LibrarySystemException e) {
                FileIO.writeToFile(e.getMessage(), args[1]);

            }


        }
    }
}
