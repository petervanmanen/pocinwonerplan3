package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Eroute.
 */
@Entity
@Table(name = "eroute")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Eroute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "eroutecode")
    private String eroutecode;

    @Column(name = "eroutesoort")
    private String eroutesoort;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Eroute id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Eroute geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getEroutecode() {
        return this.eroutecode;
    }

    public Eroute eroutecode(String eroutecode) {
        this.setEroutecode(eroutecode);
        return this;
    }

    public void setEroutecode(String eroutecode) {
        this.eroutecode = eroutecode;
    }

    public String getEroutesoort() {
        return this.eroutesoort;
    }

    public Eroute eroutesoort(String eroutesoort) {
        this.setEroutesoort(eroutesoort);
        return this;
    }

    public void setEroutesoort(String eroutesoort) {
        this.eroutesoort = eroutesoort;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eroute)) {
            return false;
        }
        return getId() != null && getId().equals(((Eroute) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Eroute{" +
            "id=" + getId() +
            ", geometrie='" + getGeometrie() + "'" +
            ", eroutecode='" + getEroutecode() + "'" +
            ", eroutesoort='" + getEroutesoort() + "'" +
            "}";
    }
}
