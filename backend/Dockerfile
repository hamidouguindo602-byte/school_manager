# ---------- Etape 1 : build ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Cache des dependances Maven (couche separee du code source)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

COPY src ./src
RUN ./mvnw -B clean package -DskipTests

# ---------- Etape 2 : runtime ----------
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
