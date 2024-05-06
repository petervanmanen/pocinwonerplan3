package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Ordeningsschema.
 */
@Entity
@Table(name = "ordeningsschema")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ordeningsschema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "text")
    private String text;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftOrdeningsschemas")
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

    public Ordeningsschema id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Ordeningsschema naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getText() {
        return this.text;
    }

    public Ordeningsschema text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Archiefstuk> getHeeftArchiefstuks() {
        return this.heeftArchiefstuks;
    }

    public void setHeeftArchiefstuks(Set<Archiefstuk> archiefstuks) {
        if (this.heeftArchiefstuks != null) {
            this.heeftArchiefstuks.forEach(i -> i.removeHeeftOrdeningsschema(this));
        }
        if (archiefstuks != null) {
            archiefstuks.forEach(i -> i.addHeeftOrdeningsschema(this));
        }
        this.heeftArchiefstuks = archiefstuks;
    }

    public Ordeningsschema heeftArchiefstuks(Set<Archiefstuk> archiefstuks) {
        this.setHeeftArchiefstuks(archiefstuks);
        return this;
    }

    public Ordeningsschema addHeeftArchiefstuk(Archiefstuk archiefstuk) {
        this.heeftArchiefstuks.add(archiefstuk);
        archiefstuk.getHeeftOrdeningsschemas().add(this);
        return this;
    }

    public Ordeningsschema removeHeeftArchiefstuk(Archiefstuk archiefstuk) {
        this.heeftArchiefstuks.remove(archiefstuk);
        archiefstuk.getHeeftOrdeningsschemas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ordeningsschema)) {
            return false;
        }
        return getId() != null && getId().equals(((Ordeningsschema) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ordeningsschema{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
