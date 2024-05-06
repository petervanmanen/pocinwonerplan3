package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Regeling.
 */
@Entity
@Table(name = "regeling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Regeling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "datumtoekenning")
    private String datumtoekenning;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isregelingsoortRegelings" }, allowSetters = true)
    private Regelingsoort isregelingsoortRegelingsoort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftParticipatiedossier",
            "heeftvoorzieningInkomensvoorziening",
            "heeftScores",
            "leverttegenprestatieTegenprestaties",
            "heeftparticipatietrajectTrajects",
            "heeftfraudegegevensFraudegegevens",
            "heeftregelingRegelings",
            "heefttrajectTrajects",
            "valtbinnendoelgroepDoelgroep",
            "heeftrelatieRelaties",
            "voorzieningbijstandspartijInkomensvoorzienings",
            "maaktonderdeeluitvanHuishoudens",
            "heefttaalniveauTaalniveaus",
            "emptyBeschikkings",
            "prestatievoorLeverings",
            "betreftDeclaratieregels",
            "ondersteuntclientClientbegeleiders",
        },
        allowSetters = true
    )
    private Client heeftregelingClient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Regeling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Regeling datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Regeling datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getDatumtoekenning() {
        return this.datumtoekenning;
    }

    public Regeling datumtoekenning(String datumtoekenning) {
        this.setDatumtoekenning(datumtoekenning);
        return this;
    }

    public void setDatumtoekenning(String datumtoekenning) {
        this.datumtoekenning = datumtoekenning;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Regeling omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Regelingsoort getIsregelingsoortRegelingsoort() {
        return this.isregelingsoortRegelingsoort;
    }

    public void setIsregelingsoortRegelingsoort(Regelingsoort regelingsoort) {
        this.isregelingsoortRegelingsoort = regelingsoort;
    }

    public Regeling isregelingsoortRegelingsoort(Regelingsoort regelingsoort) {
        this.setIsregelingsoortRegelingsoort(regelingsoort);
        return this;
    }

    public Client getHeeftregelingClient() {
        return this.heeftregelingClient;
    }

    public void setHeeftregelingClient(Client client) {
        this.heeftregelingClient = client;
    }

    public Regeling heeftregelingClient(Client client) {
        this.setHeeftregelingClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Regeling)) {
            return false;
        }
        return getId() != null && getId().equals(((Regeling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Regeling{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumtoekenning='" + getDatumtoekenning() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
