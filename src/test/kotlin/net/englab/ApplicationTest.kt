package net.englab

import io.ktor.server.testing.*
import kotlin.test.*
import net.englab.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        //client.get("/api/v1/feedback").apply {
        //    assertEquals(HttpStatusCode.OK, status)
        //    assertEquals("Hello World!", bodyAsText())
        //}
    }
}
