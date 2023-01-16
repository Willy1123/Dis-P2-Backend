FROM amazoncorretto:11

COPY target/gestionZonasBasicasSalud-API-0.0.1.jar gestionZonasBasicasSalud-API-0.0.1.jar

ENTRYPOINT ["java", "-jar", "/gestionZonasBasicasSalud-API-0.0.1.jar"]