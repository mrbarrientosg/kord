package com.gitlab.kordlib.core.entity

import com.gitlab.kordlib.core.behavior.MemberBehavior
import com.gitlab.kordlib.core.cache.data.MemberData
import com.gitlab.kordlib.core.cache.data.UserData
import equality.BehaviorEqualityTest
import equality.GuildEntityEqualityTest
import io.mockk.every
import io.mockk.mockk
import mockKord
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class MemberTest : GuildEntityEqualityTest<Member> by GuildEntityEqualityTest({ id, guildId ->
    val kord = mockKord()
    val memberData = mockk<MemberData>()
    every { memberData.userId } returns id.longValue
    every { memberData.guildId } returns guildId.longValue

    val userData = mockk<UserData>()
    every { userData.id } returns id.longValue

    Member(memberData, userData, kord)
}), BehaviorEqualityTest<Member> {
    override fun Member.behavior(): Entity = MemberBehavior(guildId = guildId, id = id, kord = kord)

    @Test
    fun `members equal users with the same ID`() {
        val kord = mockKord()
        val memberData = mockk<MemberData>()
        every { memberData.userId } returns 0L
        every { memberData.guildId } returns 1L

        val userData = mockk<UserData>()
        every { userData.id } returns 0L
        val member = Member(memberData, userData, kord)
        val user = User(userData, kord)

        assertEquals(member, user)
        assertEquals(user, member)
    }


}