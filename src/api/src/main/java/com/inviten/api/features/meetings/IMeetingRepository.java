<<<<<<< HEAD
package com.inviten.api.features.meetings;

public interface IMeetingRepository {
    public Meeting one(String id);

    public void create(Meeting meeting);

    public Meeting createAndSave(Meeting meeting);
}
=======
package com.inviten.api.features.meetings;


import com.inviten.api.features.users.User;

public interface IMeetingRepository {
    public Meeting one(String id);

    public void create(Meeting meeting);

    public Meeting createAndSave(Meeting meeting);

<<<<<<< HEAD
    public void addMember(String meetingId, Member member);

    public void deleteMember(String meetingId, String phoneNumber);
<<<<<<< HEAD
=======
    public void put(Meeting meeting);
>>>>>>> 20c175c (Implement placeProposal)
=======

    public void put(Meeting meeting);
>>>>>>> 16719cb (added put method)
}
>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
