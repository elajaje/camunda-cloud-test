import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.Topology;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class TestCamunda {
    public static void main(String[] args) {
        String gateway = "7c37fac9-d6bd-4c52-bcfd-2d4e01ffdf07.ont-1.camunda.io:443";
        String clientId = "Z4XH~oQF.0CMM-4Zz1WlPV8GI8UvNAsP";
        String clientSecret = "F7z8VGZH5.F~FaKEk~W.Eb.J_7~a3u9p-n4s7FwWLs_QOxcAezKGZfIW.F-.ZU-6";

        ZeebeClient client = ZeebeClient.newClientBuilder()
                .gatewayAddress(gateway)
                .credentialsProvider(
                        new OAuthCredentialsProviderBuilder()
                                .clientId(clientId)
                                .clientSecret(clientSecret)
                                .audience("zeebe.camunda.io")
                                .authorizationServerUrl("https://login.cloud.camunda.io/oauth/token")
                                .build()
                )
                .build();

        System.out.println("🌐 Connecting to Camunda Cloud...");

        try {
            Topology topology = client.newTopologyRequest().send().join();
            System.out.println("✅ Connected to cluster: " + topology);
        } catch (Exception e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            client.close();
        }
    }
}
