package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Productgroep.
 */
@Entity
@Table(name = "productgroep")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Productgroep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beslisboom")
    private String beslisboom;

    @Column(name = "code")
    private String code;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "soortProductgroep")
    @JsonIgnoreProperties(
        value = { "resulteertParkeerrecht", "houderRechtspersoon", "soortProductgroep", "soortProductsoort" },
        allowSetters = true
    )
    private Set<Parkeervergunning> soortParkeervergunnings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenProductgroep")
    @JsonIgnoreProperties(value = { "soortParkeervergunnings", "valtbinnenProductgroep" }, allowSetters = true)
    private Set<Productsoort> valtbinnenProductsoorts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenProductgroeps")
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

    public Productgroep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeslisboom() {
        return this.beslisboom;
    }

    public Productgroep beslisboom(String beslisboom) {
        this.setBeslisboom(beslisboom);
        return this;
    }

    public void setBeslisboom(String beslisboom) {
        this.beslisboom = beslisboom;
    }

    public String getCode() {
        return this.code;
    }

    public Productgroep code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Productgroep omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Parkeervergunning> getSoortParkeervergunnings() {
        return this.soortParkeervergunnings;
    }

    public void setSoortParkeervergunnings(Set<Parkeervergunning> parkeervergunnings) {
        if (this.soortParkeervergunnings != null) {
            this.soortParkeervergunnings.forEach(i -> i.setSoortProductgroep(null));
        }
        if (parkeervergunnings != null) {
            parkeervergunnings.forEach(i -> i.setSoortProductgroep(this));
        }
        this.soortParkeervergunnings = parkeervergunnings;
    }

    public Productgroep soortParkeervergunnings(Set<Parkeervergunning> parkeervergunnings) {
        this.setSoortParkeervergunnings(parkeervergunnings);
        return this;
    }

    public Productgroep addSoortParkeervergunning(Parkeervergunning parkeervergunning) {
        this.soortParkeervergunnings.add(parkeervergunning);
        parkeervergunning.setSoortProductgroep(this);
        return this;
    }

    public Productgroep removeSoortParkeervergunning(Parkeervergunning parkeervergunning) {
        this.soortParkeervergunnings.remove(parkeervergunning);
        parkeervergunning.setSoortProductgroep(null);
        return this;
    }

    public Set<Productsoort> getValtbinnenProductsoorts() {
        return this.valtbinnenProductsoorts;
    }

    public void setValtbinnenProductsoorts(Set<Productsoort> productsoorts) {
        if (this.valtbinnenProductsoorts != null) {
            this.valtbinnenProductsoorts.forEach(i -> i.setValtbinnenProductgroep(null));
        }
        if (productsoorts != null) {
            productsoorts.forEach(i -> i.setValtbinnenProductgroep(this));
        }
        this.valtbinnenProductsoorts = productsoorts;
    }

    public Productgroep valtbinnenProductsoorts(Set<Productsoort> productsoorts) {
        this.setValtbinnenProductsoorts(productsoorts);
        return this;
    }

    public Productgroep addValtbinnenProductsoort(Productsoort productsoort) {
        this.valtbinnenProductsoorts.add(productsoort);
        productsoort.setValtbinnenProductgroep(this);
        return this;
    }

    public Productgroep removeValtbinnenProductsoort(Productsoort productsoort) {
        this.valtbinnenProductsoorts.remove(productsoort);
        productsoort.setValtbinnenProductgroep(null);
        return this;
    }

    public Set<Product> getValtbinnenProducts() {
        return this.valtbinnenProducts;
    }

    public void setValtbinnenProducts(Set<Product> products) {
        if (this.valtbinnenProducts != null) {
            this.valtbinnenProducts.forEach(i -> i.removeValtbinnenProductgroep(this));
        }
        if (products != null) {
            products.forEach(i -> i.addValtbinnenProductgroep(this));
        }
        this.valtbinnenProducts = products;
    }

    public Productgroep valtbinnenProducts(Set<Product> products) {
        this.setValtbinnenProducts(products);
        return this;
    }

    public Productgroep addValtbinnenProduct(Product product) {
        this.valtbinnenProducts.add(product);
        product.getValtbinnenProductgroeps().add(this);
        return this;
    }

    public Productgroep removeValtbinnenProduct(Product product) {
        this.valtbinnenProducts.remove(product);
        product.getValtbinnenProductgroeps().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Productgroep)) {
            return false;
        }
        return getId() != null && getId().equals(((Productgroep) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Productgroep{" +
            "id=" + getId() +
            ", beslisboom='" + getBeslisboom() + "'" +
            ", code='" + getCode() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
