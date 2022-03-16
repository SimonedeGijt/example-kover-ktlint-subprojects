import com.pinterest.ktlint.core.RuleSet
import org.assertj.core.api.Assertions.assertThat
import org.example.CustomRuleSetProvider
import org.junit.Test

class CustomRuleSetProviderTest {

    @Test
    fun `The no-var rule is retrieved`() {
        val service = CustomRuleSetProvider()
        assertThat(service.get()).isInstanceOf(RuleSet::class.java)
    }
}
