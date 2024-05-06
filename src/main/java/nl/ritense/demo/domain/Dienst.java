package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Dienst.
 */
@Entity
@Table(name = "dienst")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dienst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftProducttype",
            "heeftHeffinggrondslags",
            "heeftStatustypes",
            "betreftProduct",
            "heeftBedrijfsprocestype",
            "isverantwoordelijkevoorMedewerker",
            "startDiensts",
            "isvanZaaks",
            "isaanleidingvoorFormuliersoorts",
        },
        allowSetters = true
    )
    private Zaaktype startZaaktype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "hoofdonderwerpOnderwerp", "heeftDiensts", "hoofdonderwerpOnderwerp2s" }, allowSetters = true)
    private Onderwerp heeftOnderwerp;

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

    public Dienst id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Zaaktype getStartZaaktype() {
        return this.startZaaktype;
    }

    public void setStartZaaktype(Zaaktype zaaktype) {
        this.startZaaktype = zaaktype;
    }

    public Dienst startZaaktype(Zaaktype zaaktype) {
        this.setStartZaaktype(zaaktype);
        return this;
    }

    public Onderwerp getHeeftOnderwerp() {
        return this.heeftOnderwerp;
    }

    public void setHeeftOnderwerp(Onderwerp onderwerp) {
        this.heeftOnderwerp = onderwerp;
    }

    public Dienst heeftOnderwerp(Onderwerp onderwerp) {
        this.setHeeftOnderwerp(onderwerp);
        return this;
    }

    public Product getBetreftProduct() {
        return this.betreftProduct;
    }

    public void setBetreftProduct(Product product) {
        this.betreftProduct = product;
    }

    public Dienst betreftProduct(Product product) {
        this.setBetreftProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dienst)) {
            return false;
        }
        return getId() != null && getId().equals(((Dienst) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dienst{" +
            "id=" + getId() +
            "}";
    }
}
