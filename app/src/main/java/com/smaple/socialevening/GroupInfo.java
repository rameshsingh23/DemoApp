package com.smaple.socialevening;

import java.util.ArrayList;
import java.util.List;

public class GroupInfo {
    String groupName;
    boolean isGroupOwner;
    String GroupOwnerName;
    List<String> GroupMembers;

    public String getGroupOwnerName() {
        return GroupOwnerName;
    }

    public void setGroupOwnerName(String groupOwnerName) {
        GroupOwnerName = groupOwnerName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isGroupOwner() {
        return isGroupOwner;
    }

    public void setIsGroupOwner(boolean isGroupOwner) {
        this.isGroupOwner = isGroupOwner;
    }

    public List<String> getGroupMembers() {
        return GroupMembers;
    }

    public void addMember(String name){
        if (GroupMembers == null) {
            GroupMembers = new ArrayList<String>();
        }
        GroupMembers.add(name);
    }
}
