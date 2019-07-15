package com.visma.fishing.services.impl;

import com.visma.fishing.model.Arrival;
import com.visma.fishing.services.ArrivalService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.visma.fishing.queries.Queries.ARRIVAL_FIND_BY_DATE;
import static com.visma.fishing.queries.Queries.ARRIVAL_FIND_BY_PORT;

@Transactional
@Stateless
public class ArrivalServiceEJB implements ArrivalService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Arrival> findAll() {
        TypedQuery<Arrival> q = em.createNamedQuery("arrival.findAll", Arrival.class);
        return q.getResultList();
    }

    @Override
    public Optional<Arrival> findById(String id) {
        return Optional.ofNullable(em.find(Arrival.class, id));
    }

    @Override
    public Arrival create(Arrival arrival) {
        em.persist(arrival);
        return arrival;
    }

    @Override
    public Optional<Arrival> updateArrivalById(String id, Arrival arrival) {
        Arrival entity = em.find(Arrival.class, id);
        if (entity == null) {
            return Optional.empty();
        }
        entity.setPort(arrival.getPort());
        em.merge(entity);
        return Optional.of(entity);

    }

    @Override
    public void remove(String id) {
        Arrival entity = em.find(Arrival.class, id);
        em.remove(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Arrival> findByPort(String port){
        return em.createNativeQuery(ARRIVAL_FIND_BY_PORT, Arrival.class)
                .setParameter(1, port)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Arrival> findByPeriod(Date start, Date end) {
        return em.createNativeQuery(ARRIVAL_FIND_BY_DATE, Arrival.class)
                .setParameter(1, start)
                .setParameter(2, end)
                .getResultList();
    }

}
