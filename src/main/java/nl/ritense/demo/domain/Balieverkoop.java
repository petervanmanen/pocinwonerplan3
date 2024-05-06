package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Balieverkoop.
 */
@Entity
@Table(name = "balieverkoop")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Balieverkoop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantal")
    private String aantal;

    @Column(name = "kanaal")
    private String kanaal;

    @Column(name = "verkooptijd")
    private String verkooptijd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftprijsProduct", "tegenprijsBalieverkoops" }, allowSetters = true)
    private Prijs tegenprijsPrijs;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Product betreftProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Balieverkoop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Balieverkoop aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public String getKanaal() {
        return this.kanaal;
    }

    public Balieverkoop kanaal(String kanaal) {
        this.setKanaal(kanaal);
        return this;
    }

    public void setKanaal(String kanaal) {
        this.kanaal = kanaal;
    }

    public String getVerkooptijd() {
        return this.verkooptijd;
    }

    public Balieverkoop verkooptijd(String verkooptijd) {
        this.setVerkooptijd(verkooptijd);
        return this;
    }

    public void setVerkooptijd(String verkooptijd) {
        this.verkooptijd = verkooptijd;
    }

    public Prijs getTegenprijsPrijs() {
        return this.tegenprijsPrijs;
    }

    public void setTegenprijsPrijs(Prijs prijs) {
        this.tegenprijsPrijs = prijs;
    }

    public Balieverkoop tegenprijsPrijs(Prijs prijs) {
        this.setTegenprijsPrijs(prijs);
        return this;
    }

    public Product getBetreftProduct() {
        return this.betreftProduct;
    }

    public void setBetreftProduct(Product product) {
        this.betreftProduct = product;
    }

    public Balieverkoop betreftProduct(Product product) {
        this.setBetreftProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Balieverkoop)) {
            return false;
        }
        return getId() != null && getId().equals(((Balieverkoop) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Balieverkoop{" +
            "id=" + getId() +
            ", aantal='" + getAantal() + "'" +
            ", kanaal='" + getKanaal() + "'" +
            ", verkooptijd='" + getVerkooptijd() + "'" +
            "}";
    }
}
