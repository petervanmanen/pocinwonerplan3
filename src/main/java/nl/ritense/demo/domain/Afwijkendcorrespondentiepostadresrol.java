package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Afwijkendcorrespondentiepostadresrol.
 */
@Entity
@Table(name = "afwijkendcorrespondentiepostadresrol")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Afwijkendcorrespondentiepostadresrol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "postadrestype")
    private String postadrestype;

    @Column(name = "postbusofantwoordnummer")
    private String postbusofantwoordnummer;

    @Column(name = "postcodepostadres")
    private String postcodepostadres;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Afwijkendcorrespondentiepostadresrol id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostadrestype() {
        return this.postadrestype;
    }

    public Afwijkendcorrespondentiepostadresrol postadrestype(String postadrestype) {
        this.setPostadrestype(postadrestype);
        return this;
    }

    public void setPostadrestype(String postadrestype) {
        this.postadrestype = postadrestype;
    }

    public String getPostbusofantwoordnummer() {
        return this.postbusofantwoordnummer;
    }

    public Afwijkendcorrespondentiepostadresrol postbusofantwoordnummer(String postbusofantwoordnummer) {
        this.setPostbusofantwoordnummer(postbusofantwoordnummer);
        return this;
    }

    public void setPostbusofantwoordnummer(String postbusofantwoordnummer) {
        this.postbusofantwoordnummer = postbusofantwoordnummer;
    }

    public String getPostcodepostadres() {
        return this.postcodepostadres;
    }

    public Afwijkendcorrespondentiepostadresrol postcodepostadres(String postcodepostadres) {
        this.setPostcodepostadres(postcodepostadres);
        return this;
    }

    public void setPostcodepostadres(String postcodepostadres) {
        this.postcodepostadres = postcodepostadres;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Afwijkendcorrespondentiepostadresrol)) {
            return false;
        }
        return getId() != null && getId().equals(((Afwijkendcorrespondentiepostadresrol) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Afwijkendcorrespondentiepostadresrol{" +
            "id=" + getId() +
            ", postadrestype='" + getPostadrestype() + "'" +
            ", postbusofantwoordnummer='" + getPostbusofantwoordnummer() + "'" +
            ", postcodepostadres='" + getPostcodepostadres() + "'" +
            "}";
    }
}
