package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Put.
 */
@Entity
@Table(name = "put")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Put implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "projectcd")
    private String projectcd;

    @Column(name = "putnummer")
    private String putnummer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftPut")
    @JsonIgnoreProperties(value = { "heeftSpoors", "heeftPut" }, allowSetters = true)
    private Set<Vlak> heeftVlaks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_put__heeftlocatie_locatie",
        joinColumns = @JoinColumn(name = "put_id"),
        inverseJoinColumns = @JoinColumn(name = "heeftlocatie_locatie_id")
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
    private Set<Locatie> heeftlocatieLocaties = new HashSet<>();

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Put id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public Put key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProjectcd() {
        return this.projectcd;
    }

    public Put projectcd(String projectcd) {
        this.setProjectcd(projectcd);
        return this;
    }

    public void setProjectcd(String projectcd) {
        this.projectcd = projectcd;
    }

    public String getPutnummer() {
        return this.putnummer;
    }

    public Put putnummer(String putnummer) {
        this.setPutnummer(putnummer);
        return this;
    }

    public void setPutnummer(String putnummer) {
        this.putnummer = putnummer;
    }

    public Set<Vlak> getHeeftVlaks() {
        return this.heeftVlaks;
    }

    public void setHeeftVlaks(Set<Vlak> vlaks) {
        if (this.heeftVlaks != null) {
            this.heeftVlaks.forEach(i -> i.setHeeftPut(null));
        }
        if (vlaks != null) {
            vlaks.forEach(i -> i.setHeeftPut(this));
        }
        this.heeftVlaks = vlaks;
    }

    public Put heeftVlaks(Set<Vlak> vlaks) {
        this.setHeeftVlaks(vlaks);
        return this;
    }

    public Put addHeeftVlak(Vlak vlak) {
        this.heeftVlaks.add(vlak);
        vlak.setHeeftPut(this);
        return this;
    }

    public Put removeHeeftVlak(Vlak vlak) {
        this.heeftVlaks.remove(vlak);
        vlak.setHeeftPut(null);
        return this;
    }

    public Set<Locatie> getHeeftlocatieLocaties() {
        return this.heeftlocatieLocaties;
    }

    public void setHeeftlocatieLocaties(Set<Locatie> locaties) {
        this.heeftlocatieLocaties = locaties;
    }

    public Put heeftlocatieLocaties(Set<Locatie> locaties) {
        this.setHeeftlocatieLocaties(locaties);
        return this;
    }

    public Put addHeeftlocatieLocatie(Locatie locatie) {
        this.heeftlocatieLocaties.add(locatie);
        return this;
    }

    public Put removeHeeftlocatieLocatie(Locatie locatie) {
        this.heeftlocatieLocaties.remove(locatie);
        return this;
    }

    public Project getHeeftProject() {
        return this.heeftProject;
    }

    public void setHeeftProject(Project project) {
        this.heeftProject = project;
    }

    public Put heeftProject(Project project) {
        this.setHeeftProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Put)) {
            return false;
        }
        return getId() != null && getId().equals(((Put) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Put{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", projectcd='" + getProjectcd() + "'" +
            ", putnummer='" + getPutnummer() + "'" +
            "}";
    }
}
