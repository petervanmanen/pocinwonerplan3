package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Verkeerstelling.
 */
@Entity
@Table(name = "verkeerstelling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verkeerstelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantal")
    private String aantal;

    @Column(name = "tijdtot")
    private String tijdtot;

    @Column(name = "tijdvanaf")
    private String tijdvanaf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "gegenereerddoorVerkeerstellings" }, allowSetters = true)
    private Sensor gegenereerddoorSensor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verkeerstelling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Verkeerstelling aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public String getTijdtot() {
        return this.tijdtot;
    }

    public Verkeerstelling tijdtot(String tijdtot) {
        this.setTijdtot(tijdtot);
        return this;
    }

    public void setTijdtot(String tijdtot) {
        this.tijdtot = tijdtot;
    }

    public String getTijdvanaf() {
        return this.tijdvanaf;
    }

    public Verkeerstelling tijdvanaf(String tijdvanaf) {
        this.setTijdvanaf(tijdvanaf);
        return this;
    }

    public void setTijdvanaf(String tijdvanaf) {
        this.tijdvanaf = tijdvanaf;
    }

    public Sensor getGegenereerddoorSensor() {
        return this.gegenereerddoorSensor;
    }

    public void setGegenereerddoorSensor(Sensor sensor) {
        this.gegenereerddoorSensor = sensor;
    }

    public Verkeerstelling gegenereerddoorSensor(Sensor sensor) {
        this.setGegenereerddoorSensor(sensor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verkeerstelling)) {
            return false;
        }
        return getId() != null && getId().equals(((Verkeerstelling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verkeerstelling{" +
            "id=" + getId() +
            ", aantal='" + getAantal() + "'" +
            ", tijdtot='" + getTijdtot() + "'" +
            ", tijdvanaf='" + getTijdvanaf() + "'" +
            "}";
    }
}
