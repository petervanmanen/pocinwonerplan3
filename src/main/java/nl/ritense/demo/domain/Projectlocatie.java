package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Projectlocatie.
 */
@Entity
@Table(name = "projectlocatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projectlocatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adres")
    private String adres;

    @Column(name = "kadastraalperceel")
    private String kadastraalperceel;

    @Column(name = "kadastralegemeente")
    private String kadastralegemeente;

    @Column(name = "kadastralesectie")
    private String kadastralesectie;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Locatie betreftLocatie;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Project heeftProject;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betreftProjectlocaties")
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

    public Projectlocatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdres() {
        return this.adres;
    }

    public Projectlocatie adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getKadastraalperceel() {
        return this.kadastraalperceel;
    }

    public Projectlocatie kadastraalperceel(String kadastraalperceel) {
        this.setKadastraalperceel(kadastraalperceel);
        return this;
    }

    public void setKadastraalperceel(String kadastraalperceel) {
        this.kadastraalperceel = kadastraalperceel;
    }

    public String getKadastralegemeente() {
        return this.kadastralegemeente;
    }

    public Projectlocatie kadastralegemeente(String kadastralegemeente) {
        this.setKadastralegemeente(kadastralegemeente);
        return this;
    }

    public void setKadastralegemeente(String kadastralegemeente) {
        this.kadastralegemeente = kadastralegemeente;
    }

    public String getKadastralesectie() {
        return this.kadastralesectie;
    }

    public Projectlocatie kadastralesectie(String kadastralesectie) {
        this.setKadastralesectie(kadastralesectie);
        return this;
    }

    public void setKadastralesectie(String kadastralesectie) {
        this.kadastralesectie = kadastralesectie;
    }

    public Locatie getBetreftLocatie() {
        return this.betreftLocatie;
    }

    public void setBetreftLocatie(Locatie locatie) {
        this.betreftLocatie = locatie;
    }

    public Projectlocatie betreftLocatie(Locatie locatie) {
        this.setBetreftLocatie(locatie);
        return this;
    }

    public Project getHeeftProject() {
        return this.heeftProject;
    }

    public void setHeeftProject(Project project) {
        this.heeftProject = project;
    }

    public Projectlocatie heeftProject(Project project) {
        this.setHeeftProject(project);
        return this;
    }

    public Set<Verzoek> getBetreftVerzoeks() {
        return this.betreftVerzoeks;
    }

    public void setBetreftVerzoeks(Set<Verzoek> verzoeks) {
        if (this.betreftVerzoeks != null) {
            this.betreftVerzoeks.forEach(i -> i.removeBetreftProjectlocatie(this));
        }
        if (verzoeks != null) {
            verzoeks.forEach(i -> i.addBetreftProjectlocatie(this));
        }
        this.betreftVerzoeks = verzoeks;
    }

    public Projectlocatie betreftVerzoeks(Set<Verzoek> verzoeks) {
        this.setBetreftVerzoeks(verzoeks);
        return this;
    }

    public Projectlocatie addBetreftVerzoek(Verzoek verzoek) {
        this.betreftVerzoeks.add(verzoek);
        verzoek.getBetreftProjectlocaties().add(this);
        return this;
    }

    public Projectlocatie removeBetreftVerzoek(Verzoek verzoek) {
        this.betreftVerzoeks.remove(verzoek);
        verzoek.getBetreftProjectlocaties().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projectlocatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Projectlocatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projectlocatie{" +
            "id=" + getId() +
            ", adres='" + getAdres() + "'" +
            ", kadastraalperceel='" + getKadastraalperceel() + "'" +
            ", kadastralegemeente='" + getKadastralegemeente() + "'" +
            ", kadastralesectie='" + getKadastralesectie() + "'" +
            "}";
    }
}
