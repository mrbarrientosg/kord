package com.gitlab.kordlib.rest.json.response

import com.gitlab.kordlib.common.entity.DiscordChannel
import com.gitlab.kordlib.common.entity.DiscordPartialGuild
import com.gitlab.kordlib.common.entity.DiscordUser
import kotlinx.serialization.*
import kotlinx.serialization.internal.IntDescriptor

@Serializable
data class InviteResponse(
        val code: String? = null,
        val guild: DiscordPartialGuild? = null,
        val channel: DiscordChannel? = null,
        val inviter: String? = null,
        @SerialName("target_user")
        val targetUser: DiscordUser? = null,
        @SerialName("target_user_type")
        val targetUserType: TargetUserType? = null,
        @SerialName("approximate_presence_count")
        val approximatePresenceCount: Int? = null,
        @SerialName("approximate_member_count")
        val approximateMemberCount: Int? = null,
        val uses: Int? = null
)

@Serializable(with = TargetUserType.TargetUserTypeSerializer::class)
enum class TargetUserType(val code: Int) {
    /** The default code for unknown values. */
    Unknown(Int.MIN_VALUE),
    STREAM(1);

    @Serializer(forClass = TargetUserType::class)
    companion object TargetUserTypeSerializer : KSerializer<TargetUserType> {
        override val descriptor: SerialDescriptor = IntDescriptor.withName("TargetUserType")

        override fun deserialize(decoder: Decoder): TargetUserType {
            val code = decoder.decodeInt()

            return values().firstOrNull { it.code == code } ?: Unknown
        }

        override fun serialize(encoder: Encoder, obj: TargetUserType) {
            encoder.encodeInt(obj.code)
        }
    }

}

@Serializable
data class InviteMetaDataResponse(
        val inviter: DiscordUser,
        val uses: Int,
        @SerialName("max_uses")
        val maxUses: Int,
        @SerialName("max_age")
        val maxAge: Int,
        val temporary: Boolean,
        @SerialName("created_at")
        val createdAt: String
)

@Serializable
data class PartialInvite(val code: String? = null)