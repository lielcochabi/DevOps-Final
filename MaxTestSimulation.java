import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class MaxTestSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://84.13.82.200:8080")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,he;q=0.8")
    .contentTypeHeader("application/x-www-form-urlencoded")
    .originHeader("http://84.13.82.200:8080")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/151.0.0.0 Safari/537.36");
  
  private Map<CharSequence, String> headers_0 = Map.of("Cache-Control", "max-age=0");

  private ScenarioBuilder scn = scenario("MaxTestSimulation")
    .exec(
      http("request_max_test")
        .post("/DevOps_Liel_Almog_Almog_Stav_Meshi/welcome.jsp")
        .headers(headers_0)
        .formParam("username", "Almog Babian")
    );

  {
    setUp(
      scn.injectOpen(
        // Linear and gradual ramp-up from 10 to 85 users per second over 3 minutes.
        // This slow ramp-up will allow us to see exactly at which second 
        // and under what load the hotspot network starts to crash (expected around 75-78).
        rampUsersPerSec(10).to(88).during(Duration.ofMinutes(3))
      )
    ).protocols(httpProtocol);
  }
}