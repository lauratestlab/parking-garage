package com.example.parkinglot;
import com.example.parkinglot.config.ApplicationProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class ParkinglotApplication {
    public ParkinglotApplication() {
    }

	private static final Logger LOG = LoggerFactory.getLogger(ParkinglotApplication.class);

	public static void main(String[] args) {
		Environment env = SpringApplication.run(ParkinglotApplication.class, args).getEnvironment();
		logApplicationStartup(env);
	}

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String applicationName = env.getProperty("spring.application.name");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank)
                .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG.warn("The host name could not be determined, using `localhost` as fallback");
        }
        LOG.info(
                """
    
                ----------------------------------------------------------
                \tApplication '{}' is running! Access URLs:
                \tLocal: \t\t{}://localhost:{}{}
                \tExternal: \t{}://{}:{}{}
                \tProfile(s): \t{}
                ----------------------------------------------------------""",
                applicationName,
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );
    }


//    @Bean
//    public CommandLineRunner dataLoader(final UserRepository repo, final CarRepository carRepository, final ReservationRepository reservationRepository,
//                                        final PaymentMethodRepository paymentMethodRepository, final PriceRepository priceRepository, final SpotReposiotry spotReposiotry, final  FloorRepository floorRepository) {
//        return new CommandLineRunner() {
//            public void run(String... args) throws Exception {
//
//                User user = new User("Marcin", "Szoska", "marszos", "1234455", "mszoska@gmail.com", Role.USER);
//
//
//
//                Car car1 = new Car();
//                car1.setModel("Tesla");
//                car1.setMake("Model S");
//                car1.setColor("Black");
//                car1.setRegistration("ABC123");
//                car1.setUser(user);
//
//                Car car2 = new Car();
//                car2.setModel("Toyota");
//                car2.setMake("Corolla");
//                car2.setColor("White");
//                car2.setRegistration("XYZ789");
//                car2.setUser(user);
//
//                PaymentMethod paymentMethod = new PaymentMethod();
//                paymentMethod.setExpirationDate("01/24");  // Expiration date: December 2026
//                paymentMethod.setCcv(123);                                   // CVV
//                paymentMethod.setCard_number(123456);              // Fake credit card number
//                paymentMethod.setFullName("Marcin Szoska");
//                paymentMethod.setDeliveryStreet("123 Main St");
//                paymentMethod.setDeliveryCity("Warsaw");
//                paymentMethod.setDeliveryState("Mazowieckie");
//                paymentMethod.setDeliveryZip("00-001");
//
//
//
//
//
//                Price price1 = new Price();
//                price1.setDuration(1);
//                price1.setPrice(2.0);
//
//                Price price2 = new Price();
//                price2.setDuration(2);
//                price2.setPrice(4.0);
//
//                Floor floor1 = new Floor();
//                floor1.setName(1);
//
//
//
//
//                Spot spot1 = new Spot();
//                spot1.setFloor(floor1);
//                spot1.setName("A1");
//
//                Spot spot2 = new Spot();
//                spot2.setFloor(floor1);
//                spot2.setName("B1");
//
//
//
//
//
//
//                Reservation reservation1 = new Reservation();
//                reservation1.setStartTime(LocalDateTime.of(2024, 10, 15, 10, 0));  // October 15, 2024, 10:00 AM
//                reservation1.setEndTime(LocalDateTime.of(2024, 10, 15, 12, 0));    // October 15, 2024, 12:00 PM
//                reservation1.setSpotId(1L);                                       // Example spot ID
//                reservation1.setPaymentMethodId(101L);                            // Example payment method ID
//                reservation1.setPrice(25.0);                                      // Example price
//                reservation1.setUser(user);
//
//
//
//
//                repo.save(user);
//                carRepository.saveAll(Arrays.asList(car1, car2));
//                paymentMethodRepository.save(paymentMethod);
//                floorRepository.save(floor1);
//                spotReposiotry.saveAll(Arrays.asList(spot1, spot2));
//                reservationRepository.save(reservation1);
//                priceRepository.saveAll(Arrays.asList(price1, price2));
//
//               List<Car> outcome = carRepository.findByUserFirstName("Marcin");
//
//
//               for (Car c: outcome) {
//                   System.out.println(c.getModel());
//               }
//
//
//
//            }
//        };
//    }
}
