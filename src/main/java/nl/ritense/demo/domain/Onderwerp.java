package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Onderwerp.
 */
@Entity
@Table(name = "onderwerp")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Onderwerp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "hoofdonderwerpOnderwerp", "heeftDiensts", "hoofdonderwerpOnderwerp2s" }, allowSetters = true)
    private Onderwerp hoofdonderwerpOnderwerp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftOnderwerp")
    @JsonIgnoreProperties(value = { "startZaaktype", "heeftOnderwerp", "betreftProduct" }, allowSetters = true)
    private Set<Dienst> heeftDiensts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hoofdonderwerpOnderwerp")
    @JsonIgnoreProperties(value = { "hoofdonderwerpOnderwerp", "heeftDiensts", "hoofdonderwerpOnderwerp2s" }, allowSetters = true)
    private Set<Onderwerp> hoofdonderwerpOnderwerp2s = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Onderwerp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Onderwerp getHoofdonderwerpOnderwerp() {
        return this.hoofdonderwerpOnderwerp;
    }

    public void setHoofdonderwerpOnderwerp(Onderwerp onderwerp) {
        this.hoofdonderwerpOnderwerp = onderwerp;
    }

    public Onderwerp hoofdonderwerpOnderwerp(Onderwerp onderwerp) {
        this.setHoofdonderwerpOnderwerp(onderwerp);
        return this;
    }

    public Set<Dienst> getHeeftDiensts() {
        return this.heeftDiensts;
    }

    public void setHeeftDiensts(Set<Dienst> diensts) {
        if (this.heeftDiensts != null) {
            this.heeftDiensts.forEach(i -> i.setHeeftOnderwerp(null));
        }
        if (diensts != null) {
            diensts.forEach(i -> i.setHeeftOnderwerp(this));
        }
        this.heeftDiensts = diensts;
    }

    public Onderwerp heeftDiensts(Set<Dienst> diensts) {
        this.setHeeftDiensts(diensts);
        return this;
    }

    public Onderwerp addHeeftDienst(Dienst dienst) {
        this.heeftDiensts.add(dienst);
        dienst.setHeeftOnderwerp(this);
        return this;
    }

    public Onderwerp removeHeeftDienst(Dienst dienst) {
        this.heeftDiensts.remove(dienst);
        dienst.setHeeftOnderwerp(null);
        return this;
    }

    public Set<Onderwerp> getHoofdonderwerpOnderwerp2s() {
        return this.hoofdonderwerpOnderwerp2s;
    }

    public void setHoofdonderwerpOnderwerp2s(Set<Onderwerp> onderwerps) {
        if (this.hoofdonderwerpOnderwerp2s != null) {
            this.hoofdonderwerpOnderwerp2s.forEach(i -> i.setHoofdonderwerpOnderwerp(null));
        }
        if (onderwerps != null) {
            onderwerps.forEach(i -> i.setHoofdonderwerpOnderwerp(this));
        }
        this.hoofdonderwerpOnderwerp2s = onderwerps;
    }

    public Onderwerp hoofdonderwerpOnderwerp2s(Set<Onderwerp> onderwerps) {
        this.setHoofdonderwerpOnderwerp2s(onderwerps);
        return this;
    }

    public Onderwerp addHoofdonderwerpOnderwerp2(Onderwerp onderwerp) {
        this.hoofdonderwerpOnderwerp2s.add(onderwerp);
        onderwerp.setHoofdonderwerpOnderwerp(this);
        return this;
    }

    public Onderwerp removeHoofdonderwerpOnderwerp2(Onderwerp onderwerp) {
        this.hoofdonderwerpOnderwerp2s.remove(onderwerp);
        onderwerp.setHoofdonderwerpOnderwerp(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Onderwerp)) {
            return false;
        }
        return getId() != null && getId().equals(((Onderwerp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Onderwerp{" +
            "id=" + getId() +
            "}";
    }
}
