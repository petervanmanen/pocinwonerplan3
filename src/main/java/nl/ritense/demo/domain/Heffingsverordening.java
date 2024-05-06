package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Heffingsverordening.
 */
@Entity
@Table(name = "heffingsverordening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Heffingsverordening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vermeldinHeffingsverordening")
    @JsonIgnoreProperties(value = { "vermeldinHeffingsverordening", "heeftZaaktype", "heeftgrondslagHeffings" }, allowSetters = true)
    private Set<Heffinggrondslag> vermeldinHeffinggrondslags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Heffingsverordening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Heffinggrondslag> getVermeldinHeffinggrondslags() {
        return this.vermeldinHeffinggrondslags;
    }

    public void setVermeldinHeffinggrondslags(Set<Heffinggrondslag> heffinggrondslags) {
        if (this.vermeldinHeffinggrondslags != null) {
            this.vermeldinHeffinggrondslags.forEach(i -> i.setVermeldinHeffingsverordening(null));
        }
        if (heffinggrondslags != null) {
            heffinggrondslags.forEach(i -> i.setVermeldinHeffingsverordening(this));
        }
        this.vermeldinHeffinggrondslags = heffinggrondslags;
    }

    public Heffingsverordening vermeldinHeffinggrondslags(Set<Heffinggrondslag> heffinggrondslags) {
        this.setVermeldinHeffinggrondslags(heffinggrondslags);
        return this;
    }

    public Heffingsverordening addVermeldinHeffinggrondslag(Heffinggrondslag heffinggrondslag) {
        this.vermeldinHeffinggrondslags.add(heffinggrondslag);
        heffinggrondslag.setVermeldinHeffingsverordening(this);
        return this;
    }

    public Heffingsverordening removeVermeldinHeffinggrondslag(Heffinggrondslag heffinggrondslag) {
        this.vermeldinHeffinggrondslags.remove(heffinggrondslag);
        heffinggrondslag.setVermeldinHeffingsverordening(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Heffingsverordening)) {
            return false;
        }
        return getId() != null && getId().equals(((Heffingsverordening) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Heffingsverordening{" +
            "id=" + getId() +
            "}";
    }
}
