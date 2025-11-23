package dev.j4d.socketclient.command

import java.time.Instant
import java.util.UUID

data class Ping(
    val id: UUID,
    val type: String,
    val time: Instant,
) {
    fun serialize() = "PING id=$id type=$type time=$time"

    companion object {
        fun deserialize(line: String): Ping {
            val arguments = line.removePrefix("PING ")
            val parts = arguments.split(" ")
            val fields =
                parts.associate {
                    val (key, value) = it.split("=")
                    key to value
                }
            return Ping(
                UUID.fromString(fields["id"]!!),
                fields["type"]!!,
                Instant.parse(fields["time"]!!),
            )
        }
    }
}
