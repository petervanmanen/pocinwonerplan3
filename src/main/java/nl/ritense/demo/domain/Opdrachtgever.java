package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Opdrachtgever.
 */
@Entity
@Table(name = "opdrachtgever")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Opdrachtgever implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "clustercode", length = 20)
    private String clustercode;

    @Column(name = "clusteromschrijving")
    private String clusteromschrijving;

    @Column(name = "naam")
    private String naam;

    @Size(max = 20)
    @Column(name = "nummer", length = 20)
    private String nummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isopdrachtgeverOpdrachtgever")
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
    private Set<Product> isopdrachtgeverProducts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "uitgevoerddoorOpdrachtgevers",
            "uitgevoerddoorOpdrachtnemers",
            "dienstverbandconformfunctieDienstverbands",
            "vacaturebijfunctieVacatures",
        },
        allowSetters = true
    )
    private Functie uitgevoerddoorFunctie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Opdrachtgever id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClustercode() {
        return this.clustercode;
    }

    public Opdrachtgever clustercode(String clustercode) {
        this.setClustercode(clustercode);
        return this;
    }

    public void setClustercode(String clustercode) {
        this.clustercode = clustercode;
    }

    public String getClusteromschrijving() {
        return this.clusteromschrijving;
    }

    public Opdrachtgever clusteromschrijving(String clusteromschrijving) {
        this.setClusteromschrijving(clusteromschrijving);
        return this;
    }

    public void setClusteromschrijving(String clusteromschrijving) {
        this.clusteromschrijving = clusteromschrijving;
    }

    public String getNaam() {
        return this.naam;
    }

    public Opdrachtgever naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Opdrachtgever nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Opdrachtgever omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Product> getIsopdrachtgeverProducts() {
        return this.isopdrachtgeverProducts;
    }

    public void setIsopdrachtgeverProducts(Set<Product> products) {
        if (this.isopdrachtgeverProducts != null) {
            this.isopdrachtgeverProducts.forEach(i -> i.setIsopdrachtgeverOpdrachtgever(null));
        }
        if (products != null) {
            products.forEach(i -> i.setIsopdrachtgeverOpdrachtgever(this));
        }
        this.isopdrachtgeverProducts = products;
    }

    public Opdrachtgever isopdrachtgeverProducts(Set<Product> products) {
        this.setIsopdrachtgeverProducts(products);
        return this;
    }

    public Opdrachtgever addIsopdrachtgeverProduct(Product product) {
        this.isopdrachtgeverProducts.add(product);
        product.setIsopdrachtgeverOpdrachtgever(this);
        return this;
    }

    public Opdrachtgever removeIsopdrachtgeverProduct(Product product) {
        this.isopdrachtgeverProducts.remove(product);
        product.setIsopdrachtgeverOpdrachtgever(null);
        return this;
    }

    public Functie getUitgevoerddoorFunctie() {
        return this.uitgevoerddoorFunctie;
    }

    public void setUitgevoerddoorFunctie(Functie functie) {
        this.uitgevoerddoorFunctie = functie;
    }

    public Opdrachtgever uitgevoerddoorFunctie(Functie functie) {
        this.setUitgevoerddoorFunctie(functie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opdrachtgever)) {
            return false;
        }
        return getId() != null && getId().equals(((Opdrachtgever) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opdrachtgever{" +
            "id=" + getId() +
            ", clustercode='" + getClustercode() + "'" +
            ", clusteromschrijving='" + getClusteromschrijving() + "'" +
            ", naam='" + getNaam() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
