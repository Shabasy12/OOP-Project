package Library;

import java.util.Scanner;

public class DeleteBook implements IOOperation {
    @Override
    public void oper(Database database, User user) {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter Book name: ");
        String bookName = s.next();

        int i = database.getBook(bookName);// إذا تم العثور على الكتاب، يتم حذفه من قاعدة البيانات
        if (i>-1){
            database.deleteBook(i);
            System.out.println("Book Deleted Successfully!\n");
        }else{
            System.out.println("Book Not Found!\n");
        }
        user.menu(database,user);

    }
}
