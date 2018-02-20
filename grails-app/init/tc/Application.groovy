package tc

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.apache.catalina.connector.Connector
import org.apache.coyote.http11.Http11NioProtocol
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.context.annotation.Bean

class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
    
    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            void customize(ConfigurableEmbeddedServletContainer container) {
                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container
                //tomcat.add && tomcat.set


                tomcat.addConnectorCustomizers(
                        new TomcatConnectorCustomizer() {
                            @Override
                            void customize(Connector connector) {
                                connector.port = 8443
                                connector.secure = true
                                connector.scheme = "https"

                                Http11NioProtocol proto = (Http11NioProtocol) connector.protocolHandler
                                proto.minSpareThreads = 5
                                proto.SSLEnabled = true
                                proto.clientAuth = false

                                proto.keystoreFile = "some.jks"
                                proto.keystorePass = "somepassword"
                                proto.keystoreType = "JKS"
                                proto.keyAlias = "ssl_server"
                                proto.truststoreFile = "some.jts"
                                proto.truststoreType = "JKS"
                                proto.truststorePass  = "changeit"
                                proto.ciphers = "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256,TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,TLS_ECDHE_RSA_WITH_RC4_128_SHA,TLS_RSA_WITH_AES_128_CBC_SHA256,TLS_RSA_WITH_AES_128_CBC_SHA,TLS_RSA_WITH_AES_256_CBC_SHA256,TLS_RSA_WITH_AES_256_CBC_SHA,SSL_RSA_WITH_RC4_128_SHA"
                            }
                        })
            }
        }
    }
}