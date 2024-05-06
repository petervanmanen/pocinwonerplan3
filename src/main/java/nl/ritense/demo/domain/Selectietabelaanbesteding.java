package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Selectietabelaanbesteding.
 */
@Entity
@Table(name = "selectietabelaanbesteding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Selectietabelaanbesteding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanbestedingsoort")
    private String aanbestedingsoort;

    @Column(name = "drempelbedragtot", precision = 21, scale = 2)
    private BigDecimal drempelbedragtot;

    @Column(name = "drempelbedragvanaf", precision = 21, scale = 2)
    private BigDecimal drempelbedragvanaf;

    @Column(name = "opdrachtcategorie")
    private String opdrachtcategorie;

    @Column(name = "openbaar")
    private String openbaar;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Selectietabelaanbesteding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanbestedingsoort() {
        return this.aanbestedingsoort;
    }

    public Selectietabelaanbesteding aanbestedingsoort(String aanbestedingsoort) {
        this.setAanbestedingsoort(aanbestedingsoort);
        return this;
    }

    public void setAanbestedingsoort(String aanbestedingsoort) {
        this.aanbestedingsoort = aanbestedingsoort;
    }

    public BigDecimal getDrempelbedragtot() {
        return this.drempelbedragtot;
    }

    public Selectietabelaanbesteding drempelbedragtot(BigDecimal drempelbedragtot) {
        this.setDrempelbedragtot(drempelbedragtot);
        return this;
    }

    public void setDrempelbedragtot(BigDecimal drempelbedragtot) {
        this.drempelbedragtot = drempelbedragtot;
    }

    public BigDecimal getDrempelbedragvanaf() {
        return this.drempelbedragvanaf;
    }

    public Selectietabelaanbesteding drempelbedragvanaf(BigDecimal drempelbedragvanaf) {
        this.setDrempelbedragvanaf(drempelbedragvanaf);
        return this;
    }

    public void setDrempelbedragvanaf(BigDecimal drempelbedragvanaf) {
        this.drempelbedragvanaf = drempelbedragvanaf;
    }

    public String getOpdrachtcategorie() {
        return this.opdrachtcategorie;
    }

    public Selectietabelaanbesteding opdrachtcategorie(String opdrachtcategorie) {
        this.setOpdrachtcategorie(opdrachtcategorie);
        return this;
    }

    public void setOpdrachtcategorie(String opdrachtcategorie) {
        this.opdrachtcategorie = opdrachtcategorie;
    }

    public String getOpenbaar() {
        return this.openbaar;
    }

    public Selectietabelaanbesteding openbaar(String openbaar) {
        this.setOpenbaar(openbaar);
        return this;
    }

    public void setOpenbaar(String openbaar) {
        this.openbaar = openbaar;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Selectietabelaanbesteding)) {
            return false;
        }
        return getId() != null && getId().equals(((Selectietabelaanbesteding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Selectietabelaanbesteding{" +
            "id=" + getId() +
            ", aanbestedingsoort='" + getAanbestedingsoort() + "'" +
            ", drempelbedragtot=" + getDrempelbedragtot() +
            ", drempelbedragvanaf=" + getDrempelbedragvanaf() +
            ", opdrachtcategorie='" + getOpdrachtcategorie() + "'" +
            ", openbaar='" + getOpenbaar() + "'" +
            "}";
    }
}
