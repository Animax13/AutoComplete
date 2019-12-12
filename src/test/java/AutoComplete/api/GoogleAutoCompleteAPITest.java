package AutoComplete.api;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Client;

class GoogleAutoCompleteAPITest {

    private final String DUMMY_URL_TEMPLATE = "dummy_{0}_{1}_{2}";
    private final String DUMMY_KEY = "dummy_key";
    private final String DUMMY_COMPONENTS = "dummy_components";

    @Mock
    private Client client;

    private GoogleAutoCompleteAPI target;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        class TestModule extends AbstractModule {
            @Override
            protected void configure() {
                bind(String.class).annotatedWith(Names.named("url-template")).toInstance(DUMMY_URL_TEMPLATE);
                bind(String.class).annotatedWith(Names.named("key")).toInstance(DUMMY_KEY);
                bind(String.class).annotatedWith(Names.named("components")).toInstance(DUMMY_COMPONENTS);
            }
        }
        Injector injector = Guice.createInjector(new TestModule());
        target = injector.getInstance(GoogleAutoCompleteAPI.class);
    }
}