import React, {useState} from 'react';
import {TouchableOpacity} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import Dialog from 'react-native-dialog';
import {Meeting} from '../../types/Meeting';
import uuid from 'react-native-uuid';

export const MeetingAddButton = ({
    onAdd = _ => {},
}: {
    onAdd: (meeting: Meeting) => void;
}) => {
    const [meetingName, setMeetingName] = useState(null);
    const [showMeetingDialog, setShowMeetingDialog] = useState(false);

    const handleMeetingAdd = () => {
        setShowMeetingDialog(true);
    };

    const handleCancel = () => {
        setShowMeetingDialog(false);
    };

    const handleAdd = () => {
        const meeting = {
            id: uuid.v4(),
            name: meetingName,
            createdAt: new Date(),
            participants: [],
        };

        onAdd(meeting);

        setMeetingName(null);
        setShowMeetingDialog(false);
    };

    return (
        <>
            <TouchableOpacity onPress={handleMeetingAdd}>
                <Icon name="pluscircle" size={25} color="#a78bfa" />
            </TouchableOpacity>
            <Dialog.Container visible={showMeetingDialog}>
                <Dialog.Title>Plan new meeting</Dialog.Title>
                <Dialog.Description>
                    Please provide the name of the meeting
                </Dialog.Description>
                <Dialog.Input
                    value={meetingName}
                    onChangeText={setMeetingName}
                    placeholder="Meeting name"
                />
                <Dialog.Button label="Cancel" onPress={handleCancel} />
                <Dialog.Button label="Add" onPress={handleAdd} />
            </Dialog.Container>
        </>
    );
};
