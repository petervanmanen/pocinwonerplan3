package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Werknemer.
 */
@Entity
@Table(name = "werknemer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Werknemer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "geboortedatum")
    private LocalDate geboortedatum;

    @Column(name = "naam")
    private String naam;

    @Column(name = "voornaam")
    private String voornaam;

    @Column(name = "woonplaats")
    private String woonplaats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "beoordeeltdoorWerknemer")
    @JsonIgnoreProperties(value = { "beoordeeltdoorWerknemer", "beoordelingvanWerknemer" }, allowSetters = true)
    private Set<Beoordeling> beoordeeltdoorBeoordelings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "beoordelingvanWerknemer")
    @JsonIgnoreProperties(value = { "beoordeeltdoorWerknemer", "beoordelingvanWerknemer" }, allowSetters = true)
    private Set<Beoordeling> beoordelingvanBeoordelings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dientinWerknemer")
    @JsonIgnoreProperties(
        value = { "ingedienddoorLeverancier", "soortdeclaratieDeclaratiesoort", "dientinWerknemer", "valtbinnenDeclaratieregels" },
        allowSetters = true
    )
    private Set<Declaratie> dientinDeclaraties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medewerkerheeftdienstverbandWerknemer")
    @JsonIgnoreProperties(
        value = {
            "dienstverbandconformfunctieFunctie",
            "aantalvolgensinzetUren",
            "medewerkerheeftdienstverbandWerknemer",
            "aantalvolgensinzetInzet",
        },
        allowSetters = true
    )
    private Set<Dienstverband> medewerkerheeftdienstverbandDienstverbands = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solliciteertWerknemer")
    @JsonIgnoreProperties(
        value = { "opvacatureVacature", "solliciteertopfunctieSollicitant", "solliciteertWerknemer", "inkadervanSollicitatiegespreks" },
        allowSetters = true
    )
    private Set<Sollicitatie> solliciteertSollicitaties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftverlofWerknemer")
    @JsonIgnoreProperties(value = { "soortverlofVerlofsoort", "heeftverlofWerknemer" }, allowSetters = true)
    private Set<Verlof> heeftverlofVerlofs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftverzuimWerknemer")
    @JsonIgnoreProperties(value = { "soortverzuimVerzuimsoort", "heeftverzuimWerknemer" }, allowSetters = true)
    private Set<Verzuim> heeftverzuimVerzuims = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftondergaanWerknemers" }, allowSetters = true)
    private Geweldsincident heeftondergaanGeweldsincident;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_werknemer__heeft_rol",
        joinColumns = @JoinColumn(name = "werknemer_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_rol_id")
    )
    @JsonIgnoreProperties(value = { "heeftWerknemers" }, allowSetters = true)
    private Set<Rol> heeftRols = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "doetsollicitatiegesprekWerknemers")
    @JsonIgnoreProperties(
        value = { "inkadervanSollicitatie", "kandidaatSollicitants", "doetsollicitatiegesprekWerknemers" },
        allowSetters = true
    )
    private Set<Sollicitatiegesprek> doetsollicitatiegesprekSollicitatiegespreks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Werknemer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getGeboortedatum() {
        return this.geboortedatum;
    }

    public Werknemer geboortedatum(LocalDate geboortedatum) {
        this.setGeboortedatum(geboortedatum);
        return this;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getNaam() {
        return this.naam;
    }

    public Werknemer naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return this.voornaam;
    }

    public Werknemer voornaam(String voornaam) {
        this.setVoornaam(voornaam);
        return this;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getWoonplaats() {
        return this.woonplaats;
    }

    public Werknemer woonplaats(String woonplaats) {
        this.setWoonplaats(woonplaats);
        return this;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Set<Beoordeling> getBeoordeeltdoorBeoordelings() {
        return this.beoordeeltdoorBeoordelings;
    }

    public void setBeoordeeltdoorBeoordelings(Set<Beoordeling> beoordelings) {
        if (this.beoordeeltdoorBeoordelings != null) {
            this.beoordeeltdoorBeoordelings.forEach(i -> i.setBeoordeeltdoorWerknemer(null));
        }
        if (beoordelings != null) {
            beoordelings.forEach(i -> i.setBeoordeeltdoorWerknemer(this));
        }
        this.beoordeeltdoorBeoordelings = beoordelings;
    }

    public Werknemer beoordeeltdoorBeoordelings(Set<Beoordeling> beoordelings) {
        this.setBeoordeeltdoorBeoordelings(beoordelings);
        return this;
    }

    public Werknemer addBeoordeeltdoorBeoordeling(Beoordeling beoordeling) {
        this.beoordeeltdoorBeoordelings.add(beoordeling);
        beoordeling.setBeoordeeltdoorWerknemer(this);
        return this;
    }

    public Werknemer removeBeoordeeltdoorBeoordeling(Beoordeling beoordeling) {
        this.beoordeeltdoorBeoordelings.remove(beoordeling);
        beoordeling.setBeoordeeltdoorWerknemer(null);
        return this;
    }

    public Set<Beoordeling> getBeoordelingvanBeoordelings() {
        return this.beoordelingvanBeoordelings;
    }

    public void setBeoordelingvanBeoordelings(Set<Beoordeling> beoordelings) {
        if (this.beoordelingvanBeoordelings != null) {
            this.beoordelingvanBeoordelings.forEach(i -> i.setBeoordelingvanWerknemer(null));
        }
        if (beoordelings != null) {
            beoordelings.forEach(i -> i.setBeoordelingvanWerknemer(this));
        }
        this.beoordelingvanBeoordelings = beoordelings;
    }

    public Werknemer beoordelingvanBeoordelings(Set<Beoordeling> beoordelings) {
        this.setBeoordelingvanBeoordelings(beoordelings);
        return this;
    }

    public Werknemer addBeoordelingvanBeoordeling(Beoordeling beoordeling) {
        this.beoordelingvanBeoordelings.add(beoordeling);
        beoordeling.setBeoordelingvanWerknemer(this);
        return this;
    }

    public Werknemer removeBeoordelingvanBeoordeling(Beoordeling beoordeling) {
        this.beoordelingvanBeoordelings.remove(beoordeling);
        beoordeling.setBeoordelingvanWerknemer(null);
        return this;
    }

    public Set<Declaratie> getDientinDeclaraties() {
        return this.dientinDeclaraties;
    }

    public void setDientinDeclaraties(Set<Declaratie> declaraties) {
        if (this.dientinDeclaraties != null) {
            this.dientinDeclaraties.forEach(i -> i.setDientinWerknemer(null));
        }
        if (declaraties != null) {
            declaraties.forEach(i -> i.setDientinWerknemer(this));
        }
        this.dientinDeclaraties = declaraties;
    }

    public Werknemer dientinDeclaraties(Set<Declaratie> declaraties) {
        this.setDientinDeclaraties(declaraties);
        return this;
    }

    public Werknemer addDientinDeclaratie(Declaratie declaratie) {
        this.dientinDeclaraties.add(declaratie);
        declaratie.setDientinWerknemer(this);
        return this;
    }

    public Werknemer removeDientinDeclaratie(Declaratie declaratie) {
        this.dientinDeclaraties.remove(declaratie);
        declaratie.setDientinWerknemer(null);
        return this;
    }

    public Set<Dienstverband> getMedewerkerheeftdienstverbandDienstverbands() {
        return this.medewerkerheeftdienstverbandDienstverbands;
    }

    public void setMedewerkerheeftdienstverbandDienstverbands(Set<Dienstverband> dienstverbands) {
        if (this.medewerkerheeftdienstverbandDienstverbands != null) {
            this.medewerkerheeftdienstverbandDienstverbands.forEach(i -> i.setMedewerkerheeftdienstverbandWerknemer(null));
        }
        if (dienstverbands != null) {
            dienstverbands.forEach(i -> i.setMedewerkerheeftdienstverbandWerknemer(this));
        }
        this.medewerkerheeftdienstverbandDienstverbands = dienstverbands;
    }

    public Werknemer medewerkerheeftdienstverbandDienstverbands(Set<Dienstverband> dienstverbands) {
        this.setMedewerkerheeftdienstverbandDienstverbands(dienstverbands);
        return this;
    }

    public Werknemer addMedewerkerheeftdienstverbandDienstverband(Dienstverband dienstverband) {
        this.medewerkerheeftdienstverbandDienstverbands.add(dienstverband);
        dienstverband.setMedewerkerheeftdienstverbandWerknemer(this);
        return this;
    }

    public Werknemer removeMedewerkerheeftdienstverbandDienstverband(Dienstverband dienstverband) {
        this.medewerkerheeftdienstverbandDienstverbands.remove(dienstverband);
        dienstverband.setMedewerkerheeftdienstverbandWerknemer(null);
        return this;
    }

    public Set<Sollicitatie> getSolliciteertSollicitaties() {
        return this.solliciteertSollicitaties;
    }

    public void setSolliciteertSollicitaties(Set<Sollicitatie> sollicitaties) {
        if (this.solliciteertSollicitaties != null) {
            this.solliciteertSollicitaties.forEach(i -> i.setSolliciteertWerknemer(null));
        }
        if (sollicitaties != null) {
            sollicitaties.forEach(i -> i.setSolliciteertWerknemer(this));
        }
        this.solliciteertSollicitaties = sollicitaties;
    }

    public Werknemer solliciteertSollicitaties(Set<Sollicitatie> sollicitaties) {
        this.setSolliciteertSollicitaties(sollicitaties);
        return this;
    }

    public Werknemer addSolliciteertSollicitatie(Sollicitatie sollicitatie) {
        this.solliciteertSollicitaties.add(sollicitatie);
        sollicitatie.setSolliciteertWerknemer(this);
        return this;
    }

    public Werknemer removeSolliciteertSollicitatie(Sollicitatie sollicitatie) {
        this.solliciteertSollicitaties.remove(sollicitatie);
        sollicitatie.setSolliciteertWerknemer(null);
        return this;
    }

    public Set<Verlof> getHeeftverlofVerlofs() {
        return this.heeftverlofVerlofs;
    }

    public void setHeeftverlofVerlofs(Set<Verlof> verlofs) {
        if (this.heeftverlofVerlofs != null) {
            this.heeftverlofVerlofs.forEach(i -> i.setHeeftverlofWerknemer(null));
        }
        if (verlofs != null) {
            verlofs.forEach(i -> i.setHeeftverlofWerknemer(this));
        }
        this.heeftverlofVerlofs = verlofs;
    }

    public Werknemer heeftverlofVerlofs(Set<Verlof> verlofs) {
        this.setHeeftverlofVerlofs(verlofs);
        return this;
    }

    public Werknemer addHeeftverlofVerlof(Verlof verlof) {
        this.heeftverlofVerlofs.add(verlof);
        verlof.setHeeftverlofWerknemer(this);
        return this;
    }

    public Werknemer removeHeeftverlofVerlof(Verlof verlof) {
        this.heeftverlofVerlofs.remove(verlof);
        verlof.setHeeftverlofWerknemer(null);
        return this;
    }

    public Set<Verzuim> getHeeftverzuimVerzuims() {
        return this.heeftverzuimVerzuims;
    }

    public void setHeeftverzuimVerzuims(Set<Verzuim> verzuims) {
        if (this.heeftverzuimVerzuims != null) {
            this.heeftverzuimVerzuims.forEach(i -> i.setHeeftverzuimWerknemer(null));
        }
        if (verzuims != null) {
            verzuims.forEach(i -> i.setHeeftverzuimWerknemer(this));
        }
        this.heeftverzuimVerzuims = verzuims;
    }

    public Werknemer heeftverzuimVerzuims(Set<Verzuim> verzuims) {
        this.setHeeftverzuimVerzuims(verzuims);
        return this;
    }

    public Werknemer addHeeftverzuimVerzuim(Verzuim verzuim) {
        this.heeftverzuimVerzuims.add(verzuim);
        verzuim.setHeeftverzuimWerknemer(this);
        return this;
    }

    public Werknemer removeHeeftverzuimVerzuim(Verzuim verzuim) {
        this.heeftverzuimVerzuims.remove(verzuim);
        verzuim.setHeeftverzuimWerknemer(null);
        return this;
    }

    public Geweldsincident getHeeftondergaanGeweldsincident() {
        return this.heeftondergaanGeweldsincident;
    }

    public void setHeeftondergaanGeweldsincident(Geweldsincident geweldsincident) {
        this.heeftondergaanGeweldsincident = geweldsincident;
    }

    public Werknemer heeftondergaanGeweldsincident(Geweldsincident geweldsincident) {
        this.setHeeftondergaanGeweldsincident(geweldsincident);
        return this;
    }

    public Set<Rol> getHeeftRols() {
        return this.heeftRols;
    }

    public void setHeeftRols(Set<Rol> rols) {
        this.heeftRols = rols;
    }

    public Werknemer heeftRols(Set<Rol> rols) {
        this.setHeeftRols(rols);
        return this;
    }

    public Werknemer addHeeftRol(Rol rol) {
        this.heeftRols.add(rol);
        return this;
    }

    public Werknemer removeHeeftRol(Rol rol) {
        this.heeftRols.remove(rol);
        return this;
    }

    public Set<Sollicitatiegesprek> getDoetsollicitatiegesprekSollicitatiegespreks() {
        return this.doetsollicitatiegesprekSollicitatiegespreks;
    }

    public void setDoetsollicitatiegesprekSollicitatiegespreks(Set<Sollicitatiegesprek> sollicitatiegespreks) {
        if (this.doetsollicitatiegesprekSollicitatiegespreks != null) {
            this.doetsollicitatiegesprekSollicitatiegespreks.forEach(i -> i.removeDoetsollicitatiegesprekWerknemer(this));
        }
        if (sollicitatiegespreks != null) {
            sollicitatiegespreks.forEach(i -> i.addDoetsollicitatiegesprekWerknemer(this));
        }
        this.doetsollicitatiegesprekSollicitatiegespreks = sollicitatiegespreks;
    }

    public Werknemer doetsollicitatiegesprekSollicitatiegespreks(Set<Sollicitatiegesprek> sollicitatiegespreks) {
        this.setDoetsollicitatiegesprekSollicitatiegespreks(sollicitatiegespreks);
        return this;
    }

    public Werknemer addDoetsollicitatiegesprekSollicitatiegesprek(Sollicitatiegesprek sollicitatiegesprek) {
        this.doetsollicitatiegesprekSollicitatiegespreks.add(sollicitatiegesprek);
        sollicitatiegesprek.getDoetsollicitatiegesprekWerknemers().add(this);
        return this;
    }

    public Werknemer removeDoetsollicitatiegesprekSollicitatiegesprek(Sollicitatiegesprek sollicitatiegesprek) {
        this.doetsollicitatiegesprekSollicitatiegespreks.remove(sollicitatiegesprek);
        sollicitatiegesprek.getDoetsollicitatiegesprekWerknemers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Werknemer)) {
            return false;
        }
        return getId() != null && getId().equals(((Werknemer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Werknemer{" +
            "id=" + getId() +
            ", geboortedatum='" + getGeboortedatum() + "'" +
            ", naam='" + getNaam() + "'" +
            ", voornaam='" + getVoornaam() + "'" +
            ", woonplaats='" + getWoonplaats() + "'" +
            "}";
    }
}
