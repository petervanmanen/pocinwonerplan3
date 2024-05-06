package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Koopsomkadastraleonroerendezaak.
 */
@Entity
@Table(name = "koopsomkadastraleonroerendezaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Koopsomkadastraleonroerendezaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumtransactie")
    private String datumtransactie;

    @Column(name = "koopsom")
    private String koopsom;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Koopsomkadastraleonroerendezaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumtransactie() {
        return this.datumtransactie;
    }

    public Koopsomkadastraleonroerendezaak datumtransactie(String datumtransactie) {
        this.setDatumtransactie(datumtransactie);
        return this;
    }

    public void setDatumtransactie(String datumtransactie) {
        this.datumtransactie = datumtransactie;
    }

    public String getKoopsom() {
        return this.koopsom;
    }

    public Koopsomkadastraleonroerendezaak koopsom(String koopsom) {
        this.setKoopsom(koopsom);
        return this;
    }

    public void setKoopsom(String koopsom) {
        this.koopsom = koopsom;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Koopsomkadastraleonroerendezaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Koopsomkadastraleonroerendezaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Koopsomkadastraleonroerendezaak{" +
            "id=" + getId() +
            ", datumtransactie='" + getDatumtransactie() + "'" +
            ", koopsom='" + getKoopsom() + "'" +
            "}";
    }
}
