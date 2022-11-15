import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {

        System.out.println("/show покупатель -  Показать продукты по покупателю\n" +
                "/find продукт - Найти покупателя по названию продукта\n" +
                "/removeProduct - удалить продукт\n" +
                "/buy покупатель продукт цена - покупка товара\n");

        Scanner keyboard = new Scanner(System.in);
        String str = keyboard.nextLine();

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Purchase.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        if (str.contains("/show ")) {
            show(factory, remove(str, "/show "));
        } else if (str.contains("/find ")) {
            find(factory, remove(str, "/find "));
        } else if (str.contains("/remove ")) {
            remove(factory, remove(str, "/remove "));
        } else if (str.contains("/buy ")) {
            buy(factory, remove(str, "/buy "));
        } else {
            System.out.println("Введена неверная команда!");
        }
        try {
//

        } finally {
            factory.close();
            session.close();
        }

    }

    public static String remove(String text, String target) {
        return text.replace(target, "");
    }

    private static void show(SessionFactory factory, String sh) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from Purchase where id > 0");
        List result = q.list();
        session.getTransaction().commit();
        for (int i = 1; i <= result.size(); i++) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            Purchase purchase = session.get(Purchase.class, i);
            Person a = purchase.getPersons();
            if (a.getName().replace(" ", "").equals(sh.replace(" ", ""))) {
                System.out.print(purchase.getProducts().getTitle()+"    ");
                System.out.println(purchase.price);
            }
            session.getTransaction().commit();
        }
    }

    private static void find(SessionFactory factory, String fd) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from Purchase where id > 0");
        List result = q.list();
        session.getTransaction().commit();
        for (int i = 1; i <= result.size(); i++) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            Purchase purchase = session.get(Purchase.class, i);
            Product a = purchase.getProducts();
            if (a.getTitle().replace(" ", "").equals(fd.replace(" ", ""))) {
                System.out.println(purchase.getPersons().getName());
            }
            session.getTransaction().commit();
        }
    }

    private static void remove(SessionFactory factory, String person) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("delete Product where title = :paramName");
        q.setParameter("paramName", person.replace(" ", ""));
        q.executeUpdate();
        session.getTransaction().commit();
    }

    private static void buy(SessionFactory factory, String g) {
        String[] a = g.split(" ");
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Person p0 = new Person();
        Product p1 = new Product();
        for(int i = 1; i <= 4; i++) {
            Person pers = session.get(Person.class, i);
            if (pers.getName().replace(" ", "").equals(a[0].replace(" ", ""))){
                p0 = session.get(Person.class, i);
            }
            Product prod = session.get(Product.class, i);
            if (prod.getTitle().replace(" ", "").equals(a[1].replace(" ", ""))){
                p1 = session.get(Product.class, i);
            }
        }
        Purchase purchase = new Purchase();
        purchase.persons = p0;
        purchase.products = p1;
        purchase.price = p1.getPrice();
        session.save(purchase);
        session.getTransaction().commit();
    }
}

