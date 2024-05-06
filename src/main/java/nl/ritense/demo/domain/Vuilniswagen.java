package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vuilniswagen.
 */
@Entity
@Table(name = "vuilniswagen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vuilniswagen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Size(max = 8)
    @Column(name = "kenteken", length = 8)
    private String kenteken;

    @Column(name = "type")
    private String type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_vuilniswagen__geschiktvoor_containertype",
        joinColumns = @JoinColumn(name = "vuilniswagen_id"),
        inverseJoinColumns = @JoinColumn(name = "geschiktvoor_containertype_id")
    )
    @JsonIgnoreProperties(value = { "soortContainers", "betreftMeldings", "geschiktvoorVuilniswagens" }, allowSetters = true)
    private Set<Containertype> geschiktvoorContainertypes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uitgevoerdmetVuilniswagen")
    @JsonIgnoreProperties(value = { "heeftOphaalmoments", "uitgevoerdmetVuilniswagen" }, allowSetters = true)
    private Set<Rit> uitgevoerdmetRits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vuilniswagen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Vuilniswagen code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKenteken() {
        return this.kenteken;
    }

    public Vuilniswagen kenteken(String kenteken) {
        this.setKenteken(kenteken);
        return this;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public String getType() {
        return this.type;
    }

    public Vuilniswagen type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Containertype> getGeschiktvoorContainertypes() {
        return this.geschiktvoorContainertypes;
    }

    public void setGeschiktvoorContainertypes(Set<Containertype> containertypes) {
        this.geschiktvoorContainertypes = containertypes;
    }

    public Vuilniswagen geschiktvoorContainertypes(Set<Containertype> containertypes) {
        this.setGeschiktvoorContainertypes(containertypes);
        return this;
    }

    public Vuilniswagen addGeschiktvoorContainertype(Containertype containertype) {
        this.geschiktvoorContainertypes.add(containertype);
        return this;
    }

    public Vuilniswagen removeGeschiktvoorContainertype(Containertype containertype) {
        this.geschiktvoorContainertypes.remove(containertype);
        return this;
    }

    public Set<Rit> getUitgevoerdmetRits() {
        return this.uitgevoerdmetRits;
    }

    public void setUitgevoerdmetRits(Set<Rit> rits) {
        if (this.uitgevoerdmetRits != null) {
            this.uitgevoerdmetRits.forEach(i -> i.setUitgevoerdmetVuilniswagen(null));
        }
        if (rits != null) {
            rits.forEach(i -> i.setUitgevoerdmetVuilniswagen(this));
        }
        this.uitgevoerdmetRits = rits;
    }

    public Vuilniswagen uitgevoerdmetRits(Set<Rit> rits) {
        this.setUitgevoerdmetRits(rits);
        return this;
    }

    public Vuilniswagen addUitgevoerdmetRit(Rit rit) {
        this.uitgevoerdmetRits.add(rit);
        rit.setUitgevoerdmetVuilniswagen(this);
        return this;
    }

    public Vuilniswagen removeUitgevoerdmetRit(Rit rit) {
        this.uitgevoerdmetRits.remove(rit);
        rit.setUitgevoerdmetVuilniswagen(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vuilniswagen)) {
            return false;
        }
        return getId() != null && getId().equals(((Vuilniswagen) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vuilniswagen{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", kenteken='" + getKenteken() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
