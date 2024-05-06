package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Initiatiefnemer.
 */
@Entity
@Table(name = "initiatiefnemer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Initiatiefnemer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftalsverantwoordelijkeInitiatiefnemer")
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
    private Set<Verzoek> heeftalsverantwoordelijkeVerzoeks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Initiatiefnemer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Verzoek> getHeeftalsverantwoordelijkeVerzoeks() {
        return this.heeftalsverantwoordelijkeVerzoeks;
    }

    public void setHeeftalsverantwoordelijkeVerzoeks(Set<Verzoek> verzoeks) {
        if (this.heeftalsverantwoordelijkeVerzoeks != null) {
            this.heeftalsverantwoordelijkeVerzoeks.forEach(i -> i.setHeeftalsverantwoordelijkeInitiatiefnemer(null));
        }
        if (verzoeks != null) {
            verzoeks.forEach(i -> i.setHeeftalsverantwoordelijkeInitiatiefnemer(this));
        }
        this.heeftalsverantwoordelijkeVerzoeks = verzoeks;
    }

    public Initiatiefnemer heeftalsverantwoordelijkeVerzoeks(Set<Verzoek> verzoeks) {
        this.setHeeftalsverantwoordelijkeVerzoeks(verzoeks);
        return this;
    }

    public Initiatiefnemer addHeeftalsverantwoordelijkeVerzoek(Verzoek verzoek) {
        this.heeftalsverantwoordelijkeVerzoeks.add(verzoek);
        verzoek.setHeeftalsverantwoordelijkeInitiatiefnemer(this);
        return this;
    }

    public Initiatiefnemer removeHeeftalsverantwoordelijkeVerzoek(Verzoek verzoek) {
        this.heeftalsverantwoordelijkeVerzoeks.remove(verzoek);
        verzoek.setHeeftalsverantwoordelijkeInitiatiefnemer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Initiatiefnemer)) {
            return false;
        }
        return getId() != null && getId().equals(((Initiatiefnemer) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Initiatiefnemer{" +
            "id=" + getId() +
            "}";
    }
}
