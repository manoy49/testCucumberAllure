package entity;

public class TestConfig {
    private final String browser;
    private final String url;

    private TestConfig(TestConfigBuilder testConfigBuilder) {
        this.browser = testConfigBuilder.browser;
        this.url = testConfigBuilder.url;
    }

    public String getBrowser() {
        return browser;
    }

    public String getUrl() {
        return url;
    }

    public static class TestConfigBuilder {
        private final String browser;
        private final String url;

        public TestConfigBuilder(String browser, String url) {
            this.browser = browser;
            this.url = url;
        }

        public TestConfig build() {
            return new TestConfig(this);
        }
    }
}
