package dev.j4d.socketclient.command

import java.time.Instant
import java.util.UUID

data class Pong(
    val id: UUID,
    val type: String,
    val time: Instant,
    val pingId: UUID,
) {
    fun serialize() = "PONG id=$id type=$type time=$time pingId=$pingId"

    companion object {
        fun deserialize(line: String): Pong {
            val arguments = line.removePrefix("PONG ")
            val parts = arguments.split(" ")
            val fields =
                parts.associate {
                    val (key, value) = it.split("=")
                    key to value
                }
            return Pong(
                id = UUID.fromString(fields["id"]!!),
                type = fields["type"]!!,
                time = Instant.parse(fields["time"]!!),
                pingId = UUID.fromString(fields["pingId"]!!),
            )
        }

        fun from(ping: Ping) = Pong(id = UUID.randomUUID(), type = ping.type, time = Instant.now(), pingId = ping.id)
    }
}
