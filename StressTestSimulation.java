import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class StressTestSimulation extends Simulation {

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

  private ScenarioBuilder scn = scenario("StressTestSimulation")
    .exec(
      http("request_stress_test")
        .post("/DevOps_Liel_Almog_Almog_Stav_Meshi/welcome.jsp")
        .headers(headers_0)
        .formParam("username", "Almog Babian")
    );

  {
    setUp(
      scn.injectOpen(
        // 1. Gradual warm-up to 90% of the known max limit
        rampUsersPerSec(10).to(70).during(Duration.ofSeconds(30)),
        
        // 2. Saturation: keeping the server at 90% of its max capacity
        constantUsersPerSec(70).during(Duration.ofSeconds(30)),
        
        // 3. Aggressive Stress: Pushing beyond Tomcat's default 200 threads
        rampUsersPerSec(70).to(90).during(Duration.ofSeconds(20)),

		constantUsersPerSec(90).during(Duration.ofSeconds(20)),
		
		rampUsersPerSec(90).to(105).during(Duration.ofSeconds(20)),
        
        // 4. Extended Extreme Load: Holding for 20 seconds to force resource exhaustion and memory leaks
        constantUsersPerSec(105).during(Duration.ofSeconds(20)),

		rampUsersPerSec(105).to(90).during(Duration.ofSeconds(20)),

		constantUsersPerSec(90).during(Duration.ofSeconds(20)),
		
		rampUsersPerSec(90).to(105).during(Duration.ofSeconds(20)),
        
        constantUsersPerSec(105).during(Duration.ofSeconds(20)),  
		
        rampUsersPerSec(105).to(90).during(Duration.ofSeconds(20)),
		  
		// 5. Recovery
        rampUsersPerSec(90).to(40).during(Duration.ofSeconds(30)),
		        
        // 6. Stability check post-crash
        constantUsersPerSec(40).during(Duration.ofSeconds(30))
      )
    ).protocols(httpProtocol);
  }
}
