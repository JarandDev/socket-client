package dev.j4d.socketclient

import dev.j4d.socketclient.serverconnection.ServerConnection
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.net.UnknownHostException

fun main(args: Array<String> = emptyArray()) {
    val arguments = args.associate { it.split("=")[0] to it.split("=")[1] }

    val reconnectOnFailure = arguments["reconnectOnFailure"]?.toBoolean() ?: true

    var running = true

    while (running) {
        try {
            val socket = Socket()
            socket.connect(InetSocketAddress("socket-server", 8080))

            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val writer = PrintWriter(OutputStreamWriter(socket.getOutputStream()), true)

            ServerConnection(socket, reader, writer).run()
        } catch (_: IOException) {
            if (!reconnectOnFailure) {
                running = false
            }
        } catch (_: UnknownHostException) {
            if (!reconnectOnFailure) {
                running = false
            }
        }
    }
}
