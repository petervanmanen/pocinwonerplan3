package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Doelstelling.
 */
@Entity
@Table(name = "doelstelling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Doelstelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftDoelstelling")
    @JsonIgnoreProperties(
        value = {
            "heeftprijsPrijs",
            "betreftZaaktypes",
            "leverancierLeverancier",
            "heeftKostenplaats",
            "valtbinnenOmzetgroeps",
            "valtbinnenProductgroeps",
            "betreftWinkelvoorraaditem",
            "heeftDoelstelling",
            "isopdrachtgeverOpdrachtgever",
            "isopdrachtnemerOpdrachtnemer",
            "betreftBalieverkoops",
            "betreftDiensts",
            "betreftBegrotingregels",
        },
        allowSetters = true
    )
    private Set<Product> heeftProducts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isvansoortDoelstellings" }, allowSetters = true)
    private Doelstellingsoort isvansoortDoelstellingsoort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftDoelstellings", "binnenPeriodes", "betreftBegrotingregels" }, allowSetters = true)
    private Hoofdstuk heeftHoofdstuk;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftDoelstelling")
    @JsonIgnoreProperties(
        value = {
            "betreftDoelstelling", "betreftProduct", "betreftKostenplaats", "betreftHoofdrekening", "betreftHoofdstuk", "heeftBegroting",
        },
        allowSetters = true
    )
    private Set<Begrotingregel> betreftBegrotingregels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Doelstelling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Doelstelling omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Product> getHeeftProducts() {
        return this.heeftProducts;
    }

    public void setHeeftProducts(Set<Product> products) {
        if (this.heeftProducts != null) {
            this.heeftProducts.forEach(i -> i.setHeeftDoelstelling(null));
        }
        if (products != null) {
            products.forEach(i -> i.setHeeftDoelstelling(this));
        }
        this.heeftProducts = products;
    }

    public Doelstelling heeftProducts(Set<Product> products) {
        this.setHeeftProducts(products);
        return this;
    }

    public Doelstelling addHeeftProduct(Product product) {
        this.heeftProducts.add(product);
        product.setHeeftDoelstelling(this);
        return this;
    }

    public Doelstelling removeHeeftProduct(Product product) {
        this.heeftProducts.remove(product);
        product.setHeeftDoelstelling(null);
        return this;
    }

    public Doelstellingsoort getIsvansoortDoelstellingsoort() {
        return this.isvansoortDoelstellingsoort;
    }

    public void setIsvansoortDoelstellingsoort(Doelstellingsoort doelstellingsoort) {
        this.isvansoortDoelstellingsoort = doelstellingsoort;
    }

    public Doelstelling isvansoortDoelstellingsoort(Doelstellingsoort doelstellingsoort) {
        this.setIsvansoortDoelstellingsoort(doelstellingsoort);
        return this;
    }

    public Hoofdstuk getHeeftHoofdstuk() {
        return this.heeftHoofdstuk;
    }

    public void setHeeftHoofdstuk(Hoofdstuk hoofdstuk) {
        this.heeftHoofdstuk = hoofdstuk;
    }

    public Doelstelling heeftHoofdstuk(Hoofdstuk hoofdstuk) {
        this.setHeeftHoofdstuk(hoofdstuk);
        return this;
    }

    public Set<Begrotingregel> getBetreftBegrotingregels() {
        return this.betreftBegrotingregels;
    }

    public void setBetreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        if (this.betreftBegrotingregels != null) {
            this.betreftBegrotingregels.forEach(i -> i.setBetreftDoelstelling(null));
        }
        if (begrotingregels != null) {
            begrotingregels.forEach(i -> i.setBetreftDoelstelling(this));
        }
        this.betreftBegrotingregels = begrotingregels;
    }

    public Doelstelling betreftBegrotingregels(Set<Begrotingregel> begrotingregels) {
        this.setBetreftBegrotingregels(begrotingregels);
        return this;
    }

    public Doelstelling addBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.add(begrotingregel);
        begrotingregel.setBetreftDoelstelling(this);
        return this;
    }

    public Doelstelling removeBetreftBegrotingregel(Begrotingregel begrotingregel) {
        this.betreftBegrotingregels.remove(begrotingregel);
        begrotingregel.setBetreftDoelstelling(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doelstelling)) {
            return false;
        }
        return getId() != null && getId().equals(((Doelstelling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doelstelling{" +
            "id=" + getId() +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
