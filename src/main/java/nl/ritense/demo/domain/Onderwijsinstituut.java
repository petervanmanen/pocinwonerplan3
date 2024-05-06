package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Onderwijsinstituut.
 */
@Entity
@Table(name = "onderwijsinstituut")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Onderwijsinstituut implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "wordtgegevendoorOnderwijsinstituuts")
    @JsonIgnoreProperties(value = { "wordtgegevendoorOnderwijsinstituuts" }, allowSetters = true)
    private Set<Opleiding> wordtgegevendoorOpleidings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Onderwijsinstituut id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Opleiding> getWordtgegevendoorOpleidings() {
        return this.wordtgegevendoorOpleidings;
    }

    public void setWordtgegevendoorOpleidings(Set<Opleiding> opleidings) {
        if (this.wordtgegevendoorOpleidings != null) {
            this.wordtgegevendoorOpleidings.forEach(i -> i.removeWordtgegevendoorOnderwijsinstituut(this));
        }
        if (opleidings != null) {
            opleidings.forEach(i -> i.addWordtgegevendoorOnderwijsinstituut(this));
        }
        this.wordtgegevendoorOpleidings = opleidings;
    }

    public Onderwijsinstituut wordtgegevendoorOpleidings(Set<Opleiding> opleidings) {
        this.setWordtgegevendoorOpleidings(opleidings);
        return this;
    }

    public Onderwijsinstituut addWordtgegevendoorOpleiding(Opleiding opleiding) {
        this.wordtgegevendoorOpleidings.add(opleiding);
        opleiding.getWordtgegevendoorOnderwijsinstituuts().add(this);
        return this;
    }

    public Onderwijsinstituut removeWordtgegevendoorOpleiding(Opleiding opleiding) {
        this.wordtgegevendoorOpleidings.remove(opleiding);
        opleiding.getWordtgegevendoorOnderwijsinstituuts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Onderwijsinstituut)) {
            return false;
        }
        return getId() != null && getId().equals(((Onderwijsinstituut) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Onderwijsinstituut{" +
            "id=" + getId() +
            "}";
    }
}
