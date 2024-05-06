package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vergadering.
 */
@Entity
@Table(name = "vergadering")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vergadering implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eindtijd")
    private String eindtijd;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "starttijd")
    private String starttijd;

    @Column(name = "titel")
    private String titel;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Raadsstuk heeftverslagRaadsstuk;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftVergadering")
    @JsonIgnoreProperties(value = { "heeftVergadering", "hoortbijStemmings", "behandeltRaadsstuks" }, allowSetters = true)
    private Set<Agendapunt> heeftAgendapunts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftVergaderings", "islidvanRaadslids" }, allowSetters = true)
    private Raadscommissie heeftRaadscommissie;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "wordtbehandeldinVergaderings")
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
    private Set<Raadsstuk> wordtbehandeldinRaadsstuks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vergadering id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEindtijd() {
        return this.eindtijd;
    }

    public Vergadering eindtijd(String eindtijd) {
        this.setEindtijd(eindtijd);
        return this;
    }

    public void setEindtijd(String eindtijd) {
        this.eindtijd = eindtijd;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Vergadering locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getStarttijd() {
        return this.starttijd;
    }

    public Vergadering starttijd(String starttijd) {
        this.setStarttijd(starttijd);
        return this;
    }

    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    public String getTitel() {
        return this.titel;
    }

    public Vergadering titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Raadsstuk getHeeftverslagRaadsstuk() {
        return this.heeftverslagRaadsstuk;
    }

    public void setHeeftverslagRaadsstuk(Raadsstuk raadsstuk) {
        this.heeftverslagRaadsstuk = raadsstuk;
    }

    public Vergadering heeftverslagRaadsstuk(Raadsstuk raadsstuk) {
        this.setHeeftverslagRaadsstuk(raadsstuk);
        return this;
    }

    public Set<Agendapunt> getHeeftAgendapunts() {
        return this.heeftAgendapunts;
    }

    public void setHeeftAgendapunts(Set<Agendapunt> agendapunts) {
        if (this.heeftAgendapunts != null) {
            this.heeftAgendapunts.forEach(i -> i.setHeeftVergadering(null));
        }
        if (agendapunts != null) {
            agendapunts.forEach(i -> i.setHeeftVergadering(this));
        }
        this.heeftAgendapunts = agendapunts;
    }

    public Vergadering heeftAgendapunts(Set<Agendapunt> agendapunts) {
        this.setHeeftAgendapunts(agendapunts);
        return this;
    }

    public Vergadering addHeeftAgendapunt(Agendapunt agendapunt) {
        this.heeftAgendapunts.add(agendapunt);
        agendapunt.setHeeftVergadering(this);
        return this;
    }

    public Vergadering removeHeeftAgendapunt(Agendapunt agendapunt) {
        this.heeftAgendapunts.remove(agendapunt);
        agendapunt.setHeeftVergadering(null);
        return this;
    }

    public Raadscommissie getHeeftRaadscommissie() {
        return this.heeftRaadscommissie;
    }

    public void setHeeftRaadscommissie(Raadscommissie raadscommissie) {
        this.heeftRaadscommissie = raadscommissie;
    }

    public Vergadering heeftRaadscommissie(Raadscommissie raadscommissie) {
        this.setHeeftRaadscommissie(raadscommissie);
        return this;
    }

    public Set<Raadsstuk> getWordtbehandeldinRaadsstuks() {
        return this.wordtbehandeldinRaadsstuks;
    }

    public void setWordtbehandeldinRaadsstuks(Set<Raadsstuk> raadsstuks) {
        if (this.wordtbehandeldinRaadsstuks != null) {
            this.wordtbehandeldinRaadsstuks.forEach(i -> i.removeWordtbehandeldinVergadering(this));
        }
        if (raadsstuks != null) {
            raadsstuks.forEach(i -> i.addWordtbehandeldinVergadering(this));
        }
        this.wordtbehandeldinRaadsstuks = raadsstuks;
    }

    public Vergadering wordtbehandeldinRaadsstuks(Set<Raadsstuk> raadsstuks) {
        this.setWordtbehandeldinRaadsstuks(raadsstuks);
        return this;
    }

    public Vergadering addWordtbehandeldinRaadsstuk(Raadsstuk raadsstuk) {
        this.wordtbehandeldinRaadsstuks.add(raadsstuk);
        raadsstuk.getWordtbehandeldinVergaderings().add(this);
        return this;
    }

    public Vergadering removeWordtbehandeldinRaadsstuk(Raadsstuk raadsstuk) {
        this.wordtbehandeldinRaadsstuks.remove(raadsstuk);
        raadsstuk.getWordtbehandeldinVergaderings().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vergadering)) {
            return false;
        }
        return getId() != null && getId().equals(((Vergadering) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vergadering{" +
            "id=" + getId() +
            ", eindtijd='" + getEindtijd() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", starttijd='" + getStarttijd() + "'" +
            ", titel='" + getTitel() + "'" +
            "}";
    }
}
