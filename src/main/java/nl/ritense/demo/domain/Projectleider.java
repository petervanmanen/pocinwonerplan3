package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Projectleider.
 */
@Entity
@Table(name = "projectleider")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projectleider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isprojectleidervanProjectleider")
    @JsonIgnoreProperties(
        value = {
            "bestaatuitGebouws",
            "binnenprogrammaProgramma",
            "isprojectleidervanProjectleider",
            "betrekkingopOmgevingsvergunnings",
            "heeftProjectontwikkelaars",
        },
        allowSetters = true
    )
    private Set<Plan> isprojectleidervanPlans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projectleider id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Projectleider naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Plan> getIsprojectleidervanPlans() {
        return this.isprojectleidervanPlans;
    }

    public void setIsprojectleidervanPlans(Set<Plan> plans) {
        if (this.isprojectleidervanPlans != null) {
            this.isprojectleidervanPlans.forEach(i -> i.setIsprojectleidervanProjectleider(null));
        }
        if (plans != null) {
            plans.forEach(i -> i.setIsprojectleidervanProjectleider(this));
        }
        this.isprojectleidervanPlans = plans;
    }

    public Projectleider isprojectleidervanPlans(Set<Plan> plans) {
        this.setIsprojectleidervanPlans(plans);
        return this;
    }

    public Projectleider addIsprojectleidervanPlan(Plan plan) {
        this.isprojectleidervanPlans.add(plan);
        plan.setIsprojectleidervanProjectleider(this);
        return this;
    }

    public Projectleider removeIsprojectleidervanPlan(Plan plan) {
        this.isprojectleidervanPlans.remove(plan);
        plan.setIsprojectleidervanProjectleider(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projectleider)) {
            return false;
        }
        return getId() != null && getId().equals(((Projectleider) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projectleider{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
