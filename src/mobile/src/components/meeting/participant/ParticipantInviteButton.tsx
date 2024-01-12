import React, {useState} from 'react';
import {TouchableOpacity} from 'react-native';
import Dialog from 'react-native-dialog';
import {useMeetingsStore} from '../../../lib/meetings/meetingsStore';
import {useAuthStore} from '../../../lib/auth/authStore';
import Icon from 'react-native-vector-icons/AntDesign';

export const ParticipantInviteButton = ({meetingId}: {meetingId: string}) => {
    const {token} = useAuthStore();
    const {inviteUser} = useMeetingsStore();

    const [phoneNumber, setPhoneNumber] = useState<string>('');
    const [showInviteDialog, setShowInviteDialog] = useState<boolean>(false);

    const handleUserInvite = () => {
        setShowInviteDialog(true);
    };

    const handleCancel = () => {
        setShowInviteDialog(false);
    };

    const handleInvite = async () => {
        await inviteUser(token, meetingId, phoneNumber.toString());

        setPhoneNumber('');
        setShowInviteDialog(false);
    };

    return (
        <>
            <TouchableOpacity onPress={handleUserInvite}>
                <Icon name="pluscircle" size={25} color="#7c3aed" />
            </TouchableOpacity>
            <Dialog.Container visible={showInviteDialog}>
                <Dialog.Title>Invite user</Dialog.Title>
                <Dialog.Description>Please provide the phone number of a user</Dialog.Description>
                <Dialog.Input
                    value={phoneNumber}
                    onChangeText={setPhoneNumber}
                    placeholder="Phone number"
                    keyboardType="number-pad"
                />
                <Dialog.Button label="Cancel" onPress={handleCancel} />
                <Dialog.Button label="Add" onPress={handleInvite} />
            </Dialog.Container>
        </>
    );
};
