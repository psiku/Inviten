import React, {useState} from 'react';
import {TouchableOpacity} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import uuid from 'react-native-uuid';
import Dialog from 'react-native-dialog';
import {useAuthStore} from '../../lib/auth/authStore';
import {useMeetingsStore} from '../../lib/meetings/meetingsStore';
import {Meeting} from '../../types/Meeting';

export const MeetingAddButton = () => {
    const {token, user} = useAuthStore();
    const {addMeeting} = useMeetingsStore();

    const [meetingName, setMeetingName] = useState<string>('');
    const [showMeetingDialog, setShowMeetingDialog] = useState<boolean>(false);

    const handleMeetingAdd = () => {
        setShowMeetingDialog(true);
    };

    const handleCancel = () => {
        setShowMeetingDialog(false);
    };

    const handleAdd = async () => {
        const meeting: Meeting = {
            id: uuid.v4().toString(),
            createdAt: new Date().toISOString(),
            name: meetingName,
            participants: [],
            placeProposals: [],
            date: null,
            dateProposals: [],
            place: null,
            isDateChosen: false,
            isPlaceChosen: false,
        };

        await addMeeting(token, meeting);

        setMeetingName('');
        setShowMeetingDialog(false);
    };

    return (
        <>
            <TouchableOpacity onPress={handleMeetingAdd}>
                <Icon name="pluscircle" size={25} color="#a78bfa" />
            </TouchableOpacity>
            <Dialog.Container visible={showMeetingDialog}>
                <Dialog.Title>Plan new meeting</Dialog.Title>
                <Dialog.Description>Please provide the name of the meeting</Dialog.Description>
                <Dialog.Input value={meetingName} onChangeText={setMeetingName} placeholder="Meeting name" />
                <Dialog.Button label="Cancel" onPress={handleCancel} />
                <Dialog.Button label="Add" onPress={handleAdd} />
            </Dialog.Container>
        </>
    );
};
