package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Producttype.
 */
@Entity
@Table(name = "producttype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Producttype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "omschrijving")
    private String omschrijving;

    @JsonIgnoreProperties(
        value = {
            "heeftproductProducttype",
            "heeftKlantbeoordeling",
            "heeftHeffing",
            "heeftbetalingBetalings",
            "heeftStatuses",
            "betreftProject",
            "isvanZaaktype",
            "kentDocuments",
            "afhandelendmedewerkerMedewerkers",
            "leidttotVerzoek",
            "heeftSubsidie",
            "betreftAanbesteding",
            "heeftbetrekkingopBalieafspraaks",
            "isuitkomstvanBesluits",
            "heeftbetrekkingopKlantcontacts",
            "heeftGrondslags",
            "uitgevoerdbinnenBedrijfsproces",
            "oefentuitBetrokkenes",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftproductProducttype")
    private Zaak heeftproductZaak;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftProducttype")
    private Zaaktype heeftZaaktype;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "heeftProducttypes", "heeftZaaktypes", "isdeelvanDeelprocestype" }, allowSetters = true)
    private Bedrijfsprocestype heeftBedrijfsprocestype;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producttype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Producttype omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Zaak getHeeftproductZaak() {
        return this.heeftproductZaak;
    }

    public void setHeeftproductZaak(Zaak zaak) {
        if (this.heeftproductZaak != null) {
            this.heeftproductZaak.setHeeftproductProducttype(null);
        }
        if (zaak != null) {
            zaak.setHeeftproductProducttype(this);
        }
        this.heeftproductZaak = zaak;
    }

    public Producttype heeftproductZaak(Zaak zaak) {
        this.setHeeftproductZaak(zaak);
        return this;
    }

    public Zaaktype getHeeftZaaktype() {
        return this.heeftZaaktype;
    }

    public void setHeeftZaaktype(Zaaktype zaaktype) {
        if (this.heeftZaaktype != null) {
            this.heeftZaaktype.setHeeftProducttype(null);
        }
        if (zaaktype != null) {
            zaaktype.setHeeftProducttype(this);
        }
        this.heeftZaaktype = zaaktype;
    }

    public Producttype heeftZaaktype(Zaaktype zaaktype) {
        this.setHeeftZaaktype(zaaktype);
        return this;
    }

    public Bedrijfsprocestype getHeeftBedrijfsprocestype() {
        return this.heeftBedrijfsprocestype;
    }

    public void setHeeftBedrijfsprocestype(Bedrijfsprocestype bedrijfsprocestype) {
        this.heeftBedrijfsprocestype = bedrijfsprocestype;
    }

    public Producttype heeftBedrijfsprocestype(Bedrijfsprocestype bedrijfsprocestype) {
        this.setHeeftBedrijfsprocestype(bedrijfsprocestype);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producttype)) {
            return false;
        }
        return getId() != null && getId().equals(((Producttype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producttype{" +
            "id=" + getId() +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
