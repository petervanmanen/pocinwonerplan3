package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Inkooppakket.
 */
@Entity
@Table(name = "inkooppakket")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inkooppakket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "naam")
    private String naam;

    @Column(name = "type")
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftInkooppakket")
    @JsonIgnoreProperties(
        value = {
            "betreftContract",
            "oorspronkelijkInkooporder",
            "hoortbijWerkbons",
            "gerelateerdInkooporders",
            "heeftInkooppakket",
            "verplichtingaanLeverancier",
            "wordtgeschrevenopHoofdrekenings",
            "oorspronkelijkInkooporder2",
            "gerelateerdInkooporder2",
            "gedektviaFactuurs",
            "heeftKostenplaats",
        },
        allowSetters = true
    )
    private Set<Inkooporder> heeftInkooporders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inkooppakket id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Inkooppakket code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNaam() {
        return this.naam;
    }

    public Inkooppakket naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getType() {
        return this.type;
    }

    public Inkooppakket type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Inkooporder> getHeeftInkooporders() {
        return this.heeftInkooporders;
    }

    public void setHeeftInkooporders(Set<Inkooporder> inkooporders) {
        if (this.heeftInkooporders != null) {
            this.heeftInkooporders.forEach(i -> i.setHeeftInkooppakket(null));
        }
        if (inkooporders != null) {
            inkooporders.forEach(i -> i.setHeeftInkooppakket(this));
        }
        this.heeftInkooporders = inkooporders;
    }

    public Inkooppakket heeftInkooporders(Set<Inkooporder> inkooporders) {
        this.setHeeftInkooporders(inkooporders);
        return this;
    }

    public Inkooppakket addHeeftInkooporder(Inkooporder inkooporder) {
        this.heeftInkooporders.add(inkooporder);
        inkooporder.setHeeftInkooppakket(this);
        return this;
    }

    public Inkooppakket removeHeeftInkooporder(Inkooporder inkooporder) {
        this.heeftInkooporders.remove(inkooporder);
        inkooporder.setHeeftInkooppakket(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inkooppakket)) {
            return false;
        }
        return getId() != null && getId().equals(((Inkooppakket) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inkooppakket{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", naam='" + getNaam() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
