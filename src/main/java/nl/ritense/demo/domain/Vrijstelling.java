package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Vrijstelling.
 */
@Entity
@Table(name = "vrijstelling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vrijstelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanvraagtoegekend")
    private Boolean aanvraagtoegekend;

    @Column(name = "buitenlandseschoollocatie")
    private String buitenlandseschoollocatie;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "verzuimsoort")
    private String verzuimsoort;

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

    public Vrijstelling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAanvraagtoegekend() {
        return this.aanvraagtoegekend;
    }

    public Vrijstelling aanvraagtoegekend(Boolean aanvraagtoegekend) {
        this.setAanvraagtoegekend(aanvraagtoegekend);
        return this;
    }

    public void setAanvraagtoegekend(Boolean aanvraagtoegekend) {
        this.aanvraagtoegekend = aanvraagtoegekend;
    }

    public String getBuitenlandseschoollocatie() {
        return this.buitenlandseschoollocatie;
    }

    public Vrijstelling buitenlandseschoollocatie(String buitenlandseschoollocatie) {
        this.setBuitenlandseschoollocatie(buitenlandseschoollocatie);
        return this;
    }

    public void setBuitenlandseschoollocatie(String buitenlandseschoollocatie) {
        this.buitenlandseschoollocatie = buitenlandseschoollocatie;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Vrijstelling datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Vrijstelling datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getVerzuimsoort() {
        return this.verzuimsoort;
    }

    public Vrijstelling verzuimsoort(String verzuimsoort) {
        this.setVerzuimsoort(verzuimsoort);
        return this;
    }

    public void setVerzuimsoort(String verzuimsoort) {
        this.verzuimsoort = verzuimsoort;
    }

    public School getHeeftSchool() {
        return this.heeftSchool;
    }

    public void setHeeftSchool(School school) {
        this.heeftSchool = school;
    }

    public Vrijstelling heeftSchool(School school) {
        this.setHeeftSchool(school);
        return this;
    }

    public Leerling getHeeftLeerling() {
        return this.heeftLeerling;
    }

    public void setHeeftLeerling(Leerling leerling) {
        this.heeftLeerling = leerling;
    }

    public Vrijstelling heeftLeerling(Leerling leerling) {
        this.setHeeftLeerling(leerling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vrijstelling)) {
            return false;
        }
        return getId() != null && getId().equals(((Vrijstelling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vrijstelling{" +
            "id=" + getId() +
            ", aanvraagtoegekend='" + getAanvraagtoegekend() + "'" +
            ", buitenlandseschoollocatie='" + getBuitenlandseschoollocatie() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", verzuimsoort='" + getVerzuimsoort() + "'" +
            "}";
    }
}
