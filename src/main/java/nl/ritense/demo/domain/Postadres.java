package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Postadres.
 */
@Entity
@Table(name = "postadres")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Postadres implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ligtinNummeraanduidings", "heeftalscorrespondentieadrespostadresinPostadres" }, allowSetters = true)
    private Woonplaats heeftalscorrespondentieadrespostadresinWoonplaats;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Postadres id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostadrestype() {
        return this.postadrestype;
    }

    public Postadres postadrestype(String postadrestype) {
        this.setPostadrestype(postadrestype);
        return this;
    }

    public void setPostadrestype(String postadrestype) {
        this.postadrestype = postadrestype;
    }

    public String getPostbusofantwoordnummer() {
        return this.postbusofantwoordnummer;
    }

    public Postadres postbusofantwoordnummer(String postbusofantwoordnummer) {
        this.setPostbusofantwoordnummer(postbusofantwoordnummer);
        return this;
    }

    public void setPostbusofantwoordnummer(String postbusofantwoordnummer) {
        this.postbusofantwoordnummer = postbusofantwoordnummer;
    }

    public String getPostcodepostadres() {
        return this.postcodepostadres;
    }

    public Postadres postcodepostadres(String postcodepostadres) {
        this.setPostcodepostadres(postcodepostadres);
        return this;
    }

    public void setPostcodepostadres(String postcodepostadres) {
        this.postcodepostadres = postcodepostadres;
    }

    public Woonplaats getHeeftalscorrespondentieadrespostadresinWoonplaats() {
        return this.heeftalscorrespondentieadrespostadresinWoonplaats;
    }

    public void setHeeftalscorrespondentieadrespostadresinWoonplaats(Woonplaats woonplaats) {
        this.heeftalscorrespondentieadrespostadresinWoonplaats = woonplaats;
    }

    public Postadres heeftalscorrespondentieadrespostadresinWoonplaats(Woonplaats woonplaats) {
        this.setHeeftalscorrespondentieadrespostadresinWoonplaats(woonplaats);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Postadres)) {
            return false;
        }
        return getId() != null && getId().equals(((Postadres) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Postadres{" +
            "id=" + getId() +
            ", postadrestype='" + getPostadrestype() + "'" +
            ", postbusofantwoordnummer='" + getPostbusofantwoordnummer() + "'" +
            ", postcodepostadres='" + getPostcodepostadres() + "'" +
            "}";
    }
}
