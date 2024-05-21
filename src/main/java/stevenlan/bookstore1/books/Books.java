package stevenlan.bookstore1.books;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Books {

    @Id
    @SequenceGenerator(
        name = "books_sequence",
        sequenceName = "books_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "books_sequence"
    )

    private Long id;
    private String title;
    private String author;
    private String description;
    private Integer listPrice;
    private Integer salePrice;

    public Books(String title, String author, String description, Integer listPrice, Integer salePrice) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
    }

    public Books(Long id, String title, String author, String description, Integer listPrice, Integer salePrice) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
    }

    public Books() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getListPrice() {
        return listPrice;
    }

    public void setListPrice(Integer listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "Books [id=" + id + ", title=" + title + ", author=" + author + ", description=" + description
                + ", listPrice=" + listPrice + ", salePrice=" + salePrice + "]";
    }
    

}
