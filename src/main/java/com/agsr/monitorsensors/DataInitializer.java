package com.agsr.monitorsensors;

import com.agsr.monitorsensors.model.*;
import com.agsr.monitorsensors.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SensorRepository sensorRepository;
    private final SensorUnitRepository sensorUnitRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           SensorRepository sensorRepository,
                           SensorUnitRepository sensorUnitRepository,
                           SensorTypeRepository sensorTypeRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.sensorRepository = sensorRepository;
        this.sensorUnitRepository = sensorUnitRepository;
        this.sensorTypeRepository = sensorTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initRolesAndUsers();
        initSensorTypes();
        initSensorUnits();
        initSensors();
    }

    private void initRolesAndUsers() {
        Role adminRole = roleRepository.findByType(RoleType.ROLE_ADMINISTRATOR);
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setType(RoleType.ROLE_ADMINISTRATOR);
            roleRepository.save(adminRole);
        }

        Role viewerRole = roleRepository.findByType(RoleType.ROLE_VIEWER);
        if (viewerRole == null) {
            viewerRole = new Role();
            viewerRole.setType(RoleType.ROLE_VIEWER);
            roleRepository.save(viewerRole);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(List.of(adminRole));
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(List.of(viewerRole));
            userRepository.save(user);
        }
    }

    private void initSensorTypes() {
        String pressure = "Pressure";
        String voltage = "Voltage";
        String temperature = "Temperature";
        String humidity = "Humidity";
        if (sensorTypeRepository.findByValue(pressure).isEmpty()) {
            sensorTypeRepository.save(new SensorType(pressure));
        }
        if (sensorTypeRepository.findByValue(voltage).isEmpty()) {
            sensorTypeRepository.save(new SensorType(voltage));
        }
        if (sensorTypeRepository.findByValue(temperature).isEmpty()) {
            sensorTypeRepository.save(new SensorType(temperature));
        }
        if (sensorTypeRepository.findByValue(humidity).isEmpty()) {
            sensorTypeRepository.save(new SensorType(humidity));
        }
    }

    private void initSensorUnits() {
        String bar = "bar";
        String voltage = "voltage";
        String celsius = "°С";
        String percent = "%";
        if (sensorUnitRepository.findByValue(bar).isEmpty()) {
            sensorUnitRepository.save(new SensorUnit(bar));
        }
        if (sensorUnitRepository.findByValue(voltage).isEmpty()) {
            sensorUnitRepository.save(new SensorUnit(voltage));
        }
        if (sensorUnitRepository.findByValue(celsius).isEmpty()) {
            sensorUnitRepository.save(new SensorUnit(celsius));
        }
        if (sensorUnitRepository.findByValue(percent).isEmpty()) {
            sensorUnitRepository.save(new SensorUnit(percent));
        }
    }

    private void initSensors() {
        List<Sensor> sensor = sensorRepository.findByName("Barometer");
        if (sensor.isEmpty()) {
            SensorType type = sensorTypeRepository.findByValue("Temperature").orElse(null);
            SensorUnit unit = sensorUnitRepository.findByValue("°С").orElse(null);
            Sensor newSensor = new Sensor();
            newSensor.setName("Barometer");
            newSensor.setModel("ac-23");
            newSensor.setRange_from(22);
            newSensor.setRange_to(45);
            newSensor.setType(type);
            newSensor.setUnit(unit);
            newSensor.setLocation("kitchen");
            newSensor.setDescription("description");
            sensorRepository.save(newSensor);
        }
    }
}
