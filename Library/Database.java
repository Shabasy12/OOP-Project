package Library;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {// القوائم المستخدمة لتخزين بيانات المستخدمين، الكتب، والطلبات
        private  ArrayList<User> users = new ArrayList<User>();
        private ArrayList<String> usernames = new ArrayList<String>();
        private ArrayList<Book> books = new ArrayList<Library.Book>();
        private  ArrayList<String> booknames = new ArrayList<String>();
        private  ArrayList<Order> orders = new ArrayList<Order>();
        private  ArrayList<Borrowing> borrowings = new ArrayList<Borrowing>();
                     // تعريف الملفات التي تخزن البيانات
        private File usersfile  = new File("C:\\Users\\DELL\\IdeaProjects\\OOP-Project\\src\\Users");
        private File booksfile  = new File("C:\\Users\\DELL\\IdeaProjects\\OOP-Project\\src\\Books");
        private File ordersfile  = new File("C:\\Users\\DELL\\IdeaProjects\\OOP-Project\\src\\orders");
        private File borrowingsfile  = new File("C:\\Users\\DELL\\IdeaProjects\\OOP-Project\\src\\Borrowings");
        private File folder =new File("C:\\Users\\DELL\\IdeaProjects\\OOP-Project\\src");
        public Database() {// إنشاء المجلد الرئيسي إذا لم يكن موجودًا
            if(!folder.exists()){
                folder.mkdirs();
            }
            if(!usersfile.exists()) {// إنشاء ملفات البيانات إذا لم تكن موجودة
                try {
                    usersfile.createNewFile();
                }catch(Exception e){}

            }
            if(!booksfile.exists()) {
                try {
                    booksfile.createNewFile();
                }catch(Exception e){}
            }
            if(!ordersfile.exists()) {
                try {
                    ordersfile.createNewFile();
                }catch(Exception e){}
            }// استرداد البيانات من الملفات عند بدء تشغيل النظام
            if(!borrowingsfile.exists()) {
                try {
                    borrowingsfile.createNewFile();
                }catch(Exception e){}
            }
            getUsers();
            getBooks();
            getOrders();
            getBorrowings();



        }
            // إضافة مستخدم جديد إلى قاعدة البيانات
        public void AddUser(User s){
            users.add(s);
            usernames.add(s.getName());// إضافة اسم المستخدم إلى قائمة الأسماء
            saveUsers();

        }
        public int login(String phonenumber, String email){
            int n= -1;// إذا لم يتم العثور على المستخدم
            for(User s : users){
                if(s.getName().matches(phonenumber) && s.getEmail().matches(email)){
                    n=users.indexOf(s);// إرجاع فهرس المستخدم
                    break;
                }
            }
            return n;
        }
        public User getUser(int n){
            return users.get(n);


        }
        public void AddBook(Library.Book book){
            books.add(book);
            booknames.add(book.toString2());
            saveBooks();
        }

            private void getUsers(){// تحميل بيانات المستخدمين من الملف
            String text1  ="";
            try {
                BufferedReader br1 = new BufferedReader(new FileReader(usersfile));
                String s1;
                while ((s1 = br1.readLine()) != null) {
                    text1 = text1+ s1;
                }
                br1.close();
            }catch (Exception e){
                System.err.println(e.toString());
            }
            if (!text1.matches("")|| !text1.isEmpty() ) {// تحليل البيانات النصية وإضافتها إلى القوائم
                String[] a1 = text1.split("<NewUser>");
                for (String s : a1) {
                    String[] a2 = s.split("<N/>");
                    if (a2[3].matches("Admin")){
                        User user   = new Admin(a2[0],a2[1],a2[2]);
                        users.add(user);
                        usernames.add(user.getName());
                    }else {
                        User user   = new NormalUser(a2[0],a2[1],a2[2]);
                        users.add(user);
                        usernames.add(user.getName());
                    }
                }
            }
            }
            private void saveUsers(){// حفظ بيانات المستخدمين إلى الملف
            String text1  ="";
            for (User user : users) {
                text1 = text1 + user.toString()+"<NewUser>\n";
            }
            try {
                PrintWriter pw = new PrintWriter(usersfile);
                pw.println(text1);
                pw.close();
            }catch (Exception e){
                System.err.println(e.toString());
            }
            }
    private void saveBooks(){
        String text1  ="";
        for (Library.Book book : books) {
            text1 = text1 + book.toString()+"<NewBook>\n";
        }
        try {
            PrintWriter pw = new PrintWriter(booksfile);
            pw.println(text1);
            pw.close();
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }
    private void getBooks(){// تحميل بيانات الكتب من الملف
        String text1 ="";
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(booksfile));
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1 = text1+ s1;
            }
            br1.close();
        }catch (Exception e){
            System.err.println(e.toString());
        }
        if (!text1.matches("")|| !text1.isEmpty() ) {// تحليل البيانات النصية وإضافتها إلى القوائم
            String[] a1 = text1.split("<NewBook>");
            for (String s : a1) {
                Library.Book book= parseBook(s);
                if (book != null) {
                    books.add(book);
                    booknames.add(book.getName());
                }


            }
        }
    }

public Library.Book parseBook(String s) {// تحويل نص الكتاب إلى كائن كتاب
    try {
        String name = s.substring(s.indexOf("Book Name: ") + 11, s.indexOf("Book Author: ")).trim();
        String author = s.substring(s.indexOf("Book Author: ") + 13, s.indexOf("Book Publisher: ")).trim();
        String publisher = s.substring(s.indexOf("Book Publisher: ") + 16, s.indexOf("Book Collection Address: ")).trim();
        String address = s.substring(s.indexOf("Book Collection Address: ") + 25, s.indexOf("Qty: ")).trim();
        int qty = Integer.parseInt(s.substring(s.indexOf("Qty: ") + 5, s.indexOf("Price: ")).trim());
        double price = Double.parseDouble(s.substring(s.indexOf("Price: ") + 7, s.indexOf("Borrowing Copies: ")).trim());
        int brwCopies = Integer.parseInt(s.substring(s.indexOf("Borrowing Copies: ") + 18).trim());

        Library.Book book = new Library.Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setAddress(address);
        book.setQty(qty);
        book.setPrice(price);
        book.setBrwcopies(brwCopies);

        return book;
    } catch (Exception e) {
        System.err.println("Error parsing book: " + s + " | " + e.getMessage());
        return null;
    }
}
    public ArrayList<Book> getAllBooks(){
            return books;
    }
    public int getBook(String name) {
        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).getName().equalsIgnoreCase(name.trim())) {
                return i;
            }
        }
        return -1;
    }
    public Book getBook(int i){
        return books.get(i);
    }
    public void deleteBook(int i){
            books.remove(i);
            booknames.remove(i);
            saveBooks();
    }
    public void deleteAllData() {
        users.clear(); // حذف جميع المستخدمين من الذاكرة
        usernames.clear(); // حذف أسماء المستخدمين
        books.clear(); // حذف جميع الكتب من الذاكرة
        booknames.clear(); // حذف أسماء الكتب
        saveUsers(); // تحديث ملف المستخدمين
        saveBooks(); // تحديث ملف الكتب

        if (usersfile.exists()) {
            usersfile.delete(); // حذف ملف المستخدمين
        }
        if (booksfile.exists()) {
            booksfile.delete(); // حذف ملف الكتب
        }
        if (ordersfile.exists()) {
            ordersfile.delete(); // حذف ملف الطلبات
        }
        if (borrowingsfile.exists()) {
            borrowingsfile.delete();
        }

        System.out.println("All data has been deleted successfully.");
    }


    public void addOrder(Order order,Book book,int bookindex){
        if (!orders.contains(order)) { // منع التكرار
            orders.add(order);
            books.set(bookindex, book);// تحديث بيانات الكتاب في القائمة
            saveOrders();
            saveBooks();
        }
    }
    private void saveOrders(){
        String text1  ="";
        for (Order order : orders) {
            text1 = text1 + order.toString2()+"<NewOrder>\n";// تحويل الطلب إلى نص بصيغة محددة
        }
        try {
            PrintWriter pw = new PrintWriter(ordersfile);
            pw.print(text1);
            pw.close();
        }catch (Exception e){
            System.err.println(e.toString());// طباعة أي خطأ يحدث أثناء الحفظ
        }
    }
    private void getOrders(){
        String text1 ="";
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(ordersfile));
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1 = text1+ s1;// قراءة جميع الطلبات من الملف
            }
            br1.close();
        }catch (Exception e){
            System.err.println(e.toString());
        }
        if (!text1.matches("")|| !text1.isEmpty() ) {// التحقق إذا كان الملف يحتوي على بيانات
            String[] a1 = text1.split("<NewOrder>");
            for (String s : a1) {
            Order order = parseOrder(s);
            orders.add(order);
//                Library.Book book= parseBook(s);
//                if (book != null) {
//                    books.add(book);
//                    booknames.add(book.getName());
                }

            }
        }
        public boolean userExists(String name ) {
            boolean f = false;
            for (User user : users) {
                if (user.getName().toLowerCase().matches(name.toLowerCase())) {// البحث عن المستخدم بالاسم
                    f = true;// إذا وجد المستخدم يتم إرجاعه
                    break;
                }
            }
            return f;
        }

        private User getUserByName(String name){
            User u= new NormalUser("");// كائن افتراضي في حالة عدم العثور
            for (User user : users) {
                if (user.getName().matches(name)) {// البحث عن المستخدم بالاسم
                    u = user;// إذا وجد المستخدم يتم إرجاعه
                    break;
                }
            }
            return u;
        }
        public Order parseOrder(String s) {
            try {
                String[] a = s.split("<N/>");// تقسيم النص إلى أجزاء بناءً على التنسيق
                if (a.length < 4) {
                    System.err.println("Error: Invalid order format: " + s);// إذا كانت البيانات غير مكتملة
                    return null;
                }

                int bookIndex = getBook(a[0]);// البحث عن الكتاب باستخدام الاسم
                if (bookIndex == -1) {
                    System.err.println("Error: Book not found: " + a[0]);
                    return null;
                }

                User user = getUserByName(a[1]);
                if (user == null) {
                    System.err.println("Error: User not found: " + a[1]);
                    return null;
                }

                double price = Double.parseDouble(a[2]);
                int quantity = Integer.parseInt(a[3]);

                return new Order(books.get(bookIndex), user, price, quantity);// إنشاء الطلب وإرجاعه
            } catch (Exception e) {
                System.err.println("Error parsing order: " + s + " | " + e.getMessage());// طباعة الأخطاء أثناء التحليل
                return null;
            }
        }
        public ArrayList<Order> getAllOrders(){
            return orders;
        }

    private void saveBorrowings() {
        String text1 = "";
        for (Borrowing borrowing : borrowings) {
            // التحقق من أنه  غير فارغ وجميع البيانات موجودة
            if (borrowing != null && borrowing.getBook() != null && borrowing.getUser() != null) {
                text1 += borrowing.toString2() + "<NewBorrowing>\n";
            }
        }
        try {
            PrintWriter pw = new PrintWriter(borrowingsfile);
            pw.print(text1);
            pw.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void getBorrowings() {
        String text1 = "";
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(borrowingsfile));
            String s1;
            while ((s1 = br1.readLine()) != null) {
                text1 =text1 + s1; // قراءة النصوص من الملف
            }
            br1.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        // التحقق إذا كان النص يحتوي على بيانات
        if (!text1.isEmpty()) {
            String[] a1 = text1.split("<NewBorrowing>");
            for (String s : a1) {
                Borrowing borrowing = parseBorrowing(s); // تحليل النصوص إلى كائنات Borrowing
                if (borrowing != null) {
                    borrowings.add(borrowing); // إضافة الكائن إلى القائمة
                }
            }
        }
    }
    private Borrowing parseBorrowing(String s) {
        try {
            // تقسيم النص إلى أجزاء باستخدام "<N/>"
            String[] a = s.split("<N/>");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(a[0], formatter); // تحليل التاريخ الأول
            LocalDate finish = LocalDate.parse(a[1], formatter); // تحليل التاريخ الثاني
            Book book = getBook(getBook(a[3]));
            User user = getUserByName(a[4]);
            Borrowing brw =new Borrowing(start, book, user);
            return brw;
        } catch (Exception e) {
            System.err.println("Error parsing borrowing data: " + s + " | " + e.getMessage());
            return null;
        }
    }

    public void borrowBook(Borrowing brw, Book book, int bookindex) {
        borrowings.add(brw);
        books.set(bookindex, book);
        saveBorrowings();
        saveBooks();

    }
    public ArrayList<Borrowing> getBrws() {
        return borrowings;
    }

    public void returnBook(Borrowing b, Book book, int bookindex) {
            borrowings.remove(b);
            books.set(bookindex, book);
            saveBorrowings();
            saveBooks();
    }

}


