import io.kotest.core.annotation.Ignored

// Kotest не сможет подставить правильный аргумент конструктора, поэтому
// нужно запретить ему запускать этот класс
@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val restClient = RestClient(dockerCompose)
    testApiV1(restClient, "rest ")
    //testApiV2(restClient, "rest ")
})

class AccRestWiremockTest : AccRestTestBase(WiremockDockerCompose)
//class AccRestSpringTest : AccRestTestBase(SpringDockerCompose)
//class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
