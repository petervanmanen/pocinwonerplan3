package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Winkelvoorraaditem.
 */
@Entity
@Table(name = "winkelvoorraaditem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Winkelvoorraaditem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantal")
    private String aantal;

    @Column(name = "aantalinbestelling")
    private String aantalinbestelling;

    @Column(name = "datumleveringbestelling")
    private LocalDate datumleveringbestelling;

    @Column(name = "locatie")
    private String locatie;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Product betreftProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Winkelvoorraaditem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Winkelvoorraaditem aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public String getAantalinbestelling() {
        return this.aantalinbestelling;
    }

    public Winkelvoorraaditem aantalinbestelling(String aantalinbestelling) {
        this.setAantalinbestelling(aantalinbestelling);
        return this;
    }

    public void setAantalinbestelling(String aantalinbestelling) {
        this.aantalinbestelling = aantalinbestelling;
    }

    public LocalDate getDatumleveringbestelling() {
        return this.datumleveringbestelling;
    }

    public Winkelvoorraaditem datumleveringbestelling(LocalDate datumleveringbestelling) {
        this.setDatumleveringbestelling(datumleveringbestelling);
        return this;
    }

    public void setDatumleveringbestelling(LocalDate datumleveringbestelling) {
        this.datumleveringbestelling = datumleveringbestelling;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Winkelvoorraaditem locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Product getBetreftProduct() {
        return this.betreftProduct;
    }

    public void setBetreftProduct(Product product) {
        this.betreftProduct = product;
    }

    public Winkelvoorraaditem betreftProduct(Product product) {
        this.setBetreftProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Winkelvoorraaditem)) {
            return false;
        }
        return getId() != null && getId().equals(((Winkelvoorraaditem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Winkelvoorraaditem{" +
            "id=" + getId() +
            ", aantal='" + getAantal() + "'" +
            ", aantalinbestelling='" + getAantalinbestelling() + "'" +
            ", datumleveringbestelling='" + getDatumleveringbestelling() + "'" +
            ", locatie='" + getLocatie() + "'" +
            "}";
    }
}
