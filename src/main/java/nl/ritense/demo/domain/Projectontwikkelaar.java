package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Projectontwikkelaar.
 */
@Entity
@Table(name = "projectontwikkelaar")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projectontwikkelaar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adres")
    private String adres;

    @Column(name = "naam")
    private String naam;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_projectontwikkelaar__heeft_plan",
        joinColumns = @JoinColumn(name = "projectontwikkelaar_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_plan_id")
    )
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
    private Set<Plan> heeftPlans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projectontwikkelaar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdres() {
        return this.adres;
    }

    public Projectontwikkelaar adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getNaam() {
        return this.naam;
    }

    public Projectontwikkelaar naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Plan> getHeeftPlans() {
        return this.heeftPlans;
    }

    public void setHeeftPlans(Set<Plan> plans) {
        this.heeftPlans = plans;
    }

    public Projectontwikkelaar heeftPlans(Set<Plan> plans) {
        this.setHeeftPlans(plans);
        return this;
    }

    public Projectontwikkelaar addHeeftPlan(Plan plan) {
        this.heeftPlans.add(plan);
        return this;
    }

    public Projectontwikkelaar removeHeeftPlan(Plan plan) {
        this.heeftPlans.remove(plan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projectontwikkelaar)) {
            return false;
        }
        return getId() != null && getId().equals(((Projectontwikkelaar) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projectontwikkelaar{" +
            "id=" + getId() +
            ", adres='" + getAdres() + "'" +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
