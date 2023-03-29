package service.wealth.terminus.domain.group

interface GroupDb {
    fun createGroup(group: Group): Group
    fun getGroup(groupId: String): Group
    fun getGroups(): List<Group>
    fun patchGroup(groupPatchRequest: GroupPatchRequest): Group
}
