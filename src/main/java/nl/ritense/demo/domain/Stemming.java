package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Stemming.
 */
@Entity
@Table(name = "stemming")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Stemming implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "resultaat")
    private String resultaat;

    @Column(name = "stemmingstype")
    private String stemmingstype;

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
    private Raadsstuk betreftRaadsstuk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftVergadering", "hoortbijStemmings", "behandeltRaadsstuks" }, allowSetters = true)
    private Agendapunt hoortbijAgendapunt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Stemming id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultaat() {
        return this.resultaat;
    }

    public Stemming resultaat(String resultaat) {
        this.setResultaat(resultaat);
        return this;
    }

    public void setResultaat(String resultaat) {
        this.resultaat = resultaat;
    }

    public String getStemmingstype() {
        return this.stemmingstype;
    }

    public Stemming stemmingstype(String stemmingstype) {
        this.setStemmingstype(stemmingstype);
        return this;
    }

    public void setStemmingstype(String stemmingstype) {
        this.stemmingstype = stemmingstype;
    }

    public Raadsstuk getBetreftRaadsstuk() {
        return this.betreftRaadsstuk;
    }

    public void setBetreftRaadsstuk(Raadsstuk raadsstuk) {
        this.betreftRaadsstuk = raadsstuk;
    }

    public Stemming betreftRaadsstuk(Raadsstuk raadsstuk) {
        this.setBetreftRaadsstuk(raadsstuk);
        return this;
    }

    public Agendapunt getHoortbijAgendapunt() {
        return this.hoortbijAgendapunt;
    }

    public void setHoortbijAgendapunt(Agendapunt agendapunt) {
        this.hoortbijAgendapunt = agendapunt;
    }

    public Stemming hoortbijAgendapunt(Agendapunt agendapunt) {
        this.setHoortbijAgendapunt(agendapunt);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stemming)) {
            return false;
        }
        return getId() != null && getId().equals(((Stemming) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stemming{" +
            "id=" + getId() +
            ", resultaat='" + getResultaat() + "'" +
            ", stemmingstype='" + getStemmingstype() + "'" +
            "}";
    }
}
