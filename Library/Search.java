package Library;

import java.util.Scanner;

public class Search implements IOOperation{
    @Override
    public void oper(Database database, User user) {
        System.out.println("\nEnter book name:");
        Scanner s = new Scanner(System.in);
        String name = s.next();
        int i = database.getBook(name);// البحث عن الكتاب في قاعدة البيانات
        if (i>-1){// إذا تم العثور على الكتاب
            System.out.println("\n"+database.getBook(i).toString()+"\n");// عرض تفاصيل الكتاب
        }else{
            System.out.println("Book Not Found!\n");
        }
        user.menu(database,user);

    }
}
