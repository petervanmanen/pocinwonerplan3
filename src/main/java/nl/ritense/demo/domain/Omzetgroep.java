package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Omzetgroep.
 */
@Entity
@Table(name = "omzetgroep")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Omzetgroep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenOmzetgroeps")
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
    private Set<Product> valtbinnenProducts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Omzetgroep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Omzetgroep naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Omzetgroep omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Product> getValtbinnenProducts() {
        return this.valtbinnenProducts;
    }

    public void setValtbinnenProducts(Set<Product> products) {
        if (this.valtbinnenProducts != null) {
            this.valtbinnenProducts.forEach(i -> i.removeValtbinnenOmzetgroep(this));
        }
        if (products != null) {
            products.forEach(i -> i.addValtbinnenOmzetgroep(this));
        }
        this.valtbinnenProducts = products;
    }

    public Omzetgroep valtbinnenProducts(Set<Product> products) {
        this.setValtbinnenProducts(products);
        return this;
    }

    public Omzetgroep addValtbinnenProduct(Product product) {
        this.valtbinnenProducts.add(product);
        product.getValtbinnenOmzetgroeps().add(this);
        return this;
    }

    public Omzetgroep removeValtbinnenProduct(Product product) {
        this.valtbinnenProducts.remove(product);
        product.getValtbinnenOmzetgroeps().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Omzetgroep)) {
            return false;
        }
        return getId() != null && getId().equals(((Omzetgroep) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Omzetgroep{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
