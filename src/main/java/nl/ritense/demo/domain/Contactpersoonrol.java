package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Contactpersoonrol.
 */
@Entity
@Table(name = "contactpersoonrol")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contactpersoonrol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "contactpersoonemailadres")
    private String contactpersoonemailadres;

    @Column(name = "contactpersoonfunctie")
    private String contactpersoonfunctie;

    @Column(name = "contactpersoonnaam")
    private String contactpersoonnaam;

    @Size(max = 20)
    @Column(name = "contactpersoontelefoonnummer", length = 20)
    private String contactpersoontelefoonnummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contactpersoonrol id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactpersoonemailadres() {
        return this.contactpersoonemailadres;
    }

    public Contactpersoonrol contactpersoonemailadres(String contactpersoonemailadres) {
        this.setContactpersoonemailadres(contactpersoonemailadres);
        return this;
    }

    public void setContactpersoonemailadres(String contactpersoonemailadres) {
        this.contactpersoonemailadres = contactpersoonemailadres;
    }

    public String getContactpersoonfunctie() {
        return this.contactpersoonfunctie;
    }

    public Contactpersoonrol contactpersoonfunctie(String contactpersoonfunctie) {
        this.setContactpersoonfunctie(contactpersoonfunctie);
        return this;
    }

    public void setContactpersoonfunctie(String contactpersoonfunctie) {
        this.contactpersoonfunctie = contactpersoonfunctie;
    }

    public String getContactpersoonnaam() {
        return this.contactpersoonnaam;
    }

    public Contactpersoonrol contactpersoonnaam(String contactpersoonnaam) {
        this.setContactpersoonnaam(contactpersoonnaam);
        return this;
    }

    public void setContactpersoonnaam(String contactpersoonnaam) {
        this.contactpersoonnaam = contactpersoonnaam;
    }

    public String getContactpersoontelefoonnummer() {
        return this.contactpersoontelefoonnummer;
    }

    public Contactpersoonrol contactpersoontelefoonnummer(String contactpersoontelefoonnummer) {
        this.setContactpersoontelefoonnummer(contactpersoontelefoonnummer);
        return this;
    }

    public void setContactpersoontelefoonnummer(String contactpersoontelefoonnummer) {
        this.contactpersoontelefoonnummer = contactpersoontelefoonnummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contactpersoonrol)) {
            return false;
        }
        return getId() != null && getId().equals(((Contactpersoonrol) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contactpersoonrol{" +
            "id=" + getId() +
            ", contactpersoonemailadres='" + getContactpersoonemailadres() + "'" +
            ", contactpersoonfunctie='" + getContactpersoonfunctie() + "'" +
            ", contactpersoonnaam='" + getContactpersoonnaam() + "'" +
            ", contactpersoontelefoonnummer='" + getContactpersoontelefoonnummer() + "'" +
            "}";
    }
}
