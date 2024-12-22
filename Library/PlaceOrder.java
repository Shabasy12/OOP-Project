package Library;

import java.util.Scanner;

public class PlaceOrder implements IOOperation{
    @Override
    public void oper(Database database, User user) {
        Order order= new Order();
        System.out.println("\nEnter book name:");
        Scanner s = new Scanner(System.in);
        String bookName = s.next();
        int i =database.getBook(bookName);
        if (i<=-1) {
            System.out.println("Book does not exist");

        }else{
            Book book=database.getBook(i);// استرداد الكتاب من قاعدة البيانات
            order.setBook(book);// تعيين الكتاب إلى الطلب
            order.setUser(user);// تعيين المستخدم إلى الطلب
            System.out.println("Enter qty:");// طلب إدخال الكمية المطلوبة
            int qty = s.nextInt();
            order.setQty(qty);
            order.setPrice(book.getPrice()*qty); // حساب السعر الإجمالي للطلب
            int bookindex = database.getBook(book.getName());// الحصول على فهرس الكتاب
            book.setQty(book.getQty()-qty);// تحديث الكمية المتوفرة من الكتاب في المخزون
            database.addOrder(order,book,bookindex);
            System.out.println("Order placed successfully!\n");
        }
        user.menu(database,user);

    }
}
