import javax.persistence.*;

@Entity
@Table(name = "purchase")

public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    public Person persons;

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product products;

    @Column
    public int price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPersons() {
        return persons;
    }

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products = products;
    }

    public void setPersons(Person persons) {
        this.persons = persons;
    }

}

