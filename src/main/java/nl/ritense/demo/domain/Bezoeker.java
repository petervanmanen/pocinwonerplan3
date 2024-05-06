package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Bezoeker.
 */
@Entity
@Table(name = "bezoeker")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bezoeker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doetBezoeker")
    @JsonIgnoreProperties(value = { "voorArchiefstuks", "doetBezoeker" }, allowSetters = true)
    private Set<Aanvraag> doetAanvraags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bezoeker id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Aanvraag> getDoetAanvraags() {
        return this.doetAanvraags;
    }

    public void setDoetAanvraags(Set<Aanvraag> aanvraags) {
        if (this.doetAanvraags != null) {
            this.doetAanvraags.forEach(i -> i.setDoetBezoeker(null));
        }
        if (aanvraags != null) {
            aanvraags.forEach(i -> i.setDoetBezoeker(this));
        }
        this.doetAanvraags = aanvraags;
    }

    public Bezoeker doetAanvraags(Set<Aanvraag> aanvraags) {
        this.setDoetAanvraags(aanvraags);
        return this;
    }

    public Bezoeker addDoetAanvraag(Aanvraag aanvraag) {
        this.doetAanvraags.add(aanvraag);
        aanvraag.setDoetBezoeker(this);
        return this;
    }

    public Bezoeker removeDoetAanvraag(Aanvraag aanvraag) {
        this.doetAanvraags.remove(aanvraag);
        aanvraag.setDoetBezoeker(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bezoeker)) {
            return false;
        }
        return getId() != null && getId().equals(((Bezoeker) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bezoeker{" +
            "id=" + getId() +
            "}";
    }
}
