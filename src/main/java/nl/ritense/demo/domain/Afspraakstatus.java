package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Afspraakstatus.
 */
@Entity
@Table(name = "afspraakstatus")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Afspraakstatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftAfspraakstatus")
    @JsonIgnoreProperties(
        value = { "mondtuitinKlantcontact", "heeftAfspraakstatus", "metMedewerker", "heeftbetrekkingopZaak" },
        allowSetters = true
    )
    private Set<Balieafspraak> heeftBalieafspraaks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Afspraakstatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public Afspraakstatus status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Balieafspraak> getHeeftBalieafspraaks() {
        return this.heeftBalieafspraaks;
    }

    public void setHeeftBalieafspraaks(Set<Balieafspraak> balieafspraaks) {
        if (this.heeftBalieafspraaks != null) {
            this.heeftBalieafspraaks.forEach(i -> i.setHeeftAfspraakstatus(null));
        }
        if (balieafspraaks != null) {
            balieafspraaks.forEach(i -> i.setHeeftAfspraakstatus(this));
        }
        this.heeftBalieafspraaks = balieafspraaks;
    }

    public Afspraakstatus heeftBalieafspraaks(Set<Balieafspraak> balieafspraaks) {
        this.setHeeftBalieafspraaks(balieafspraaks);
        return this;
    }

    public Afspraakstatus addHeeftBalieafspraak(Balieafspraak balieafspraak) {
        this.heeftBalieafspraaks.add(balieafspraak);
        balieafspraak.setHeeftAfspraakstatus(this);
        return this;
    }

    public Afspraakstatus removeHeeftBalieafspraak(Balieafspraak balieafspraak) {
        this.heeftBalieafspraaks.remove(balieafspraak);
        balieafspraak.setHeeftAfspraakstatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Afspraakstatus)) {
            return false;
        }
        return getId() != null && getId().equals(((Afspraakstatus) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Afspraakstatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
