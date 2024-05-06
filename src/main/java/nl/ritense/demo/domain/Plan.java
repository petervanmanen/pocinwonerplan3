package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Plan.
 */
@Entity
@Table(name = "plan")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "zeventigprocentverkocht")
    private Boolean zeventigprocentverkocht;

    @Column(name = "aardgasloos")
    private Boolean aardgasloos;

    @Column(name = "bestemminggoedgekeurd")
    private Boolean bestemminggoedgekeurd;

    @Column(name = "eersteoplevering")
    private LocalDate eersteoplevering;

    @Column(name = "eigendomgemeente")
    private Boolean eigendomgemeente;

    @Column(name = "gebiedstransformatie")
    private Boolean gebiedstransformatie;

    @Column(name = "intentie")
    private Boolean intentie;

    @Column(name = "laatsteoplevering")
    private LocalDate laatsteoplevering;

    @Column(name = "naam")
    private String naam;

    @Column(name = "nummer")
    private String nummer;

    @Column(name = "onherroepelijk")
    private Boolean onherroepelijk;

    @Column(name = "percelen")
    private String percelen;

    @Column(name = "startbouw")
    private LocalDate startbouw;

    @Column(name = "startverkoop")
    private LocalDate startverkoop;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bestaatuitPlan")
    @JsonIgnoreProperties(value = { "bestaatuitPlan" }, allowSetters = true)
    private Set<Gebouw> bestaatuitGebouws = new HashSet<>();

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
    private Programma binnenprogrammaProgramma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isprojectleidervanPlans" }, allowSetters = true)
    private Projectleider isprojectleidervanProjectleider;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betrekkingopPlan")
    @JsonIgnoreProperties(value = { "betrekkingopPlan" }, allowSetters = true)
    private Set<Omgevingsvergunning> betrekkingopOmgevingsvergunnings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftPlans")
    @JsonIgnoreProperties(value = { "heeftPlans" }, allowSetters = true)
    private Set<Projectontwikkelaar> heeftProjectontwikkelaars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Plan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getZeventigprocentverkocht() {
        return this.zeventigprocentverkocht;
    }

    public Plan zeventigprocentverkocht(Boolean zeventigprocentverkocht) {
        this.setZeventigprocentverkocht(zeventigprocentverkocht);
        return this;
    }

    public void setZeventigprocentverkocht(Boolean zeventigprocentverkocht) {
        this.zeventigprocentverkocht = zeventigprocentverkocht;
    }

    public Boolean getAardgasloos() {
        return this.aardgasloos;
    }

    public Plan aardgasloos(Boolean aardgasloos) {
        this.setAardgasloos(aardgasloos);
        return this;
    }

    public void setAardgasloos(Boolean aardgasloos) {
        this.aardgasloos = aardgasloos;
    }

    public Boolean getBestemminggoedgekeurd() {
        return this.bestemminggoedgekeurd;
    }

    public Plan bestemminggoedgekeurd(Boolean bestemminggoedgekeurd) {
        this.setBestemminggoedgekeurd(bestemminggoedgekeurd);
        return this;
    }

    public void setBestemminggoedgekeurd(Boolean bestemminggoedgekeurd) {
        this.bestemminggoedgekeurd = bestemminggoedgekeurd;
    }

    public LocalDate getEersteoplevering() {
        return this.eersteoplevering;
    }

    public Plan eersteoplevering(LocalDate eersteoplevering) {
        this.setEersteoplevering(eersteoplevering);
        return this;
    }

    public void setEersteoplevering(LocalDate eersteoplevering) {
        this.eersteoplevering = eersteoplevering;
    }

    public Boolean getEigendomgemeente() {
        return this.eigendomgemeente;
    }

    public Plan eigendomgemeente(Boolean eigendomgemeente) {
        this.setEigendomgemeente(eigendomgemeente);
        return this;
    }

    public void setEigendomgemeente(Boolean eigendomgemeente) {
        this.eigendomgemeente = eigendomgemeente;
    }

    public Boolean getGebiedstransformatie() {
        return this.gebiedstransformatie;
    }

    public Plan gebiedstransformatie(Boolean gebiedstransformatie) {
        this.setGebiedstransformatie(gebiedstransformatie);
        return this;
    }

    public void setGebiedstransformatie(Boolean gebiedstransformatie) {
        this.gebiedstransformatie = gebiedstransformatie;
    }

    public Boolean getIntentie() {
        return this.intentie;
    }

    public Plan intentie(Boolean intentie) {
        this.setIntentie(intentie);
        return this;
    }

    public void setIntentie(Boolean intentie) {
        this.intentie = intentie;
    }

    public LocalDate getLaatsteoplevering() {
        return this.laatsteoplevering;
    }

    public Plan laatsteoplevering(LocalDate laatsteoplevering) {
        this.setLaatsteoplevering(laatsteoplevering);
        return this;
    }

    public void setLaatsteoplevering(LocalDate laatsteoplevering) {
        this.laatsteoplevering = laatsteoplevering;
    }

    public String getNaam() {
        return this.naam;
    }

    public Plan naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Plan nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public Boolean getOnherroepelijk() {
        return this.onherroepelijk;
    }

    public Plan onherroepelijk(Boolean onherroepelijk) {
        this.setOnherroepelijk(onherroepelijk);
        return this;
    }

    public void setOnherroepelijk(Boolean onherroepelijk) {
        this.onherroepelijk = onherroepelijk;
    }

    public String getPercelen() {
        return this.percelen;
    }

    public Plan percelen(String percelen) {
        this.setPercelen(percelen);
        return this;
    }

    public void setPercelen(String percelen) {
        this.percelen = percelen;
    }

    public LocalDate getStartbouw() {
        return this.startbouw;
    }

    public Plan startbouw(LocalDate startbouw) {
        this.setStartbouw(startbouw);
        return this;
    }

    public void setStartbouw(LocalDate startbouw) {
        this.startbouw = startbouw;
    }

    public LocalDate getStartverkoop() {
        return this.startverkoop;
    }

    public Plan startverkoop(LocalDate startverkoop) {
        this.setStartverkoop(startverkoop);
        return this;
    }

    public void setStartverkoop(LocalDate startverkoop) {
        this.startverkoop = startverkoop;
    }

    public Set<Gebouw> getBestaatuitGebouws() {
        return this.bestaatuitGebouws;
    }

    public void setBestaatuitGebouws(Set<Gebouw> gebouws) {
        if (this.bestaatuitGebouws != null) {
            this.bestaatuitGebouws.forEach(i -> i.setBestaatuitPlan(null));
        }
        if (gebouws != null) {
            gebouws.forEach(i -> i.setBestaatuitPlan(this));
        }
        this.bestaatuitGebouws = gebouws;
    }

    public Plan bestaatuitGebouws(Set<Gebouw> gebouws) {
        this.setBestaatuitGebouws(gebouws);
        return this;
    }

    public Plan addBestaatuitGebouw(Gebouw gebouw) {
        this.bestaatuitGebouws.add(gebouw);
        gebouw.setBestaatuitPlan(this);
        return this;
    }

    public Plan removeBestaatuitGebouw(Gebouw gebouw) {
        this.bestaatuitGebouws.remove(gebouw);
        gebouw.setBestaatuitPlan(null);
        return this;
    }

    public Programma getBinnenprogrammaProgramma() {
        return this.binnenprogrammaProgramma;
    }

    public void setBinnenprogrammaProgramma(Programma programma) {
        this.binnenprogrammaProgramma = programma;
    }

    public Plan binnenprogrammaProgramma(Programma programma) {
        this.setBinnenprogrammaProgramma(programma);
        return this;
    }

    public Projectleider getIsprojectleidervanProjectleider() {
        return this.isprojectleidervanProjectleider;
    }

    public void setIsprojectleidervanProjectleider(Projectleider projectleider) {
        this.isprojectleidervanProjectleider = projectleider;
    }

    public Plan isprojectleidervanProjectleider(Projectleider projectleider) {
        this.setIsprojectleidervanProjectleider(projectleider);
        return this;
    }

    public Set<Omgevingsvergunning> getBetrekkingopOmgevingsvergunnings() {
        return this.betrekkingopOmgevingsvergunnings;
    }

    public void setBetrekkingopOmgevingsvergunnings(Set<Omgevingsvergunning> omgevingsvergunnings) {
        if (this.betrekkingopOmgevingsvergunnings != null) {
            this.betrekkingopOmgevingsvergunnings.forEach(i -> i.setBetrekkingopPlan(null));
        }
        if (omgevingsvergunnings != null) {
            omgevingsvergunnings.forEach(i -> i.setBetrekkingopPlan(this));
        }
        this.betrekkingopOmgevingsvergunnings = omgevingsvergunnings;
    }

    public Plan betrekkingopOmgevingsvergunnings(Set<Omgevingsvergunning> omgevingsvergunnings) {
        this.setBetrekkingopOmgevingsvergunnings(omgevingsvergunnings);
        return this;
    }

    public Plan addBetrekkingopOmgevingsvergunning(Omgevingsvergunning omgevingsvergunning) {
        this.betrekkingopOmgevingsvergunnings.add(omgevingsvergunning);
        omgevingsvergunning.setBetrekkingopPlan(this);
        return this;
    }

    public Plan removeBetrekkingopOmgevingsvergunning(Omgevingsvergunning omgevingsvergunning) {
        this.betrekkingopOmgevingsvergunnings.remove(omgevingsvergunning);
        omgevingsvergunning.setBetrekkingopPlan(null);
        return this;
    }

    public Set<Projectontwikkelaar> getHeeftProjectontwikkelaars() {
        return this.heeftProjectontwikkelaars;
    }

    public void setHeeftProjectontwikkelaars(Set<Projectontwikkelaar> projectontwikkelaars) {
        if (this.heeftProjectontwikkelaars != null) {
            this.heeftProjectontwikkelaars.forEach(i -> i.removeHeeftPlan(this));
        }
        if (projectontwikkelaars != null) {
            projectontwikkelaars.forEach(i -> i.addHeeftPlan(this));
        }
        this.heeftProjectontwikkelaars = projectontwikkelaars;
    }

    public Plan heeftProjectontwikkelaars(Set<Projectontwikkelaar> projectontwikkelaars) {
        this.setHeeftProjectontwikkelaars(projectontwikkelaars);
        return this;
    }

    public Plan addHeeftProjectontwikkelaar(Projectontwikkelaar projectontwikkelaar) {
        this.heeftProjectontwikkelaars.add(projectontwikkelaar);
        projectontwikkelaar.getHeeftPlans().add(this);
        return this;
    }

    public Plan removeHeeftProjectontwikkelaar(Projectontwikkelaar projectontwikkelaar) {
        this.heeftProjectontwikkelaars.remove(projectontwikkelaar);
        projectontwikkelaar.getHeeftPlans().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plan)) {
            return false;
        }
        return getId() != null && getId().equals(((Plan) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plan{" +
            "id=" + getId() +
            ", zeventigprocentverkocht='" + getZeventigprocentverkocht() + "'" +
            ", aardgasloos='" + getAardgasloos() + "'" +
            ", bestemminggoedgekeurd='" + getBestemminggoedgekeurd() + "'" +
            ", eersteoplevering='" + getEersteoplevering() + "'" +
            ", eigendomgemeente='" + getEigendomgemeente() + "'" +
            ", gebiedstransformatie='" + getGebiedstransformatie() + "'" +
            ", intentie='" + getIntentie() + "'" +
            ", laatsteoplevering='" + getLaatsteoplevering() + "'" +
            ", naam='" + getNaam() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", onherroepelijk='" + getOnherroepelijk() + "'" +
            ", percelen='" + getPercelen() + "'" +
            ", startbouw='" + getStartbouw() + "'" +
            ", startverkoop='" + getStartverkoop() + "'" +
            "}";
    }
}
