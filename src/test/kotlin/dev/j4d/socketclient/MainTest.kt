package dev.j4d.socketclient

import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun `main can launch`() {
        main(args = arrayOf("reconnectOnFailure=false"))
    }
}
