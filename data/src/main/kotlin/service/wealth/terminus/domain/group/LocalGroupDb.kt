package service.wealth.terminus.domain.group

import mu.KotlinLogging

class LocalGroupDb(): GroupDb {
    private val logger = KotlinLogging.logger {}

    val groupMap: MutableMap<String, Group> = mutableMapOf()
    override fun createGroup(group: Group): Group {
        if (groupMap.containsKey(group.id)) {
            throw Exception()
        }

        groupMap[group.id] = group
        logger.info { "Inserted group to Map. \nTotal Map: $groupMap" }
        return group

    }

    override fun getGroup(groupId: String): Group {
        logger.info { "Fetching group with groupId: $groupId" }
        return groupMap[groupId] ?: throw NullPointerException()
    }

    override fun getGroups(): List<Group> {
        logger.info { "Fetching all groups" }
        return groupMap.map { it.value }
    }

    override fun patchGroup(groupPatchRequest: GroupPatchRequest): Group {
        TODO("Not yet implemented")
    }
}
