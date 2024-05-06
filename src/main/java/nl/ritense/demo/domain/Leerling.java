package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Leerling.
 */
@Entity
@Table(name = "leerling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Leerling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "kwetsbarejongere")
    private Boolean kwetsbarejongere;

    @JsonIgnoreProperties(value = { "heeftLeerling" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Startkwalificatie heeftStartkwalificatie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLeerling")
    @JsonIgnoreProperties(value = { "heeftSchool", "heeftLeerling" }, allowSetters = true)
    private Set<Verzuimmelding> heeftVerzuimmeldings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLeerling")
    @JsonIgnoreProperties(value = { "heeftSchool", "heeftLeerling" }, allowSetters = true)
    private Set<Vrijstelling> heeftVrijstellings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLeerling")
    @JsonIgnoreProperties(
        value = { "heeftSchool", "betreftAanbesteding", "betreftGunning", "heeftLeerling", "heeftLeverancier" },
        allowSetters = true
    )
    private Set<Inschrijving> heeftInschrijvings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLeerling")
    @JsonIgnoreProperties(value = { "emptyLoopbaanstaps", "heeftLeerling", "kentSchools" }, allowSetters = true)
    private Set<Onderwijsloopbaan> heeftOnderwijsloopbaans = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLeerling")
    @JsonIgnoreProperties(value = { "heeftLeerling", "heeftSchool" }, allowSetters = true)
    private Set<Uitschrijving> heeftUitschrijvings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftLeerling")
    @JsonIgnoreProperties(value = { "betreftLeerling", "behandelaarLeerplichtambtenaar", "betreftSchool" }, allowSetters = true)
    private Set<Beslissing> betreftBeslissings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Leerling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getKwetsbarejongere() {
        return this.kwetsbarejongere;
    }

    public Leerling kwetsbarejongere(Boolean kwetsbarejongere) {
        this.setKwetsbarejongere(kwetsbarejongere);
        return this;
    }

    public void setKwetsbarejongere(Boolean kwetsbarejongere) {
        this.kwetsbarejongere = kwetsbarejongere;
    }

    public Startkwalificatie getHeeftStartkwalificatie() {
        return this.heeftStartkwalificatie;
    }

    public void setHeeftStartkwalificatie(Startkwalificatie startkwalificatie) {
        this.heeftStartkwalificatie = startkwalificatie;
    }

    public Leerling heeftStartkwalificatie(Startkwalificatie startkwalificatie) {
        this.setHeeftStartkwalificatie(startkwalificatie);
        return this;
    }

    public Set<Verzuimmelding> getHeeftVerzuimmeldings() {
        return this.heeftVerzuimmeldings;
    }

    public void setHeeftVerzuimmeldings(Set<Verzuimmelding> verzuimmeldings) {
        if (this.heeftVerzuimmeldings != null) {
            this.heeftVerzuimmeldings.forEach(i -> i.setHeeftLeerling(null));
        }
        if (verzuimmeldings != null) {
            verzuimmeldings.forEach(i -> i.setHeeftLeerling(this));
        }
        this.heeftVerzuimmeldings = verzuimmeldings;
    }

    public Leerling heeftVerzuimmeldings(Set<Verzuimmelding> verzuimmeldings) {
        this.setHeeftVerzuimmeldings(verzuimmeldings);
        return this;
    }

    public Leerling addHeeftVerzuimmelding(Verzuimmelding verzuimmelding) {
        this.heeftVerzuimmeldings.add(verzuimmelding);
        verzuimmelding.setHeeftLeerling(this);
        return this;
    }

    public Leerling removeHeeftVerzuimmelding(Verzuimmelding verzuimmelding) {
        this.heeftVerzuimmeldings.remove(verzuimmelding);
        verzuimmelding.setHeeftLeerling(null);
        return this;
    }

    public Set<Vrijstelling> getHeeftVrijstellings() {
        return this.heeftVrijstellings;
    }

    public void setHeeftVrijstellings(Set<Vrijstelling> vrijstellings) {
        if (this.heeftVrijstellings != null) {
            this.heeftVrijstellings.forEach(i -> i.setHeeftLeerling(null));
        }
        if (vrijstellings != null) {
            vrijstellings.forEach(i -> i.setHeeftLeerling(this));
        }
        this.heeftVrijstellings = vrijstellings;
    }

    public Leerling heeftVrijstellings(Set<Vrijstelling> vrijstellings) {
        this.setHeeftVrijstellings(vrijstellings);
        return this;
    }

    public Leerling addHeeftVrijstelling(Vrijstelling vrijstelling) {
        this.heeftVrijstellings.add(vrijstelling);
        vrijstelling.setHeeftLeerling(this);
        return this;
    }

    public Leerling removeHeeftVrijstelling(Vrijstelling vrijstelling) {
        this.heeftVrijstellings.remove(vrijstelling);
        vrijstelling.setHeeftLeerling(null);
        return this;
    }

    public Set<Inschrijving> getHeeftInschrijvings() {
        return this.heeftInschrijvings;
    }

    public void setHeeftInschrijvings(Set<Inschrijving> inschrijvings) {
        if (this.heeftInschrijvings != null) {
            this.heeftInschrijvings.forEach(i -> i.setHeeftLeerling(null));
        }
        if (inschrijvings != null) {
            inschrijvings.forEach(i -> i.setHeeftLeerling(this));
        }
        this.heeftInschrijvings = inschrijvings;
    }

    public Leerling heeftInschrijvings(Set<Inschrijving> inschrijvings) {
        this.setHeeftInschrijvings(inschrijvings);
        return this;
    }

    public Leerling addHeeftInschrijving(Inschrijving inschrijving) {
        this.heeftInschrijvings.add(inschrijving);
        inschrijving.setHeeftLeerling(this);
        return this;
    }

    public Leerling removeHeeftInschrijving(Inschrijving inschrijving) {
        this.heeftInschrijvings.remove(inschrijving);
        inschrijving.setHeeftLeerling(null);
        return this;
    }

    public Set<Onderwijsloopbaan> getHeeftOnderwijsloopbaans() {
        return this.heeftOnderwijsloopbaans;
    }

    public void setHeeftOnderwijsloopbaans(Set<Onderwijsloopbaan> onderwijsloopbaans) {
        if (this.heeftOnderwijsloopbaans != null) {
            this.heeftOnderwijsloopbaans.forEach(i -> i.setHeeftLeerling(null));
        }
        if (onderwijsloopbaans != null) {
            onderwijsloopbaans.forEach(i -> i.setHeeftLeerling(this));
        }
        this.heeftOnderwijsloopbaans = onderwijsloopbaans;
    }

    public Leerling heeftOnderwijsloopbaans(Set<Onderwijsloopbaan> onderwijsloopbaans) {
        this.setHeeftOnderwijsloopbaans(onderwijsloopbaans);
        return this;
    }

    public Leerling addHeeftOnderwijsloopbaan(Onderwijsloopbaan onderwijsloopbaan) {
        this.heeftOnderwijsloopbaans.add(onderwijsloopbaan);
        onderwijsloopbaan.setHeeftLeerling(this);
        return this;
    }

    public Leerling removeHeeftOnderwijsloopbaan(Onderwijsloopbaan onderwijsloopbaan) {
        this.heeftOnderwijsloopbaans.remove(onderwijsloopbaan);
        onderwijsloopbaan.setHeeftLeerling(null);
        return this;
    }

    public Set<Uitschrijving> getHeeftUitschrijvings() {
        return this.heeftUitschrijvings;
    }

    public void setHeeftUitschrijvings(Set<Uitschrijving> uitschrijvings) {
        if (this.heeftUitschrijvings != null) {
            this.heeftUitschrijvings.forEach(i -> i.setHeeftLeerling(null));
        }
        if (uitschrijvings != null) {
            uitschrijvings.forEach(i -> i.setHeeftLeerling(this));
        }
        this.heeftUitschrijvings = uitschrijvings;
    }

    public Leerling heeftUitschrijvings(Set<Uitschrijving> uitschrijvings) {
        this.setHeeftUitschrijvings(uitschrijvings);
        return this;
    }

    public Leerling addHeeftUitschrijving(Uitschrijving uitschrijving) {
        this.heeftUitschrijvings.add(uitschrijving);
        uitschrijving.setHeeftLeerling(this);
        return this;
    }

    public Leerling removeHeeftUitschrijving(Uitschrijving uitschrijving) {
        this.heeftUitschrijvings.remove(uitschrijving);
        uitschrijving.setHeeftLeerling(null);
        return this;
    }

    public Set<Beslissing> getBetreftBeslissings() {
        return this.betreftBeslissings;
    }

    public void setBetreftBeslissings(Set<Beslissing> beslissings) {
        if (this.betreftBeslissings != null) {
            this.betreftBeslissings.forEach(i -> i.setBetreftLeerling(null));
        }
        if (beslissings != null) {
            beslissings.forEach(i -> i.setBetreftLeerling(this));
        }
        this.betreftBeslissings = beslissings;
    }

    public Leerling betreftBeslissings(Set<Beslissing> beslissings) {
        this.setBetreftBeslissings(beslissings);
        return this;
    }

    public Leerling addBetreftBeslissing(Beslissing beslissing) {
        this.betreftBeslissings.add(beslissing);
        beslissing.setBetreftLeerling(this);
        return this;
    }

    public Leerling removeBetreftBeslissing(Beslissing beslissing) {
        this.betreftBeslissings.remove(beslissing);
        beslissing.setBetreftLeerling(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leerling)) {
            return false;
        }
        return getId() != null && getId().equals(((Leerling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leerling{" +
            "id=" + getId() +
            ", kwetsbarejongere='" + getKwetsbarejongere() + "'" +
            "}";
    }
}
