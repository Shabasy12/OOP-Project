package Library;

import java.util.ArrayList;

public class ViewBooks implements IOOperation {

    @Override
    public void oper(Database database, User user) {

        ArrayList<Book> books = database.getAllBooks();// استرداد قائمة الكتب من قاعدة البيانات
        System.out.println("Name\t\tAuthor\t\tPublisher\t\tCLA\t\tQty\t\tprice"
        +"\t\tBrw cps"); // عرض رأس الجدول
        for (Book b : books) {// عرض تفاصيل كل كتاب في القائمة
            System.out.println(b.getName()+"\t\t"+b.getAuthor()+"\t\t"+b.getPublisher()+"\t\t\t"+
                    b.getAddress()+"\t\t"+b.getQty()+"\t\t"+b.getPrice()+"\t\t"+b.getBrwcopies());
        }
        System.out.println();
        user.menu(database,user);
    }

}
