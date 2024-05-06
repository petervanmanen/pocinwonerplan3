package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Locatie.
 */
@Entity
@Table(name = "locatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Locatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adres")
    private String adres;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftLocatie")
    @JsonIgnoreProperties(
        value = { "heeftVulgraadmetings", "geschiktvoorFractie", "soortContainertype", "heeftLocatie", "gelostOphaalmoments" },
        allowSetters = true
    )
    private Set<Container> heeftContainers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftLocatie")
    @JsonIgnoreProperties(
        value = {
            "hoofdcategorieCategorie",
            "subcategorieCategorie",
            "betreftContainertype",
            "betreftFractie",
            "betreftLocatie",
            "melderMedewerker",
            "uitvoerderLeverancier",
            "uitvoerderMedewerker",
            "betreftBeheerobjects",
            "heeftSchouwronde",
        },
        allowSetters = true
    )
    private Set<Melding> betreftMeldings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gestoptopLocatie")
    @JsonIgnoreProperties(value = { "gelostContainer", "gestoptopLocatie", "heeftRit" }, allowSetters = true)
    private Set<Ophaalmoment> gestoptopOphaalmoments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftLocatie")
    @JsonIgnoreProperties(value = { "betreftLocatie", "heeftProject", "betreftVerzoeks" }, allowSetters = true)
    private Set<Projectlocatie> betreftProjectlocaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftlocatieLocaties")
    @JsonIgnoreProperties(value = { "heeftVlaks", "heeftlocatieLocaties", "heeftProject" }, allowSetters = true)
    private Set<Put> heeftlocatiePuts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "wordtbegrensddoorLocaties")
    @JsonIgnoreProperties(
        value = {
            "heeftuitstroomredenUitstroomreden",
            "heeftresultaatResultaat",
            "heeftArcheologiebesluits",
            "heeftBorings",
            "heeftPuts",
            "heeftProjectlocaties",
            "heeftProjectactiviteits",
            "soortprojectProjectsoort",
            "wordtbegrensddoorLocaties",
            "heeftKostenplaats",
            "hoortbijVindplaats",
            "heeftprojectTraject",
            "betreftZaaks",
        },
        allowSetters = true
    )
    private Set<Project> wordtbegrensddoorProjects = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betreftLocaties")
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "werkingsgebiedLocaties")
    @JsonIgnoreProperties(
        value = {
            "werkingsgebiedRegelteksts",
            "isgerelateerdRegelteksts",
            "heeftthemaThemas",
            "heeftidealisatieIdealisaties",
            "werkingsgebiedLocaties",
            "bevatOmgevingsdocument",
            "werkingsgebiedRegeltekst2",
            "isgerelateerdRegeltekst2",
        },
        allowSetters = true
    )
    private Set<Regeltekst> werkingsgebiedRegelteksts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "isverbondenmetLocaties")
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
    private Set<Activiteit> isverbondenmetActiviteits = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "verwijstnaarLocaties")
    @JsonIgnoreProperties(value = { "verwijstnaarLocaties", "beschrijftgebiedsaanwijzingInstructieregels" }, allowSetters = true)
    private Set<Gebiedsaanwijzing> verwijstnaarGebiedsaanwijzings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "geldtvoorLocaties")
    @JsonIgnoreProperties(value = { "geldtvoorLocaties", "bevatNorm" }, allowSetters = true)
    private Set<Normwaarde> geldtvoorNormwaardes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Locatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdres() {
        return this.adres;
    }

    public Locatie adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Set<Container> getHeeftContainers() {
        return this.heeftContainers;
    }

    public void setHeeftContainers(Set<Container> containers) {
        if (this.heeftContainers != null) {
            this.heeftContainers.forEach(i -> i.setHeeftLocatie(null));
        }
        if (containers != null) {
            containers.forEach(i -> i.setHeeftLocatie(this));
        }
        this.heeftContainers = containers;
    }

    public Locatie heeftContainers(Set<Container> containers) {
        this.setHeeftContainers(containers);
        return this;
    }

    public Locatie addHeeftContainer(Container container) {
        this.heeftContainers.add(container);
        container.setHeeftLocatie(this);
        return this;
    }

    public Locatie removeHeeftContainer(Container container) {
        this.heeftContainers.remove(container);
        container.setHeeftLocatie(null);
        return this;
    }

    public Set<Melding> getBetreftMeldings() {
        return this.betreftMeldings;
    }

    public void setBetreftMeldings(Set<Melding> meldings) {
        if (this.betreftMeldings != null) {
            this.betreftMeldings.forEach(i -> i.setBetreftLocatie(null));
        }
        if (meldings != null) {
            meldings.forEach(i -> i.setBetreftLocatie(this));
        }
        this.betreftMeldings = meldings;
    }

    public Locatie betreftMeldings(Set<Melding> meldings) {
        this.setBetreftMeldings(meldings);
        return this;
    }

    public Locatie addBetreftMelding(Melding melding) {
        this.betreftMeldings.add(melding);
        melding.setBetreftLocatie(this);
        return this;
    }

    public Locatie removeBetreftMelding(Melding melding) {
        this.betreftMeldings.remove(melding);
        melding.setBetreftLocatie(null);
        return this;
    }

    public Set<Ophaalmoment> getGestoptopOphaalmoments() {
        return this.gestoptopOphaalmoments;
    }

    public void setGestoptopOphaalmoments(Set<Ophaalmoment> ophaalmoments) {
        if (this.gestoptopOphaalmoments != null) {
            this.gestoptopOphaalmoments.forEach(i -> i.setGestoptopLocatie(null));
        }
        if (ophaalmoments != null) {
            ophaalmoments.forEach(i -> i.setGestoptopLocatie(this));
        }
        this.gestoptopOphaalmoments = ophaalmoments;
    }

    public Locatie gestoptopOphaalmoments(Set<Ophaalmoment> ophaalmoments) {
        this.setGestoptopOphaalmoments(ophaalmoments);
        return this;
    }

    public Locatie addGestoptopOphaalmoment(Ophaalmoment ophaalmoment) {
        this.gestoptopOphaalmoments.add(ophaalmoment);
        ophaalmoment.setGestoptopLocatie(this);
        return this;
    }

    public Locatie removeGestoptopOphaalmoment(Ophaalmoment ophaalmoment) {
        this.gestoptopOphaalmoments.remove(ophaalmoment);
        ophaalmoment.setGestoptopLocatie(null);
        return this;
    }

    public Set<Projectlocatie> getBetreftProjectlocaties() {
        return this.betreftProjectlocaties;
    }

    public void setBetreftProjectlocaties(Set<Projectlocatie> projectlocaties) {
        if (this.betreftProjectlocaties != null) {
            this.betreftProjectlocaties.forEach(i -> i.setBetreftLocatie(null));
        }
        if (projectlocaties != null) {
            projectlocaties.forEach(i -> i.setBetreftLocatie(this));
        }
        this.betreftProjectlocaties = projectlocaties;
    }

    public Locatie betreftProjectlocaties(Set<Projectlocatie> projectlocaties) {
        this.setBetreftProjectlocaties(projectlocaties);
        return this;
    }

    public Locatie addBetreftProjectlocatie(Projectlocatie projectlocatie) {
        this.betreftProjectlocaties.add(projectlocatie);
        projectlocatie.setBetreftLocatie(this);
        return this;
    }

    public Locatie removeBetreftProjectlocatie(Projectlocatie projectlocatie) {
        this.betreftProjectlocaties.remove(projectlocatie);
        projectlocatie.setBetreftLocatie(null);
        return this;
    }

    public Set<Put> getHeeftlocatiePuts() {
        return this.heeftlocatiePuts;
    }

    public void setHeeftlocatiePuts(Set<Put> puts) {
        if (this.heeftlocatiePuts != null) {
            this.heeftlocatiePuts.forEach(i -> i.removeHeeftlocatieLocatie(this));
        }
        if (puts != null) {
            puts.forEach(i -> i.addHeeftlocatieLocatie(this));
        }
        this.heeftlocatiePuts = puts;
    }

    public Locatie heeftlocatiePuts(Set<Put> puts) {
        this.setHeeftlocatiePuts(puts);
        return this;
    }

    public Locatie addHeeftlocatiePut(Put put) {
        this.heeftlocatiePuts.add(put);
        put.getHeeftlocatieLocaties().add(this);
        return this;
    }

    public Locatie removeHeeftlocatiePut(Put put) {
        this.heeftlocatiePuts.remove(put);
        put.getHeeftlocatieLocaties().remove(this);
        return this;
    }

    public Set<Project> getWordtbegrensddoorProjects() {
        return this.wordtbegrensddoorProjects;
    }

    public void setWordtbegrensddoorProjects(Set<Project> projects) {
        if (this.wordtbegrensddoorProjects != null) {
            this.wordtbegrensddoorProjects.forEach(i -> i.removeWordtbegrensddoorLocatie(this));
        }
        if (projects != null) {
            projects.forEach(i -> i.addWordtbegrensddoorLocatie(this));
        }
        this.wordtbegrensddoorProjects = projects;
    }

    public Locatie wordtbegrensddoorProjects(Set<Project> projects) {
        this.setWordtbegrensddoorProjects(projects);
        return this;
    }

    public Locatie addWordtbegrensddoorProject(Project project) {
        this.wordtbegrensddoorProjects.add(project);
        project.getWordtbegrensddoorLocaties().add(this);
        return this;
    }

    public Locatie removeWordtbegrensddoorProject(Project project) {
        this.wordtbegrensddoorProjects.remove(project);
        project.getWordtbegrensddoorLocaties().remove(this);
        return this;
    }

    public Set<Verzoek> getBetreftVerzoeks() {
        return this.betreftVerzoeks;
    }

    public void setBetreftVerzoeks(Set<Verzoek> verzoeks) {
        if (this.betreftVerzoeks != null) {
            this.betreftVerzoeks.forEach(i -> i.removeBetreftLocatie(this));
        }
        if (verzoeks != null) {
            verzoeks.forEach(i -> i.addBetreftLocatie(this));
        }
        this.betreftVerzoeks = verzoeks;
    }

    public Locatie betreftVerzoeks(Set<Verzoek> verzoeks) {
        this.setBetreftVerzoeks(verzoeks);
        return this;
    }

    public Locatie addBetreftVerzoek(Verzoek verzoek) {
        this.betreftVerzoeks.add(verzoek);
        verzoek.getBetreftLocaties().add(this);
        return this;
    }

    public Locatie removeBetreftVerzoek(Verzoek verzoek) {
        this.betreftVerzoeks.remove(verzoek);
        verzoek.getBetreftLocaties().remove(this);
        return this;
    }

    public Set<Regeltekst> getWerkingsgebiedRegelteksts() {
        return this.werkingsgebiedRegelteksts;
    }

    public void setWerkingsgebiedRegelteksts(Set<Regeltekst> regelteksts) {
        if (this.werkingsgebiedRegelteksts != null) {
            this.werkingsgebiedRegelteksts.forEach(i -> i.removeWerkingsgebiedLocatie(this));
        }
        if (regelteksts != null) {
            regelteksts.forEach(i -> i.addWerkingsgebiedLocatie(this));
        }
        this.werkingsgebiedRegelteksts = regelteksts;
    }

    public Locatie werkingsgebiedRegelteksts(Set<Regeltekst> regelteksts) {
        this.setWerkingsgebiedRegelteksts(regelteksts);
        return this;
    }

    public Locatie addWerkingsgebiedRegeltekst(Regeltekst regeltekst) {
        this.werkingsgebiedRegelteksts.add(regeltekst);
        regeltekst.getWerkingsgebiedLocaties().add(this);
        return this;
    }

    public Locatie removeWerkingsgebiedRegeltekst(Regeltekst regeltekst) {
        this.werkingsgebiedRegelteksts.remove(regeltekst);
        regeltekst.getWerkingsgebiedLocaties().remove(this);
        return this;
    }

    public Set<Activiteit> getIsverbondenmetActiviteits() {
        return this.isverbondenmetActiviteits;
    }

    public void setIsverbondenmetActiviteits(Set<Activiteit> activiteits) {
        if (this.isverbondenmetActiviteits != null) {
            this.isverbondenmetActiviteits.forEach(i -> i.removeIsverbondenmetLocatie(this));
        }
        if (activiteits != null) {
            activiteits.forEach(i -> i.addIsverbondenmetLocatie(this));
        }
        this.isverbondenmetActiviteits = activiteits;
    }

    public Locatie isverbondenmetActiviteits(Set<Activiteit> activiteits) {
        this.setIsverbondenmetActiviteits(activiteits);
        return this;
    }

    public Locatie addIsverbondenmetActiviteit(Activiteit activiteit) {
        this.isverbondenmetActiviteits.add(activiteit);
        activiteit.getIsverbondenmetLocaties().add(this);
        return this;
    }

    public Locatie removeIsverbondenmetActiviteit(Activiteit activiteit) {
        this.isverbondenmetActiviteits.remove(activiteit);
        activiteit.getIsverbondenmetLocaties().remove(this);
        return this;
    }

    public Set<Gebiedsaanwijzing> getVerwijstnaarGebiedsaanwijzings() {
        return this.verwijstnaarGebiedsaanwijzings;
    }

    public void setVerwijstnaarGebiedsaanwijzings(Set<Gebiedsaanwijzing> gebiedsaanwijzings) {
        if (this.verwijstnaarGebiedsaanwijzings != null) {
            this.verwijstnaarGebiedsaanwijzings.forEach(i -> i.removeVerwijstnaarLocatie(this));
        }
        if (gebiedsaanwijzings != null) {
            gebiedsaanwijzings.forEach(i -> i.addVerwijstnaarLocatie(this));
        }
        this.verwijstnaarGebiedsaanwijzings = gebiedsaanwijzings;
    }

    public Locatie verwijstnaarGebiedsaanwijzings(Set<Gebiedsaanwijzing> gebiedsaanwijzings) {
        this.setVerwijstnaarGebiedsaanwijzings(gebiedsaanwijzings);
        return this;
    }

    public Locatie addVerwijstnaarGebiedsaanwijzing(Gebiedsaanwijzing gebiedsaanwijzing) {
        this.verwijstnaarGebiedsaanwijzings.add(gebiedsaanwijzing);
        gebiedsaanwijzing.getVerwijstnaarLocaties().add(this);
        return this;
    }

    public Locatie removeVerwijstnaarGebiedsaanwijzing(Gebiedsaanwijzing gebiedsaanwijzing) {
        this.verwijstnaarGebiedsaanwijzings.remove(gebiedsaanwijzing);
        gebiedsaanwijzing.getVerwijstnaarLocaties().remove(this);
        return this;
    }

    public Set<Normwaarde> getGeldtvoorNormwaardes() {
        return this.geldtvoorNormwaardes;
    }

    public void setGeldtvoorNormwaardes(Set<Normwaarde> normwaardes) {
        if (this.geldtvoorNormwaardes != null) {
            this.geldtvoorNormwaardes.forEach(i -> i.removeGeldtvoorLocatie(this));
        }
        if (normwaardes != null) {
            normwaardes.forEach(i -> i.addGeldtvoorLocatie(this));
        }
        this.geldtvoorNormwaardes = normwaardes;
    }

    public Locatie geldtvoorNormwaardes(Set<Normwaarde> normwaardes) {
        this.setGeldtvoorNormwaardes(normwaardes);
        return this;
    }

    public Locatie addGeldtvoorNormwaarde(Normwaarde normwaarde) {
        this.geldtvoorNormwaardes.add(normwaarde);
        normwaarde.getGeldtvoorLocaties().add(this);
        return this;
    }

    public Locatie removeGeldtvoorNormwaarde(Normwaarde normwaarde) {
        this.geldtvoorNormwaardes.remove(normwaarde);
        normwaarde.getGeldtvoorLocaties().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Locatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Locatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Locatie{" +
            "id=" + getId() +
            ", adres='" + getAdres() + "'" +
            "}";
    }
}
