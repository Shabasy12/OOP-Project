package Library;

import java.util.Scanner;

public class DeleteAllData implements IOOperation{
    @Override
    public void oper(Database database, User user) {
        System.out.println("\nAre you sure you want to delete all data?\n"
        +"1. Continue\n2. Main Menu");
        Scanner s = new Scanner(System.in);
        int i = s.nextInt();// قراءة خيار المستخدم
        if (i == 1) {
            database.deleteAllData();// إذا اختار المستخدم المتابعة، يتم حذف جميع البيانات
        }else{
            user.menu(database,user); // إذا اختار العودة، يتم الرجوع إلى القائمة الرئيسية

        }

    }
}
