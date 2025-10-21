package dev.j4d.socketclient.serverconnection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

class ServerConnection(
    private val socket: Socket,
    private val reader: BufferedReader,
    private val writer: PrintWriter
) {

    fun run() = runBlocking(Dispatchers.IO) {
        launch {
            println("Sending ping every 10th second")
            while (!socket.isClosed) {
                writer.println("PING")
                println("Sent: PING")
                Thread.sleep(10_000L)
            }
        }

        launch {
            println("Reading from server")
            while (!socket.isClosed) {
                val line = reader.readLine()
                println("Received: $line")
                if (line == null) {
                    socket.close()
                } else if (line == "PING") {
                    writer.println("PONG")
                    println("Sent: PONG")
                }
            }
        }
    }
}
