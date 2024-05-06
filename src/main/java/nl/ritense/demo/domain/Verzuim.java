package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Verzuim.
 */
@Entity
@Table(name = "verzuim")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verzuim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumtijdeinde")
    private String datumtijdeinde;

    @Column(name = "datumtijdstart")
    private String datumtijdstart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortverzuimVerzuims" }, allowSetters = true)
    private Verzuimsoort soortverzuimVerzuimsoort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "beoordeeltdoorBeoordelings",
            "beoordelingvanBeoordelings",
            "dientinDeclaraties",
            "medewerkerheeftdienstverbandDienstverbands",
            "solliciteertSollicitaties",
            "heeftverlofVerlofs",
            "heeftverzuimVerzuims",
            "heeftondergaanGeweldsincident",
            "heeftRols",
            "doetsollicitatiegesprekSollicitatiegespreks",
        },
        allowSetters = true
    )
    private Werknemer heeftverzuimWerknemer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verzuim id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumtijdeinde() {
        return this.datumtijdeinde;
    }

    public Verzuim datumtijdeinde(String datumtijdeinde) {
        this.setDatumtijdeinde(datumtijdeinde);
        return this;
    }

    public void setDatumtijdeinde(String datumtijdeinde) {
        this.datumtijdeinde = datumtijdeinde;
    }

    public String getDatumtijdstart() {
        return this.datumtijdstart;
    }

    public Verzuim datumtijdstart(String datumtijdstart) {
        this.setDatumtijdstart(datumtijdstart);
        return this;
    }

    public void setDatumtijdstart(String datumtijdstart) {
        this.datumtijdstart = datumtijdstart;
    }

    public Verzuimsoort getSoortverzuimVerzuimsoort() {
        return this.soortverzuimVerzuimsoort;
    }

    public void setSoortverzuimVerzuimsoort(Verzuimsoort verzuimsoort) {
        this.soortverzuimVerzuimsoort = verzuimsoort;
    }

    public Verzuim soortverzuimVerzuimsoort(Verzuimsoort verzuimsoort) {
        this.setSoortverzuimVerzuimsoort(verzuimsoort);
        return this;
    }

    public Werknemer getHeeftverzuimWerknemer() {
        return this.heeftverzuimWerknemer;
    }

    public void setHeeftverzuimWerknemer(Werknemer werknemer) {
        this.heeftverzuimWerknemer = werknemer;
    }

    public Verzuim heeftverzuimWerknemer(Werknemer werknemer) {
        this.setHeeftverzuimWerknemer(werknemer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verzuim)) {
            return false;
        }
        return getId() != null && getId().equals(((Verzuim) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verzuim{" +
            "id=" + getId() +
            ", datumtijdeinde='" + getDatumtijdeinde() + "'" +
            ", datumtijdstart='" + getDatumtijdstart() + "'" +
            "}";
    }
}
