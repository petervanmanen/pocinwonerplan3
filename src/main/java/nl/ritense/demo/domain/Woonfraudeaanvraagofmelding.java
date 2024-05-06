package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Woonfraudeaanvraagofmelding.
 */
@Entity
@Table(name = "woonfraudeaanvraagofmelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Woonfraudeaanvraagofmelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adres")
    private String adres;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "locatieomschrijving")
    private String locatieomschrijving;

    @Column(name = "meldingomschrijving")
    private String meldingomschrijving;

    @Column(name = "meldingtekst")
    private String meldingtekst;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Woonfraudeaanvraagofmelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdres() {
        return this.adres;
    }

    public Woonfraudeaanvraagofmelding adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public Woonfraudeaanvraagofmelding categorie(String categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getLocatieomschrijving() {
        return this.locatieomschrijving;
    }

    public Woonfraudeaanvraagofmelding locatieomschrijving(String locatieomschrijving) {
        this.setLocatieomschrijving(locatieomschrijving);
        return this;
    }

    public void setLocatieomschrijving(String locatieomschrijving) {
        this.locatieomschrijving = locatieomschrijving;
    }

    public String getMeldingomschrijving() {
        return this.meldingomschrijving;
    }

    public Woonfraudeaanvraagofmelding meldingomschrijving(String meldingomschrijving) {
        this.setMeldingomschrijving(meldingomschrijving);
        return this;
    }

    public void setMeldingomschrijving(String meldingomschrijving) {
        this.meldingomschrijving = meldingomschrijving;
    }

    public String getMeldingtekst() {
        return this.meldingtekst;
    }

    public Woonfraudeaanvraagofmelding meldingtekst(String meldingtekst) {
        this.setMeldingtekst(meldingtekst);
        return this;
    }

    public void setMeldingtekst(String meldingtekst) {
        this.meldingtekst = meldingtekst;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Woonfraudeaanvraagofmelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Woonfraudeaanvraagofmelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Woonfraudeaanvraagofmelding{" +
            "id=" + getId() +
            ", adres='" + getAdres() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", locatieomschrijving='" + getLocatieomschrijving() + "'" +
            ", meldingomschrijving='" + getMeldingomschrijving() + "'" +
            ", meldingtekst='" + getMeldingtekst() + "'" +
            "}";
    }
}
