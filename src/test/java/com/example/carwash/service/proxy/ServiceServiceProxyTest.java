package com.example.carwash.service.proxy;

import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.dtos.ServiceAddDTO;
import com.example.carwash.model.entity.Service;
import com.example.carwash.model.view.ServiceIndexView;
import com.example.carwash.model.view.ServiceView;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.ServiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class ServiceServiceProxyTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CustomMapper customMapper;

    @Mock
    private ServiceServiceImpl serviceServiceImpl;

    @InjectMocks
    private ServiceServiceProxy serviceServiceProxy;

    private List<Service> services = new ArrayList<Service>() {
        {
        add(new Service("Test", "asdfgh", "htps://www.google.com", 12.00, new ArrayList<>()));
        add(new Service("Test2", "asdfgh", "htps://www.google.com", 13.00, new ArrayList<>()));
        add(new Service("Test3", "asdfgh", "htps://www.google.com", 14.00, new ArrayList<>()));
        }
    };

    private List<AppointmentServiceDTO> appointmentServiceDTOS;
    private List<ServiceIndexView> serviceIndexViews;
    private List<ServiceView> serviceViews;


    @BeforeEach
    public void setUp() {
        serviceRepository = org.mockito.Mockito.mock(ServiceRepository.class);
        customMapper = org.mockito.Mockito.mock(CustomMapper.class);
        serviceServiceImpl = new ServiceServiceImpl(serviceRepository, customMapper);
        serviceServiceProxy = new ServiceServiceProxy(serviceServiceImpl);
        appointmentServiceDTOS = new java.util.ArrayList<>();
        serviceIndexViews = new java.util.ArrayList<>();
        serviceViews = new java.util.ArrayList<>();
        getAppointmentServiceDTOS();
        getServiceIndexViews();
        getServiceViews();
    }

    @Test
    public void getAllServicesForAppointmentPage() {
        getAllService();
        List<AppointmentServiceDTO> appointmentServiceDTOS = serviceServiceProxy.getAllServicesForAppointmentPage();
        Assertions.assertEquals(3, serviceServiceProxy.allServices());
        Assertions.assertEquals(3, appointmentServiceDTOS.size());
        Assertions.assertEquals(appointmentServiceDTOS, serviceServiceProxy.getAllServicesForAppointmentPage());
    }

    @Test
    public void getAllServicesForIndexPage() {
        getAllService();
        List<ServiceIndexView> serviceIndexViews = serviceServiceProxy.getAllServicesForIndexPage();
        Assertions.assertEquals(3, serviceServiceProxy.allServices());
        Assertions.assertEquals(3, serviceIndexViews.size());
        Assertions.assertEquals(serviceIndexViews, serviceServiceProxy.getAllServicesForIndexPage());
    }

    @Test
    public void getAllServicesForServicesPage() {
        getAllService();
        List<ServiceView> serviceViews = serviceServiceProxy.getAllServicesForServicesPage();
        Assertions.assertEquals(3, serviceServiceProxy.allServices());
        Assertions.assertEquals(3, serviceViews.size());
        Assertions.assertEquals(serviceViews, serviceServiceProxy.getAllServicesForServicesPage());
    }

    @Test
    public void addService() {
        getAllService();
        ServiceAddDTO serviceAddDTO = new ServiceAddDTO("Test4", "asdfgh", "htps://www.google.com", 12.00);
        serviceServiceProxy.addService(serviceAddDTO);
        Service service = new Service(serviceAddDTO.getName(), serviceAddDTO.getDescription(), serviceAddDTO.getUrlVideo(), serviceAddDTO.getPrice(), new ArrayList<>());
        services.add(service);
        when(serviceRepository.save(service)).thenReturn(service);
        when(serviceRepository.findAll()).thenReturn(services);
        serviceServiceProxy.saveAll(services);

        Assertions.assertEquals(4, serviceServiceProxy.allServices());
    }



    private void getAppointmentServiceDTOS() {
        AppointmentServiceDTO appointmentServiceDTO = new AppointmentServiceDTO();
        appointmentServiceDTO.setName("Test");
        appointmentServiceDTO.setPrice("12.00");
        appointmentServiceDTOS.add(appointmentServiceDTO);
        appointmentServiceDTO = new AppointmentServiceDTO();
        appointmentServiceDTO.setName("Test2");
        appointmentServiceDTO.setPrice("13.00");
        appointmentServiceDTOS.add(appointmentServiceDTO);
        appointmentServiceDTO = new AppointmentServiceDTO();
        appointmentServiceDTO.setName("Test3");
        appointmentServiceDTO.setPrice("14.00");
        appointmentServiceDTOS.add(appointmentServiceDTO);
    }

    private void getServiceIndexViews() {
        ServiceIndexView serviceIndexView = new ServiceIndexView();
        serviceIndexView.setName("Test");
        serviceIndexView.setDescription("asdfgh");
        serviceIndexViews.add(serviceIndexView);
        serviceIndexView = new ServiceIndexView();
        serviceIndexView.setName("Test2");
        serviceIndexView.setDescription("asdfgh");
        serviceIndexViews.add(serviceIndexView);
        serviceIndexView = new ServiceIndexView();
        serviceIndexView.setName("Test3");
        serviceIndexView.setDescription("asdfgh");
        serviceIndexViews.add(serviceIndexView);
    }

    private void getServiceViews() {
        ServiceView serviceView = new ServiceView();
        serviceView.setName("Test");
        serviceView.setId(1L);
        serviceView.setDescription("asdfgh");
        serviceView.setPrice("12.00");
        serviceView.setUrlVideo("htps://www.google.com");
        serviceViews.add(serviceView);
        serviceView = new ServiceView();
        serviceView.setName("Test2");
        serviceView.setId(2L);
        serviceView.setDescription("asdfgh");
        serviceView.setPrice("13.00");
        serviceView.setUrlVideo("htps://www.google.com");
        serviceViews.add(serviceView);
        serviceView = new ServiceView();
        serviceView.setName("Test3");
        serviceView.setId(3L);
        serviceView.setDescription("asdfgh");
        serviceView.setPrice("14.00");
        serviceView.setUrlVideo("htps://www.google.com");
        serviceViews.add(serviceView);
    }

    private void getAllService() {
        when(serviceRepository.saveAll(services)).thenReturn(services);
        when(serviceRepository.findAll()).thenReturn(services);
        when(customMapper.serviceToServiceView(services.get(0))).thenReturn(serviceViews.get(0));
        when(customMapper.serviceToServiceView(services.get(1))).thenReturn(serviceViews.get(1));
        when(customMapper.serviceToServiceView(services.get(2))).thenReturn(serviceViews.get(2));
        when(customMapper.serviceToServiceIndexView(services.get(0))).thenReturn(serviceIndexViews.get(0));
        when(customMapper.serviceToServiceIndexView(services.get(1))).thenReturn(serviceIndexViews.get(1));
        when(customMapper.serviceToServiceIndexView(services.get(2))).thenReturn(serviceIndexViews.get(2));
        when(customMapper.appointmentToAppointmentServiceDTO(services.get(0))).thenReturn(appointmentServiceDTOS.get(0));
        when(customMapper.appointmentToAppointmentServiceDTO(services.get(1))).thenReturn(appointmentServiceDTOS.get(1));
        when(customMapper.appointmentToAppointmentServiceDTO(services.get(2))).thenReturn(appointmentServiceDTOS.get(2));
    }
}
