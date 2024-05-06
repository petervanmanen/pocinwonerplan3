package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Telefoonstatus.
 */
@Entity
@Table(name = "telefoonstatus")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Telefoonstatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "contactconnectionstate", length = 20)
    private String contactconnectionstate;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftTelefoonstatus")
    @JsonIgnoreProperties(value = { "mondtuitinKlantcontacts", "heeftTelefoonstatus", "heeftTelefoononderwerp" }, allowSetters = true)
    private Set<Telefoontje> heeftTelefoontjes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Telefoonstatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactconnectionstate() {
        return this.contactconnectionstate;
    }

    public Telefoonstatus contactconnectionstate(String contactconnectionstate) {
        this.setContactconnectionstate(contactconnectionstate);
        return this;
    }

    public void setContactconnectionstate(String contactconnectionstate) {
        this.contactconnectionstate = contactconnectionstate;
    }

    public String getStatus() {
        return this.status;
    }

    public Telefoonstatus status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Telefoontje> getHeeftTelefoontjes() {
        return this.heeftTelefoontjes;
    }

    public void setHeeftTelefoontjes(Set<Telefoontje> telefoontjes) {
        if (this.heeftTelefoontjes != null) {
            this.heeftTelefoontjes.forEach(i -> i.setHeeftTelefoonstatus(null));
        }
        if (telefoontjes != null) {
            telefoontjes.forEach(i -> i.setHeeftTelefoonstatus(this));
        }
        this.heeftTelefoontjes = telefoontjes;
    }

    public Telefoonstatus heeftTelefoontjes(Set<Telefoontje> telefoontjes) {
        this.setHeeftTelefoontjes(telefoontjes);
        return this;
    }

    public Telefoonstatus addHeeftTelefoontje(Telefoontje telefoontje) {
        this.heeftTelefoontjes.add(telefoontje);
        telefoontje.setHeeftTelefoonstatus(this);
        return this;
    }

    public Telefoonstatus removeHeeftTelefoontje(Telefoontje telefoontje) {
        this.heeftTelefoontjes.remove(telefoontje);
        telefoontje.setHeeftTelefoonstatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Telefoonstatus)) {
            return false;
        }
        return getId() != null && getId().equals(((Telefoonstatus) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Telefoonstatus{" +
            "id=" + getId() +
            ", contactconnectionstate='" + getContactconnectionstate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
