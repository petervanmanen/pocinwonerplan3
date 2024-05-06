package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Verzuimmelding.
 */
@Entity
@Table(name = "verzuimmelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verzuimmelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "voorstelschool")
    private String voorstelschool;

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
    private School heeftSchool;

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
    private Leerling heeftLeerling;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verzuimmelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Verzuimmelding datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Verzuimmelding datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getVoorstelschool() {
        return this.voorstelschool;
    }

    public Verzuimmelding voorstelschool(String voorstelschool) {
        this.setVoorstelschool(voorstelschool);
        return this;
    }

    public void setVoorstelschool(String voorstelschool) {
        this.voorstelschool = voorstelschool;
    }

    public School getHeeftSchool() {
        return this.heeftSchool;
    }

    public void setHeeftSchool(School school) {
        this.heeftSchool = school;
    }

    public Verzuimmelding heeftSchool(School school) {
        this.setHeeftSchool(school);
        return this;
    }

    public Leerling getHeeftLeerling() {
        return this.heeftLeerling;
    }

    public void setHeeftLeerling(Leerling leerling) {
        this.heeftLeerling = leerling;
    }

    public Verzuimmelding heeftLeerling(Leerling leerling) {
        this.setHeeftLeerling(leerling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verzuimmelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Verzuimmelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verzuimmelding{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", voorstelschool='" + getVoorstelschool() + "'" +
            "}";
    }
}
