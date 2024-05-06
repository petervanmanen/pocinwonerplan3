package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Beslissing.
 */
@Entity
@Table(name = "beslissing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beslissing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Column(name = "reden")
    private String reden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftStartkwalificatie",
            "heeftVerzuimmeldings",
            "heeftVrijstellings",
            "heeftInschrijvings",
            "heeftOnderwijsloopbaans",
            "heeftUitschrijvings",
            "betreftBeslissings",
        },
        allowSetters = true
    )
    private Leerling betreftLeerling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "behandelaarBeslissings" }, allowSetters = true)
    private Leerplichtambtenaar behandelaarLeerplichtambtenaar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftUitschrijvings",
            "kentOnderwijsloopbaans",
            "heeftOnderwijssoorts",
            "gebruiktSportlocaties",
            "betreftBeslissings",
            "heeftVerzuimmeldings",
            "heeftVrijstellings",
            "heeftInschrijvings",
        },
        allowSetters = true
    )
    private School betreftSchool;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beslissing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Beslissing datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Beslissing opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getReden() {
        return this.reden;
    }

    public Beslissing reden(String reden) {
        this.setReden(reden);
        return this;
    }

    public void setReden(String reden) {
        this.reden = reden;
    }

    public Leerling getBetreftLeerling() {
        return this.betreftLeerling;
    }

    public void setBetreftLeerling(Leerling leerling) {
        this.betreftLeerling = leerling;
    }

    public Beslissing betreftLeerling(Leerling leerling) {
        this.setBetreftLeerling(leerling);
        return this;
    }

    public Leerplichtambtenaar getBehandelaarLeerplichtambtenaar() {
        return this.behandelaarLeerplichtambtenaar;
    }

    public void setBehandelaarLeerplichtambtenaar(Leerplichtambtenaar leerplichtambtenaar) {
        this.behandelaarLeerplichtambtenaar = leerplichtambtenaar;
    }

    public Beslissing behandelaarLeerplichtambtenaar(Leerplichtambtenaar leerplichtambtenaar) {
        this.setBehandelaarLeerplichtambtenaar(leerplichtambtenaar);
        return this;
    }

    public School getBetreftSchool() {
        return this.betreftSchool;
    }

    public void setBetreftSchool(School school) {
        this.betreftSchool = school;
    }

    public Beslissing betreftSchool(School school) {
        this.setBetreftSchool(school);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beslissing)) {
            return false;
        }
        return getId() != null && getId().equals(((Beslissing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beslissing{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", reden='" + getReden() + "'" +
            "}";
    }
}
