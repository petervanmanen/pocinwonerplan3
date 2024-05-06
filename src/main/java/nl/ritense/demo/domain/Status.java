package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Status.
 */
@Entity
@Table(name = "status")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumstatusgezet")
    private String datumstatusgezet;

    @Column(name = "indicatieiaatstgezettestatus")
    private String indicatieiaatstgezettestatus;

    @Column(name = "statustoelichting")
    private String statustoelichting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftZaaktype", "isvanStatuses" }, allowSetters = true)
    private Statustype isvanStatustype;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Zaak heeftZaak;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Status id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumstatusgezet() {
        return this.datumstatusgezet;
    }

    public Status datumstatusgezet(String datumstatusgezet) {
        this.setDatumstatusgezet(datumstatusgezet);
        return this;
    }

    public void setDatumstatusgezet(String datumstatusgezet) {
        this.datumstatusgezet = datumstatusgezet;
    }

    public String getIndicatieiaatstgezettestatus() {
        return this.indicatieiaatstgezettestatus;
    }

    public Status indicatieiaatstgezettestatus(String indicatieiaatstgezettestatus) {
        this.setIndicatieiaatstgezettestatus(indicatieiaatstgezettestatus);
        return this;
    }

    public void setIndicatieiaatstgezettestatus(String indicatieiaatstgezettestatus) {
        this.indicatieiaatstgezettestatus = indicatieiaatstgezettestatus;
    }

    public String getStatustoelichting() {
        return this.statustoelichting;
    }

    public Status statustoelichting(String statustoelichting) {
        this.setStatustoelichting(statustoelichting);
        return this;
    }

    public void setStatustoelichting(String statustoelichting) {
        this.statustoelichting = statustoelichting;
    }

    public Statustype getIsvanStatustype() {
        return this.isvanStatustype;
    }

    public void setIsvanStatustype(Statustype statustype) {
        this.isvanStatustype = statustype;
    }

    public Status isvanStatustype(Statustype statustype) {
        this.setIsvanStatustype(statustype);
        return this;
    }

    public Zaak getHeeftZaak() {
        return this.heeftZaak;
    }

    public void setHeeftZaak(Zaak zaak) {
        this.heeftZaak = zaak;
    }

    public Status heeftZaak(Zaak zaak) {
        this.setHeeftZaak(zaak);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return getId() != null && getId().equals(((Status) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", datumstatusgezet='" + getDatumstatusgezet() + "'" +
            ", indicatieiaatstgezettestatus='" + getIndicatieiaatstgezettestatus() + "'" +
            ", statustoelichting='" + getStatustoelichting() + "'" +
            "}";
    }
}
