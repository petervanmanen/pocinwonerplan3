package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Regeltekst.
 */
@Entity
@Table(name = "regeltekst")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Regeltekst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "tekst")
    private String tekst;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "werkingsgebiedRegeltekst2")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isgerelateerdRegeltekst2")
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
    private Set<Regeltekst> isgerelateerdRegelteksts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_regeltekst__heeftthema_thema",
        joinColumns = @JoinColumn(name = "regeltekst_id"),
        inverseJoinColumns = @JoinColumn(name = "heeftthema_thema_id")
    )
    @JsonIgnoreProperties(value = { "subthemaThemas", "subthemaThema2", "heeftthemaRegelteksts" }, allowSetters = true)
    private Set<Thema> heeftthemaThemas = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_regeltekst__heeftidealisatie_idealisatie",
        joinColumns = @JoinColumn(name = "regeltekst_id"),
        inverseJoinColumns = @JoinColumn(name = "heeftidealisatie_idealisatie_id")
    )
    @JsonIgnoreProperties(value = { "heeftidealisatieRegelteksts" }, allowSetters = true)
    private Set<Idealisatie> heeftidealisatieIdealisaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_regeltekst__werkingsgebied_locatie",
        joinColumns = @JoinColumn(name = "regeltekst_id"),
        inverseJoinColumns = @JoinColumn(name = "werkingsgebied_locatie_id")
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
    private Set<Locatie> werkingsgebiedLocaties = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bevatRegelteksts" }, allowSetters = true)
    private Omgevingsdocument bevatOmgevingsdocument;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Regeltekst werkingsgebiedRegeltekst2;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Regeltekst isgerelateerdRegeltekst2;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Regeltekst id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Regeltekst identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Regeltekst omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getTekst() {
        return this.tekst;
    }

    public Regeltekst tekst(String tekst) {
        this.setTekst(tekst);
        return this;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Set<Regeltekst> getWerkingsgebiedRegelteksts() {
        return this.werkingsgebiedRegelteksts;
    }

    public void setWerkingsgebiedRegelteksts(Set<Regeltekst> regelteksts) {
        if (this.werkingsgebiedRegelteksts != null) {
            this.werkingsgebiedRegelteksts.forEach(i -> i.setWerkingsgebiedRegeltekst2(null));
        }
        if (regelteksts != null) {
            regelteksts.forEach(i -> i.setWerkingsgebiedRegeltekst2(this));
        }
        this.werkingsgebiedRegelteksts = regelteksts;
    }

    public Regeltekst werkingsgebiedRegelteksts(Set<Regeltekst> regelteksts) {
        this.setWerkingsgebiedRegelteksts(regelteksts);
        return this;
    }

    public Regeltekst addWerkingsgebiedRegeltekst(Regeltekst regeltekst) {
        this.werkingsgebiedRegelteksts.add(regeltekst);
        regeltekst.setWerkingsgebiedRegeltekst2(this);
        return this;
    }

    public Regeltekst removeWerkingsgebiedRegeltekst(Regeltekst regeltekst) {
        this.werkingsgebiedRegelteksts.remove(regeltekst);
        regeltekst.setWerkingsgebiedRegeltekst2(null);
        return this;
    }

    public Set<Regeltekst> getIsgerelateerdRegelteksts() {
        return this.isgerelateerdRegelteksts;
    }

    public void setIsgerelateerdRegelteksts(Set<Regeltekst> regelteksts) {
        if (this.isgerelateerdRegelteksts != null) {
            this.isgerelateerdRegelteksts.forEach(i -> i.setIsgerelateerdRegeltekst2(null));
        }
        if (regelteksts != null) {
            regelteksts.forEach(i -> i.setIsgerelateerdRegeltekst2(this));
        }
        this.isgerelateerdRegelteksts = regelteksts;
    }

    public Regeltekst isgerelateerdRegelteksts(Set<Regeltekst> regelteksts) {
        this.setIsgerelateerdRegelteksts(regelteksts);
        return this;
    }

    public Regeltekst addIsgerelateerdRegeltekst(Regeltekst regeltekst) {
        this.isgerelateerdRegelteksts.add(regeltekst);
        regeltekst.setIsgerelateerdRegeltekst2(this);
        return this;
    }

    public Regeltekst removeIsgerelateerdRegeltekst(Regeltekst regeltekst) {
        this.isgerelateerdRegelteksts.remove(regeltekst);
        regeltekst.setIsgerelateerdRegeltekst2(null);
        return this;
    }

    public Set<Thema> getHeeftthemaThemas() {
        return this.heeftthemaThemas;
    }

    public void setHeeftthemaThemas(Set<Thema> themas) {
        this.heeftthemaThemas = themas;
    }

    public Regeltekst heeftthemaThemas(Set<Thema> themas) {
        this.setHeeftthemaThemas(themas);
        return this;
    }

    public Regeltekst addHeeftthemaThema(Thema thema) {
        this.heeftthemaThemas.add(thema);
        return this;
    }

    public Regeltekst removeHeeftthemaThema(Thema thema) {
        this.heeftthemaThemas.remove(thema);
        return this;
    }

    public Set<Idealisatie> getHeeftidealisatieIdealisaties() {
        return this.heeftidealisatieIdealisaties;
    }

    public void setHeeftidealisatieIdealisaties(Set<Idealisatie> idealisaties) {
        this.heeftidealisatieIdealisaties = idealisaties;
    }

    public Regeltekst heeftidealisatieIdealisaties(Set<Idealisatie> idealisaties) {
        this.setHeeftidealisatieIdealisaties(idealisaties);
        return this;
    }

    public Regeltekst addHeeftidealisatieIdealisatie(Idealisatie idealisatie) {
        this.heeftidealisatieIdealisaties.add(idealisatie);
        return this;
    }

    public Regeltekst removeHeeftidealisatieIdealisatie(Idealisatie idealisatie) {
        this.heeftidealisatieIdealisaties.remove(idealisatie);
        return this;
    }

    public Set<Locatie> getWerkingsgebiedLocaties() {
        return this.werkingsgebiedLocaties;
    }

    public void setWerkingsgebiedLocaties(Set<Locatie> locaties) {
        this.werkingsgebiedLocaties = locaties;
    }

    public Regeltekst werkingsgebiedLocaties(Set<Locatie> locaties) {
        this.setWerkingsgebiedLocaties(locaties);
        return this;
    }

    public Regeltekst addWerkingsgebiedLocatie(Locatie locatie) {
        this.werkingsgebiedLocaties.add(locatie);
        return this;
    }

    public Regeltekst removeWerkingsgebiedLocatie(Locatie locatie) {
        this.werkingsgebiedLocaties.remove(locatie);
        return this;
    }

    public Omgevingsdocument getBevatOmgevingsdocument() {
        return this.bevatOmgevingsdocument;
    }

    public void setBevatOmgevingsdocument(Omgevingsdocument omgevingsdocument) {
        this.bevatOmgevingsdocument = omgevingsdocument;
    }

    public Regeltekst bevatOmgevingsdocument(Omgevingsdocument omgevingsdocument) {
        this.setBevatOmgevingsdocument(omgevingsdocument);
        return this;
    }

    public Regeltekst getWerkingsgebiedRegeltekst2() {
        return this.werkingsgebiedRegeltekst2;
    }

    public void setWerkingsgebiedRegeltekst2(Regeltekst regeltekst) {
        this.werkingsgebiedRegeltekst2 = regeltekst;
    }

    public Regeltekst werkingsgebiedRegeltekst2(Regeltekst regeltekst) {
        this.setWerkingsgebiedRegeltekst2(regeltekst);
        return this;
    }

    public Regeltekst getIsgerelateerdRegeltekst2() {
        return this.isgerelateerdRegeltekst2;
    }

    public void setIsgerelateerdRegeltekst2(Regeltekst regeltekst) {
        this.isgerelateerdRegeltekst2 = regeltekst;
    }

    public Regeltekst isgerelateerdRegeltekst2(Regeltekst regeltekst) {
        this.setIsgerelateerdRegeltekst2(regeltekst);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Regeltekst)) {
            return false;
        }
        return getId() != null && getId().equals(((Regeltekst) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Regeltekst{" +
            "id=" + getId() +
            ", identificatie='" + getIdentificatie() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", tekst='" + getTekst() + "'" +
            "}";
    }
}
