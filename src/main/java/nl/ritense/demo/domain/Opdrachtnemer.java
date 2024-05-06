package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Opdrachtnemer.
 */
@Entity
@Table(name = "opdrachtnemer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Opdrachtnemer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "clustercode", length = 20)
    private String clustercode;

    @Column(name = "clustercodeomschrijving")
    private String clustercodeomschrijving;

    @Column(name = "naam")
    private String naam;

    @Size(max = 20)
    @Column(name = "nummer", length = 20)
    private String nummer;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isopdrachtnemerOpdrachtnemer")
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
    private Set<Product> isopdrachtnemerProducts = new HashSet<>();

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

    public Opdrachtnemer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClustercode() {
        return this.clustercode;
    }

    public Opdrachtnemer clustercode(String clustercode) {
        this.setClustercode(clustercode);
        return this;
    }

    public void setClustercode(String clustercode) {
        this.clustercode = clustercode;
    }

    public String getClustercodeomschrijving() {
        return this.clustercodeomschrijving;
    }

    public Opdrachtnemer clustercodeomschrijving(String clustercodeomschrijving) {
        this.setClustercodeomschrijving(clustercodeomschrijving);
        return this;
    }

    public void setClustercodeomschrijving(String clustercodeomschrijving) {
        this.clustercodeomschrijving = clustercodeomschrijving;
    }

    public String getNaam() {
        return this.naam;
    }

    public Opdrachtnemer naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Opdrachtnemer nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Opdrachtnemer omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Product> getIsopdrachtnemerProducts() {
        return this.isopdrachtnemerProducts;
    }

    public void setIsopdrachtnemerProducts(Set<Product> products) {
        if (this.isopdrachtnemerProducts != null) {
            this.isopdrachtnemerProducts.forEach(i -> i.setIsopdrachtnemerOpdrachtnemer(null));
        }
        if (products != null) {
            products.forEach(i -> i.setIsopdrachtnemerOpdrachtnemer(this));
        }
        this.isopdrachtnemerProducts = products;
    }

    public Opdrachtnemer isopdrachtnemerProducts(Set<Product> products) {
        this.setIsopdrachtnemerProducts(products);
        return this;
    }

    public Opdrachtnemer addIsopdrachtnemerProduct(Product product) {
        this.isopdrachtnemerProducts.add(product);
        product.setIsopdrachtnemerOpdrachtnemer(this);
        return this;
    }

    public Opdrachtnemer removeIsopdrachtnemerProduct(Product product) {
        this.isopdrachtnemerProducts.remove(product);
        product.setIsopdrachtnemerOpdrachtnemer(null);
        return this;
    }

    public Functie getUitgevoerddoorFunctie() {
        return this.uitgevoerddoorFunctie;
    }

    public void setUitgevoerddoorFunctie(Functie functie) {
        this.uitgevoerddoorFunctie = functie;
    }

    public Opdrachtnemer uitgevoerddoorFunctie(Functie functie) {
        this.setUitgevoerddoorFunctie(functie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opdrachtnemer)) {
            return false;
        }
        return getId() != null && getId().equals(((Opdrachtnemer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opdrachtnemer{" +
            "id=" + getId() +
            ", clustercode='" + getClustercode() + "'" +
            ", clustercodeomschrijving='" + getClustercodeomschrijving() + "'" +
            ", naam='" + getNaam() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
