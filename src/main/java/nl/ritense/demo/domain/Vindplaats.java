package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vindplaats.
 */
@Entity
@Table(name = "vindplaats")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vindplaats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "aard", length = 20)
    private String aard;

    @Column(name = "begindatering")
    private String begindatering;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Column(name = "bibliografie")
    private String bibliografie;

    @Column(name = "datering")
    private String datering;

    @Column(name = "depot")
    private String depot;

    @Column(name = "documentatie")
    private String documentatie;

    @Column(name = "einddatering")
    private String einddatering;

    @Column(name = "gemeente")
    private String gemeente;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "mobilia")
    private String mobilia;

    @Column(name = "onderzoek")
    private String onderzoek;

    @Size(max = 20)
    @Column(name = "projectcode", length = 20)
    private String projectcode;

    @Column(name = "vindplaats")
    private String vindplaats;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Project hoortbijProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftStellings", "istevindeninVindplaats" }, allowSetters = true)
    private Depot istevindeninDepot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftPlanks", "heeftStelling", "istevindeninVindplaats" }, allowSetters = true)
    private Kast istevindeninKast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftKast", "istevindeninVindplaats" }, allowSetters = true)
    private Plank istevindeninPlank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftMagazijnlocaties", "heeftKasts", "heeftDepot", "istevindeninVindplaats" }, allowSetters = true)
    private Stelling istevindeninStelling;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftVindplaats")
    @JsonIgnoreProperties(
        value = {
            "isonderdeelvanArchief",
            "heeftUitgever",
            "heeftVindplaats",
            "heeftOrdeningsschemas",
            "stamtuitPeriodes",
            "valtbinnenIndeling",
            "voorAanvraags",
        },
        allowSetters = true
    )
    private Set<Archiefstuk> heeftArchiefstuks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vindplaats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAard() {
        return this.aard;
    }

    public Vindplaats aard(String aard) {
        this.setAard(aard);
        return this;
    }

    public void setAard(String aard) {
        this.aard = aard;
    }

    public String getBegindatering() {
        return this.begindatering;
    }

    public Vindplaats begindatering(String begindatering) {
        this.setBegindatering(begindatering);
        return this;
    }

    public void setBegindatering(String begindatering) {
        this.begindatering = begindatering;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Vindplaats beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getBibliografie() {
        return this.bibliografie;
    }

    public Vindplaats bibliografie(String bibliografie) {
        this.setBibliografie(bibliografie);
        return this;
    }

    public void setBibliografie(String bibliografie) {
        this.bibliografie = bibliografie;
    }

    public String getDatering() {
        return this.datering;
    }

    public Vindplaats datering(String datering) {
        this.setDatering(datering);
        return this;
    }

    public void setDatering(String datering) {
        this.datering = datering;
    }

    public String getDepot() {
        return this.depot;
    }

    public Vindplaats depot(String depot) {
        this.setDepot(depot);
        return this;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getDocumentatie() {
        return this.documentatie;
    }

    public Vindplaats documentatie(String documentatie) {
        this.setDocumentatie(documentatie);
        return this;
    }

    public void setDocumentatie(String documentatie) {
        this.documentatie = documentatie;
    }

    public String getEinddatering() {
        return this.einddatering;
    }

    public Vindplaats einddatering(String einddatering) {
        this.setEinddatering(einddatering);
        return this;
    }

    public void setEinddatering(String einddatering) {
        this.einddatering = einddatering;
    }

    public String getGemeente() {
        return this.gemeente;
    }

    public Vindplaats gemeente(String gemeente) {
        this.setGemeente(gemeente);
        return this;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Vindplaats locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getMobilia() {
        return this.mobilia;
    }

    public Vindplaats mobilia(String mobilia) {
        this.setMobilia(mobilia);
        return this;
    }

    public void setMobilia(String mobilia) {
        this.mobilia = mobilia;
    }

    public String getOnderzoek() {
        return this.onderzoek;
    }

    public Vindplaats onderzoek(String onderzoek) {
        this.setOnderzoek(onderzoek);
        return this;
    }

    public void setOnderzoek(String onderzoek) {
        this.onderzoek = onderzoek;
    }

    public String getProjectcode() {
        return this.projectcode;
    }

    public Vindplaats projectcode(String projectcode) {
        this.setProjectcode(projectcode);
        return this;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    public String getVindplaats() {
        return this.vindplaats;
    }

    public Vindplaats vindplaats(String vindplaats) {
        this.setVindplaats(vindplaats);
        return this;
    }

    public void setVindplaats(String vindplaats) {
        this.vindplaats = vindplaats;
    }

    public Project getHoortbijProject() {
        return this.hoortbijProject;
    }

    public void setHoortbijProject(Project project) {
        this.hoortbijProject = project;
    }

    public Vindplaats hoortbijProject(Project project) {
        this.setHoortbijProject(project);
        return this;
    }

    public Depot getIstevindeninDepot() {
        return this.istevindeninDepot;
    }

    public void setIstevindeninDepot(Depot depot) {
        this.istevindeninDepot = depot;
    }

    public Vindplaats istevindeninDepot(Depot depot) {
        this.setIstevindeninDepot(depot);
        return this;
    }

    public Kast getIstevindeninKast() {
        return this.istevindeninKast;
    }

    public void setIstevindeninKast(Kast kast) {
        this.istevindeninKast = kast;
    }

    public Vindplaats istevindeninKast(Kast kast) {
        this.setIstevindeninKast(kast);
        return this;
    }

    public Plank getIstevindeninPlank() {
        return this.istevindeninPlank;
    }

    public void setIstevindeninPlank(Plank plank) {
        this.istevindeninPlank = plank;
    }

    public Vindplaats istevindeninPlank(Plank plank) {
        this.setIstevindeninPlank(plank);
        return this;
    }

    public Stelling getIstevindeninStelling() {
        return this.istevindeninStelling;
    }

    public void setIstevindeninStelling(Stelling stelling) {
        this.istevindeninStelling = stelling;
    }

    public Vindplaats istevindeninStelling(Stelling stelling) {
        this.setIstevindeninStelling(stelling);
        return this;
    }

    public Set<Archiefstuk> getHeeftArchiefstuks() {
        return this.heeftArchiefstuks;
    }

    public void setHeeftArchiefstuks(Set<Archiefstuk> archiefstuks) {
        if (this.heeftArchiefstuks != null) {
            this.heeftArchiefstuks.forEach(i -> i.setHeeftVindplaats(null));
        }
        if (archiefstuks != null) {
            archiefstuks.forEach(i -> i.setHeeftVindplaats(this));
        }
        this.heeftArchiefstuks = archiefstuks;
    }

    public Vindplaats heeftArchiefstuks(Set<Archiefstuk> archiefstuks) {
        this.setHeeftArchiefstuks(archiefstuks);
        return this;
    }

    public Vindplaats addHeeftArchiefstuk(Archiefstuk archiefstuk) {
        this.heeftArchiefstuks.add(archiefstuk);
        archiefstuk.setHeeftVindplaats(this);
        return this;
    }

    public Vindplaats removeHeeftArchiefstuk(Archiefstuk archiefstuk) {
        this.heeftArchiefstuks.remove(archiefstuk);
        archiefstuk.setHeeftVindplaats(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vindplaats)) {
            return false;
        }
        return getId() != null && getId().equals(((Vindplaats) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vindplaats{" +
            "id=" + getId() +
            ", aard='" + getAard() + "'" +
            ", begindatering='" + getBegindatering() + "'" +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", bibliografie='" + getBibliografie() + "'" +
            ", datering='" + getDatering() + "'" +
            ", depot='" + getDepot() + "'" +
            ", documentatie='" + getDocumentatie() + "'" +
            ", einddatering='" + getEinddatering() + "'" +
            ", gemeente='" + getGemeente() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", mobilia='" + getMobilia() + "'" +
            ", onderzoek='" + getOnderzoek() + "'" +
            ", projectcode='" + getProjectcode() + "'" +
            ", vindplaats='" + getVindplaats() + "'" +
            "}";
    }
}
