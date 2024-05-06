package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Klimplant.
 */
@Entity
@Table(name = "klimplant")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Klimplant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "knipfrequentie")
    private String knipfrequentie;

    @Column(name = "knipoppervlakte")
    private String knipoppervlakte;

    @Column(name = "ondersteuningsvorm")
    private String ondersteuningsvorm;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Klimplant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Klimplant hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getKnipfrequentie() {
        return this.knipfrequentie;
    }

    public Klimplant knipfrequentie(String knipfrequentie) {
        this.setKnipfrequentie(knipfrequentie);
        return this;
    }

    public void setKnipfrequentie(String knipfrequentie) {
        this.knipfrequentie = knipfrequentie;
    }

    public String getKnipoppervlakte() {
        return this.knipoppervlakte;
    }

    public Klimplant knipoppervlakte(String knipoppervlakte) {
        this.setKnipoppervlakte(knipoppervlakte);
        return this;
    }

    public void setKnipoppervlakte(String knipoppervlakte) {
        this.knipoppervlakte = knipoppervlakte;
    }

    public String getOndersteuningsvorm() {
        return this.ondersteuningsvorm;
    }

    public Klimplant ondersteuningsvorm(String ondersteuningsvorm) {
        this.setOndersteuningsvorm(ondersteuningsvorm);
        return this;
    }

    public void setOndersteuningsvorm(String ondersteuningsvorm) {
        this.ondersteuningsvorm = ondersteuningsvorm;
    }

    public String getType() {
        return this.type;
    }

    public Klimplant type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Klimplant)) {
            return false;
        }
        return getId() != null && getId().equals(((Klimplant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Klimplant{" +
            "id=" + getId() +
            ", hoogte='" + getHoogte() + "'" +
            ", knipfrequentie='" + getKnipfrequentie() + "'" +
            ", knipoppervlakte='" + getKnipoppervlakte() + "'" +
            ", ondersteuningsvorm='" + getOndersteuningsvorm() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
