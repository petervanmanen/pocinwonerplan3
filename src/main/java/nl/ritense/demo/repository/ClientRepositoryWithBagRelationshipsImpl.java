package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ClientRepositoryWithBagRelationshipsImpl implements ClientRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String CLIENTS_PARAMETER = "clients";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Client> fetchBagRelationships(Optional<Client> client) {
        return client
            .map(this::fetchHeeftrelatieRelaties)
            .map(this::fetchVoorzieningbijstandspartijInkomensvoorzienings)
            .map(this::fetchMaaktonderdeeluitvanHuishoudens)
            .map(this::fetchHeefttaalniveauTaalniveaus);
    }

    @Override
    public Page<Client> fetchBagRelationships(Page<Client> clients) {
        return new PageImpl<>(fetchBagRelationships(clients.getContent()), clients.getPageable(), clients.getTotalElements());
    }

    @Override
    public List<Client> fetchBagRelationships(List<Client> clients) {
        return Optional.of(clients)
            .map(this::fetchHeeftrelatieRelaties)
            .map(this::fetchVoorzieningbijstandspartijInkomensvoorzienings)
            .map(this::fetchMaaktonderdeeluitvanHuishoudens)
            .map(this::fetchHeefttaalniveauTaalniveaus)
            .orElse(Collections.emptyList());
    }

    Client fetchHeeftrelatieRelaties(Client result) {
        return entityManager
            .createQuery("select client from Client client left join fetch client.heeftrelatieRelaties where client.id = :id", Client.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Client> fetchHeeftrelatieRelaties(List<Client> clients) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, clients.size()).forEach(index -> order.put(clients.get(index).getId(), index));
        List<Client> result = entityManager
            .createQuery(
                "select client from Client client left join fetch client.heeftrelatieRelaties where client in :clients",
                Client.class
            )
            .setParameter(CLIENTS_PARAMETER, clients)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Client fetchVoorzieningbijstandspartijInkomensvoorzienings(Client result) {
        return entityManager
            .createQuery(
                "select client from Client client left join fetch client.voorzieningbijstandspartijInkomensvoorzienings where client.id = :id",
                Client.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Client> fetchVoorzieningbijstandspartijInkomensvoorzienings(List<Client> clients) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, clients.size()).forEach(index -> order.put(clients.get(index).getId(), index));
        List<Client> result = entityManager
            .createQuery(
                "select client from Client client left join fetch client.voorzieningbijstandspartijInkomensvoorzienings where client in :clients",
                Client.class
            )
            .setParameter(CLIENTS_PARAMETER, clients)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Client fetchMaaktonderdeeluitvanHuishoudens(Client result) {
        return entityManager
            .createQuery(
                "select client from Client client left join fetch client.maaktonderdeeluitvanHuishoudens where client.id = :id",
                Client.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Client> fetchMaaktonderdeeluitvanHuishoudens(List<Client> clients) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, clients.size()).forEach(index -> order.put(clients.get(index).getId(), index));
        List<Client> result = entityManager
            .createQuery(
                "select client from Client client left join fetch client.maaktonderdeeluitvanHuishoudens where client in :clients",
                Client.class
            )
            .setParameter(CLIENTS_PARAMETER, clients)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Client fetchHeefttaalniveauTaalniveaus(Client result) {
        return entityManager
            .createQuery(
                "select client from Client client left join fetch client.heefttaalniveauTaalniveaus where client.id = :id",
                Client.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Client> fetchHeefttaalniveauTaalniveaus(List<Client> clients) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, clients.size()).forEach(index -> order.put(clients.get(index).getId(), index));
        List<Client> result = entityManager
            .createQuery(
                "select client from Client client left join fetch client.heefttaalniveauTaalniveaus where client in :clients",
                Client.class
            )
            .setParameter(CLIENTS_PARAMETER, clients)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
