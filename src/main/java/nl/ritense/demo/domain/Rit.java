package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rit.
 */
@Entity
@Table(name = "rit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eindtijd")
    private String eindtijd;

    @Column(name = "ritcode")
    private String ritcode;

    @Column(name = "starttijd")
    private String starttijd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftRit")
    @JsonIgnoreProperties(value = { "gelostContainer", "gestoptopLocatie", "heeftRit" }, allowSetters = true)
    private Set<Ophaalmoment> heeftOphaalmoments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "geschiktvoorContainertypes", "uitgevoerdmetRits" }, allowSetters = true)
    private Vuilniswagen uitgevoerdmetVuilniswagen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEindtijd() {
        return this.eindtijd;
    }

    public Rit eindtijd(String eindtijd) {
        this.setEindtijd(eindtijd);
        return this;
    }

    public void setEindtijd(String eindtijd) {
        this.eindtijd = eindtijd;
    }

    public String getRitcode() {
        return this.ritcode;
    }

    public Rit ritcode(String ritcode) {
        this.setRitcode(ritcode);
        return this;
    }

    public void setRitcode(String ritcode) {
        this.ritcode = ritcode;
    }

    public String getStarttijd() {
        return this.starttijd;
    }

    public Rit starttijd(String starttijd) {
        this.setStarttijd(starttijd);
        return this;
    }

    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    public Set<Ophaalmoment> getHeeftOphaalmoments() {
        return this.heeftOphaalmoments;
    }

    public void setHeeftOphaalmoments(Set<Ophaalmoment> ophaalmoments) {
        if (this.heeftOphaalmoments != null) {
            this.heeftOphaalmoments.forEach(i -> i.setHeeftRit(null));
        }
        if (ophaalmoments != null) {
            ophaalmoments.forEach(i -> i.setHeeftRit(this));
        }
        this.heeftOphaalmoments = ophaalmoments;
    }

    public Rit heeftOphaalmoments(Set<Ophaalmoment> ophaalmoments) {
        this.setHeeftOphaalmoments(ophaalmoments);
        return this;
    }

    public Rit addHeeftOphaalmoment(Ophaalmoment ophaalmoment) {
        this.heeftOphaalmoments.add(ophaalmoment);
        ophaalmoment.setHeeftRit(this);
        return this;
    }

    public Rit removeHeeftOphaalmoment(Ophaalmoment ophaalmoment) {
        this.heeftOphaalmoments.remove(ophaalmoment);
        ophaalmoment.setHeeftRit(null);
        return this;
    }

    public Vuilniswagen getUitgevoerdmetVuilniswagen() {
        return this.uitgevoerdmetVuilniswagen;
    }

    public void setUitgevoerdmetVuilniswagen(Vuilniswagen vuilniswagen) {
        this.uitgevoerdmetVuilniswagen = vuilniswagen;
    }

    public Rit uitgevoerdmetVuilniswagen(Vuilniswagen vuilniswagen) {
        this.setUitgevoerdmetVuilniswagen(vuilniswagen);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rit)) {
            return false;
        }
        return getId() != null && getId().equals(((Rit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rit{" +
            "id=" + getId() +
            ", eindtijd='" + getEindtijd() + "'" +
            ", ritcode='" + getRitcode() + "'" +
            ", starttijd='" + getStarttijd() + "'" +
            "}";
    }
}
