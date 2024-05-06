package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Aanvraag.
 */
@Entity
@Table(name = "aanvraag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aanvraag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumtijd")
    private String datumtijd;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_aanvraag__voor_archiefstuk",
        joinColumns = @JoinColumn(name = "aanvraag_id"),
        inverseJoinColumns = @JoinColumn(name = "voor_archiefstuk_id")
    )
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
    private Set<Archiefstuk> voorArchiefstuks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "doetAanvraags" }, allowSetters = true)
    private Bezoeker doetBezoeker;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aanvraag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumtijd() {
        return this.datumtijd;
    }

    public Aanvraag datumtijd(String datumtijd) {
        this.setDatumtijd(datumtijd);
        return this;
    }

    public void setDatumtijd(String datumtijd) {
        this.datumtijd = datumtijd;
    }

    public Set<Archiefstuk> getVoorArchiefstuks() {
        return this.voorArchiefstuks;
    }

    public void setVoorArchiefstuks(Set<Archiefstuk> archiefstuks) {
        this.voorArchiefstuks = archiefstuks;
    }

    public Aanvraag voorArchiefstuks(Set<Archiefstuk> archiefstuks) {
        this.setVoorArchiefstuks(archiefstuks);
        return this;
    }

    public Aanvraag addVoorArchiefstuk(Archiefstuk archiefstuk) {
        this.voorArchiefstuks.add(archiefstuk);
        return this;
    }

    public Aanvraag removeVoorArchiefstuk(Archiefstuk archiefstuk) {
        this.voorArchiefstuks.remove(archiefstuk);
        return this;
    }

    public Bezoeker getDoetBezoeker() {
        return this.doetBezoeker;
    }

    public void setDoetBezoeker(Bezoeker bezoeker) {
        this.doetBezoeker = bezoeker;
    }

    public Aanvraag doetBezoeker(Bezoeker bezoeker) {
        this.setDoetBezoeker(bezoeker);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aanvraag)) {
            return false;
        }
        return getId() != null && getId().equals(((Aanvraag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aanvraag{" +
            "id=" + getId() +
            ", datumtijd='" + getDatumtijd() + "'" +
            "}";
    }
}
