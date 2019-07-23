package com.visma.fishing.services.impl;

import com.visma.fishing.model.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationServiceEJBTest {

    private static final String MODE = "mode";
    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String ID = "id";
    private static final String DEFAULT_VALUE = "default value";

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Configuration> query;

    @InjectMocks
    private ConfigurationServiceEJB service;

    private List<Configuration> configurationList = new ArrayList<>();

    private Configuration configuration;

    @Before
    public void init() {
        configuration = new Configuration(MODE, KEY, VALUE);
        configurationList.add(configuration);
    }

    @Test
    public void findAllShouldReturnConfigurationList() {
        when(em.createNativeQuery(anyString(), eq(Configuration.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(configurationList);

        List<Configuration> resultList = service.findAll();

        verify(em, times(1)).createNativeQuery(anyString(), eq(Configuration.class));
        assertEquals("Configuration list size should be 1.", 1, resultList.size());
    }

    @Test
    public void findByIdShouldReturnCorrectStatusCode() {
        when(em.find(eq(Configuration.class), anyString())).thenReturn(configuration);

        Optional<Configuration> optional = service.findById(ID);

        verify(em, times(1)).find(eq(Configuration.class), anyString());
        assertTrue("Should contain configuration.", optional.isPresent());
        assertEquals("Should contain configuration.", configuration, optional.get());
    }

    @Test
    public void createShouldInvokeEMPersist() {
        service.create(configuration);
        verify(em, times(1)).persist(any(Configuration.class));

    }
    @Test
    public void removeShouldInvokeEntityManagersRemove() {
        when(em.find(eq(Configuration.class), anyString())).thenReturn(configuration);

        service.remove(ID);

        verify(em, times(1)).remove(configuration);
    }

    @Test
    public void updateShouldReturnConfiguration() {
        when(em.find(eq(Configuration.class), anyString())).thenReturn(configuration);

        Optional<Configuration> optional = service.findById(ID);

        verify(em, times(1)).find(eq(Configuration.class), anyString());
        assertTrue("Should contain configuration.", optional.isPresent());
        assertEquals("Should contain configuration.", configuration, optional.get());
    }

    @Test
    public void findValueByKey() {
        when(em.createNativeQuery(anyString(), eq(Configuration.class))).thenReturn(query);
        when(query.setParameter(anyInt(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(configuration);

        String response = service.findValueByKey(KEY, DEFAULT_VALUE);
        verify(em, times(1)).createNativeQuery(anyString(), eq(Configuration.class));

        assertEquals(VALUE, response);
    }
}