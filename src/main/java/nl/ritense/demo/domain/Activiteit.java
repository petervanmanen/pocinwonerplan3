package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Activiteit.
 */
@Entity
@Table(name = "activiteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activiteit implements Serializable {

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
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Activiteit gerelateerdeactiviteitActiviteit;

    @JsonIgnoreProperties(
        value = {
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Activiteit bovenliggendeactiviteitActiviteit;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bestaatuitActiviteit2")
    @JsonIgnoreProperties(
        value = {
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    private Set<Activiteit> bestaatuitActiviteits = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftActiviteit")
    @JsonIgnoreProperties(value = { "betreftVoorziening", "betreftZaal", "heeftActiviteit" }, allowSetters = true)
    private Set<Reservering> heeftReserverings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "voorTentoonstelling", "heeftActiviteits" }, allowSetters = true)
    private Rondleiding heeftRondleiding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vansoortActiviteits" }, allowSetters = true)
    private Activiteitsoort vansoortActiviteitsoort;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_activiteit__isverbondenmet_locatie",
        joinColumns = @JoinColumn(name = "activiteit_id"),
        inverseJoinColumns = @JoinColumn(name = "isverbondenmet_locatie_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftContainers",
            "betreftMeldings",
            "gestoptopOphaalmoments",
            "betreftProjectlocaties",
            "heeftlocatiePuts",
            "wordtbegrensddoorProjects",
            "betreftVerzoeks",
            "werkingsgebiedRegelteksts",
            "isverbondenmetActiviteits",
            "verwijstnaarGebiedsaanwijzings",
            "geldtvoorNormwaardes",
        },
        allowSetters = true
    )
    private Set<Locatie> isverbondenmetLocaties = new HashSet<>();

    @JsonIgnoreProperties(
        value = {
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "gerelateerdeactiviteitActiviteit")
    private Activiteit gerelateerdeactiviteitActiviteit2;

    @JsonIgnoreProperties(
        value = {
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bovenliggendeactiviteitActiviteit")
    private Activiteit bovenliggendeactiviteitActiviteit2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    private Activiteit bestaatuitActiviteit2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "bestaatuitActiviteits",
            "binnenprogrammaPlans",
            "heeftKostenplaats",
            "voorProgrammasoorts",
            "voorMuseumrelatie",
            "hoortbijRaadsstuks",
        },
        allowSetters = true
    )
    private Programma bestaatuitProgramma;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betreftActiviteits")
    @JsonIgnoreProperties(
        value = {
            "leidttotZaak",
            "bevatSpecificaties",
            "betrefteerderverzoekVerzoek",
            "betreftProjectactiviteits",
            "betreftProjectlocaties",
            "betreftActiviteits",
            "betreftLocaties",
            "heeftalsverantwoordelijkeInitiatiefnemer",
            "betrefteerderverzoekVerzoek2s",
        },
        allowSetters = true
    )
    private Set<Verzoek> betreftVerzoeks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Activiteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Activiteit omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Activiteit getGerelateerdeactiviteitActiviteit() {
        return this.gerelateerdeactiviteitActiviteit;
    }

    public void setGerelateerdeactiviteitActiviteit(Activiteit activiteit) {
        this.gerelateerdeactiviteitActiviteit = activiteit;
    }

    public Activiteit gerelateerdeactiviteitActiviteit(Activiteit activiteit) {
        this.setGerelateerdeactiviteitActiviteit(activiteit);
        return this;
    }

    public Activiteit getBovenliggendeactiviteitActiviteit() {
        return this.bovenliggendeactiviteitActiviteit;
    }

    public void setBovenliggendeactiviteitActiviteit(Activiteit activiteit) {
        this.bovenliggendeactiviteitActiviteit = activiteit;
    }

    public Activiteit bovenliggendeactiviteitActiviteit(Activiteit activiteit) {
        this.setBovenliggendeactiviteitActiviteit(activiteit);
        return this;
    }

    public Set<Activiteit> getBestaatuitActiviteits() {
        return this.bestaatuitActiviteits;
    }

    public void setBestaatuitActiviteits(Set<Activiteit> activiteits) {
        if (this.bestaatuitActiviteits != null) {
            this.bestaatuitActiviteits.forEach(i -> i.setBestaatuitActiviteit2(null));
        }
        if (activiteits != null) {
            activiteits.forEach(i -> i.setBestaatuitActiviteit2(this));
        }
        this.bestaatuitActiviteits = activiteits;
    }

    public Activiteit bestaatuitActiviteits(Set<Activiteit> activiteits) {
        this.setBestaatuitActiviteits(activiteits);
        return this;
    }

    public Activiteit addBestaatuitActiviteit(Activiteit activiteit) {
        this.bestaatuitActiviteits.add(activiteit);
        activiteit.setBestaatuitActiviteit2(this);
        return this;
    }

    public Activiteit removeBestaatuitActiviteit(Activiteit activiteit) {
        this.bestaatuitActiviteits.remove(activiteit);
        activiteit.setBestaatuitActiviteit2(null);
        return this;
    }

    public Set<Reservering> getHeeftReserverings() {
        return this.heeftReserverings;
    }

    public void setHeeftReserverings(Set<Reservering> reserverings) {
        if (this.heeftReserverings != null) {
            this.heeftReserverings.forEach(i -> i.setHeeftActiviteit(null));
        }
        if (reserverings != null) {
            reserverings.forEach(i -> i.setHeeftActiviteit(this));
        }
        this.heeftReserverings = reserverings;
    }

    public Activiteit heeftReserverings(Set<Reservering> reserverings) {
        this.setHeeftReserverings(reserverings);
        return this;
    }

    public Activiteit addHeeftReservering(Reservering reservering) {
        this.heeftReserverings.add(reservering);
        reservering.setHeeftActiviteit(this);
        return this;
    }

    public Activiteit removeHeeftReservering(Reservering reservering) {
        this.heeftReserverings.remove(reservering);
        reservering.setHeeftActiviteit(null);
        return this;
    }

    public Rondleiding getHeeftRondleiding() {
        return this.heeftRondleiding;
    }

    public void setHeeftRondleiding(Rondleiding rondleiding) {
        this.heeftRondleiding = rondleiding;
    }

    public Activiteit heeftRondleiding(Rondleiding rondleiding) {
        this.setHeeftRondleiding(rondleiding);
        return this;
    }

    public Activiteitsoort getVansoortActiviteitsoort() {
        return this.vansoortActiviteitsoort;
    }

    public void setVansoortActiviteitsoort(Activiteitsoort activiteitsoort) {
        this.vansoortActiviteitsoort = activiteitsoort;
    }

    public Activiteit vansoortActiviteitsoort(Activiteitsoort activiteitsoort) {
        this.setVansoortActiviteitsoort(activiteitsoort);
        return this;
    }

    public Set<Locatie> getIsverbondenmetLocaties() {
        return this.isverbondenmetLocaties;
    }

    public void setIsverbondenmetLocaties(Set<Locatie> locaties) {
        this.isverbondenmetLocaties = locaties;
    }

    public Activiteit isverbondenmetLocaties(Set<Locatie> locaties) {
        this.setIsverbondenmetLocaties(locaties);
        return this;
    }

    public Activiteit addIsverbondenmetLocatie(Locatie locatie) {
        this.isverbondenmetLocaties.add(locatie);
        return this;
    }

    public Activiteit removeIsverbondenmetLocatie(Locatie locatie) {
        this.isverbondenmetLocaties.remove(locatie);
        return this;
    }

    public Activiteit getGerelateerdeactiviteitActiviteit2() {
        return this.gerelateerdeactiviteitActiviteit2;
    }

    public void setGerelateerdeactiviteitActiviteit2(Activiteit activiteit) {
        if (this.gerelateerdeactiviteitActiviteit2 != null) {
            this.gerelateerdeactiviteitActiviteit2.setGerelateerdeactiviteitActiviteit(null);
        }
        if (activiteit != null) {
            activiteit.setGerelateerdeactiviteitActiviteit(this);
        }
        this.gerelateerdeactiviteitActiviteit2 = activiteit;
    }

    public Activiteit gerelateerdeactiviteitActiviteit2(Activiteit activiteit) {
        this.setGerelateerdeactiviteitActiviteit2(activiteit);
        return this;
    }

    public Activiteit getBovenliggendeactiviteitActiviteit2() {
        return this.bovenliggendeactiviteitActiviteit2;
    }

    public void setBovenliggendeactiviteitActiviteit2(Activiteit activiteit) {
        if (this.bovenliggendeactiviteitActiviteit2 != null) {
            this.bovenliggendeactiviteitActiviteit2.setBovenliggendeactiviteitActiviteit(null);
        }
        if (activiteit != null) {
            activiteit.setBovenliggendeactiviteitActiviteit(this);
        }
        this.bovenliggendeactiviteitActiviteit2 = activiteit;
    }

    public Activiteit bovenliggendeactiviteitActiviteit2(Activiteit activiteit) {
        this.setBovenliggendeactiviteitActiviteit2(activiteit);
        return this;
    }

    public Activiteit getBestaatuitActiviteit2() {
        return this.bestaatuitActiviteit2;
    }

    public void setBestaatuitActiviteit2(Activiteit activiteit) {
        this.bestaatuitActiviteit2 = activiteit;
    }

    public Activiteit bestaatuitActiviteit2(Activiteit activiteit) {
        this.setBestaatuitActiviteit2(activiteit);
        return this;
    }

    public Programma getBestaatuitProgramma() {
        return this.bestaatuitProgramma;
    }

    public void setBestaatuitProgramma(Programma programma) {
        this.bestaatuitProgramma = programma;
    }

    public Activiteit bestaatuitProgramma(Programma programma) {
        this.setBestaatuitProgramma(programma);
        return this;
    }

    public Set<Verzoek> getBetreftVerzoeks() {
        return this.betreftVerzoeks;
    }

    public void setBetreftVerzoeks(Set<Verzoek> verzoeks) {
        if (this.betreftVerzoeks != null) {
            this.betreftVerzoeks.forEach(i -> i.removeBetreftActiviteit(this));
        }
        if (verzoeks != null) {
            verzoeks.forEach(i -> i.addBetreftActiviteit(this));
        }
        this.betreftVerzoeks = verzoeks;
    }

    public Activiteit betreftVerzoeks(Set<Verzoek> verzoeks) {
        this.setBetreftVerzoeks(verzoeks);
        return this;
    }

    public Activiteit addBetreftVerzoek(Verzoek verzoek) {
        this.betreftVerzoeks.add(verzoek);
        verzoek.getBetreftActiviteits().add(this);
        return this;
    }

    public Activiteit removeBetreftVerzoek(Verzoek verzoek) {
        this.betreftVerzoeks.remove(verzoek);
        verzoek.getBetreftActiviteits().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activiteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Activiteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activiteit{" +
            "id=" + getId() +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
