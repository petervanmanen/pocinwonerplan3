package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hotel.
 */
@Entity
@Table(name = "hotel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalkamers")
    private String aantalkamers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftHotel")
    @JsonIgnoreProperties(value = { "heeftHotel" }, allowSetters = true)
    private Set<Hotelbezoek> heeftHotelbezoeks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hotel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalkamers() {
        return this.aantalkamers;
    }

    public Hotel aantalkamers(String aantalkamers) {
        this.setAantalkamers(aantalkamers);
        return this;
    }

    public void setAantalkamers(String aantalkamers) {
        this.aantalkamers = aantalkamers;
    }

    public Set<Hotelbezoek> getHeeftHotelbezoeks() {
        return this.heeftHotelbezoeks;
    }

    public void setHeeftHotelbezoeks(Set<Hotelbezoek> hotelbezoeks) {
        if (this.heeftHotelbezoeks != null) {
            this.heeftHotelbezoeks.forEach(i -> i.setHeeftHotel(null));
        }
        if (hotelbezoeks != null) {
            hotelbezoeks.forEach(i -> i.setHeeftHotel(this));
        }
        this.heeftHotelbezoeks = hotelbezoeks;
    }

    public Hotel heeftHotelbezoeks(Set<Hotelbezoek> hotelbezoeks) {
        this.setHeeftHotelbezoeks(hotelbezoeks);
        return this;
    }

    public Hotel addHeeftHotelbezoek(Hotelbezoek hotelbezoek) {
        this.heeftHotelbezoeks.add(hotelbezoek);
        hotelbezoek.setHeeftHotel(this);
        return this;
    }

    public Hotel removeHeeftHotelbezoek(Hotelbezoek hotelbezoek) {
        this.heeftHotelbezoeks.remove(hotelbezoek);
        hotelbezoek.setHeeftHotel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hotel)) {
            return false;
        }
        return getId() != null && getId().equals(((Hotel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hotel{" +
            "id=" + getId() +
            ", aantalkamers='" + getAantalkamers() + "'" +
            "}";
    }
}
