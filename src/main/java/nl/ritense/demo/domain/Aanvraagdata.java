package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Aanvraagdata.
 */
@Entity
@Table(name = "aanvraagdata")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aanvraagdata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private String data;

    @Column(name = "veld")
    private String veld;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftveldenFormuliersoort", "isconformAanvraagdata" }, allowSetters = true)
    private Formuliersoortveld isconformFormuliersoortveld;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aanvraagdata id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public Aanvraagdata data(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getVeld() {
        return this.veld;
    }

    public Aanvraagdata veld(String veld) {
        this.setVeld(veld);
        return this;
    }

    public void setVeld(String veld) {
        this.veld = veld;
    }

    public Formuliersoortveld getIsconformFormuliersoortveld() {
        return this.isconformFormuliersoortveld;
    }

    public void setIsconformFormuliersoortveld(Formuliersoortveld formuliersoortveld) {
        this.isconformFormuliersoortveld = formuliersoortveld;
    }

    public Aanvraagdata isconformFormuliersoortveld(Formuliersoortveld formuliersoortveld) {
        this.setIsconformFormuliersoortveld(formuliersoortveld);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aanvraagdata)) {
            return false;
        }
        return getId() != null && getId().equals(((Aanvraagdata) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aanvraagdata{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", veld='" + getVeld() + "'" +
            "}";
    }
}
