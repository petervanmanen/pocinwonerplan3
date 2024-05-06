package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Programma.
 */
@Entity
@Table(name = "programma")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Programma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bestaatuitProgramma")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "binnenprogrammaProgramma")
    @JsonIgnoreProperties(
        value = {
            "bestaatuitGebouws",
            "binnenprogrammaProgramma",
            "isprojectleidervanProjectleider",
            "betrekkingopOmgevingsvergunnings",
            "heeftProjectontwikkelaars",
        },
        allowSetters = true
    )
    private Set<Plan> binnenprogrammaPlans = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftVastgoedobjects",
            "heeftWerkorders",
            "heeftSubrekenings",
            "heeftInkooporders",
            "heeftTaakvelds",
            "heeftProgrammas",
            "heeftSubsidies",
            "heeftSubsidiecomponents",
            "betreftBegrotingregels",
            "schrijftopFactuurs",
            "heeftbetrekkingopMutaties",
            "heeftProducts",
            "heeftHoofdrekenings",
            "heeftProjects",
        },
        allowSetters = true
    )
    private Kostenplaats heeftKostenplaats;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_programma__voor_programmasoort",
        joinColumns = @JoinColumn(name = "programma_id"),
        inverseJoinColumns = @JoinColumn(name = "voor_programmasoort_id")
    )
    @JsonIgnoreProperties(value = { "voorProgrammas" }, allowSetters = true)
    private Set<Programmasoort> voorProgrammasoorts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "voorProgrammas", "valtbinnenDoelgroeps", "versturenaanMailings" }, allowSetters = true)
    private Museumrelatie voorMuseumrelatie;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "hoortbijProgrammas")
    @JsonIgnoreProperties(
        value = {
            "heeftTaakveld",
            "behandeltAgendapunts",
            "hoortbijProgrammas",
            "wordtbehandeldinVergaderings",
            "heeftverslagVergadering",
            "betreftStemming",
            "heeftCategorie",
            "hoortbijDossiers",
            "heeftIndieners",
        },
        allowSetters = true
    )
    private Set<Raadsstuk> hoortbijRaadsstuks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Programma id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Programma naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Activiteit> getBestaatuitActiviteits() {
        return this.bestaatuitActiviteits;
    }

    public void setBestaatuitActiviteits(Set<Activiteit> activiteits) {
        if (this.bestaatuitActiviteits != null) {
            this.bestaatuitActiviteits.forEach(i -> i.setBestaatuitProgramma(null));
        }
        if (activiteits != null) {
            activiteits.forEach(i -> i.setBestaatuitProgramma(this));
        }
        this.bestaatuitActiviteits = activiteits;
    }

    public Programma bestaatuitActiviteits(Set<Activiteit> activiteits) {
        this.setBestaatuitActiviteits(activiteits);
        return this;
    }

    public Programma addBestaatuitActiviteit(Activiteit activiteit) {
        this.bestaatuitActiviteits.add(activiteit);
        activiteit.setBestaatuitProgramma(this);
        return this;
    }

    public Programma removeBestaatuitActiviteit(Activiteit activiteit) {
        this.bestaatuitActiviteits.remove(activiteit);
        activiteit.setBestaatuitProgramma(null);
        return this;
    }

    public Set<Plan> getBinnenprogrammaPlans() {
        return this.binnenprogrammaPlans;
    }

    public void setBinnenprogrammaPlans(Set<Plan> plans) {
        if (this.binnenprogrammaPlans != null) {
            this.binnenprogrammaPlans.forEach(i -> i.setBinnenprogrammaProgramma(null));
        }
        if (plans != null) {
            plans.forEach(i -> i.setBinnenprogrammaProgramma(this));
        }
        this.binnenprogrammaPlans = plans;
    }

    public Programma binnenprogrammaPlans(Set<Plan> plans) {
        this.setBinnenprogrammaPlans(plans);
        return this;
    }

    public Programma addBinnenprogrammaPlan(Plan plan) {
        this.binnenprogrammaPlans.add(plan);
        plan.setBinnenprogrammaProgramma(this);
        return this;
    }

    public Programma removeBinnenprogrammaPlan(Plan plan) {
        this.binnenprogrammaPlans.remove(plan);
        plan.setBinnenprogrammaProgramma(null);
        return this;
    }

    public Kostenplaats getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats = kostenplaats;
    }

    public Programma heeftKostenplaats(Kostenplaats kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    public Set<Programmasoort> getVoorProgrammasoorts() {
        return this.voorProgrammasoorts;
    }

    public void setVoorProgrammasoorts(Set<Programmasoort> programmasoorts) {
        this.voorProgrammasoorts = programmasoorts;
    }

    public Programma voorProgrammasoorts(Set<Programmasoort> programmasoorts) {
        this.setVoorProgrammasoorts(programmasoorts);
        return this;
    }

    public Programma addVoorProgrammasoort(Programmasoort programmasoort) {
        this.voorProgrammasoorts.add(programmasoort);
        return this;
    }

    public Programma removeVoorProgrammasoort(Programmasoort programmasoort) {
        this.voorProgrammasoorts.remove(programmasoort);
        return this;
    }

    public Museumrelatie getVoorMuseumrelatie() {
        return this.voorMuseumrelatie;
    }

    public void setVoorMuseumrelatie(Museumrelatie museumrelatie) {
        this.voorMuseumrelatie = museumrelatie;
    }

    public Programma voorMuseumrelatie(Museumrelatie museumrelatie) {
        this.setVoorMuseumrelatie(museumrelatie);
        return this;
    }

    public Set<Raadsstuk> getHoortbijRaadsstuks() {
        return this.hoortbijRaadsstuks;
    }

    public void setHoortbijRaadsstuks(Set<Raadsstuk> raadsstuks) {
        if (this.hoortbijRaadsstuks != null) {
            this.hoortbijRaadsstuks.forEach(i -> i.removeHoortbijProgramma(this));
        }
        if (raadsstuks != null) {
            raadsstuks.forEach(i -> i.addHoortbijProgramma(this));
        }
        this.hoortbijRaadsstuks = raadsstuks;
    }

    public Programma hoortbijRaadsstuks(Set<Raadsstuk> raadsstuks) {
        this.setHoortbijRaadsstuks(raadsstuks);
        return this;
    }

    public Programma addHoortbijRaadsstuk(Raadsstuk raadsstuk) {
        this.hoortbijRaadsstuks.add(raadsstuk);
        raadsstuk.getHoortbijProgrammas().add(this);
        return this;
    }

    public Programma removeHoortbijRaadsstuk(Raadsstuk raadsstuk) {
        this.hoortbijRaadsstuks.remove(raadsstuk);
        raadsstuk.getHoortbijProgrammas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Programma)) {
            return false;
        }
        return getId() != null && getId().equals(((Programma) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Programma{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
