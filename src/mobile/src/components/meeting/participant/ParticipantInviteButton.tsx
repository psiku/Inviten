import React, {useState} from 'react';
import {Text, TouchableOpacity, View} from 'react-native';
import Dialog from 'react-native-dialog';
import {useMeetingsStore} from '../../../lib/meetings/meetingsStore';
import {useAuthStore} from '../../../lib/auth/authStore';

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
        await inviteUser(token, meetingId, phoneNumber);

        setPhoneNumber('');
        setShowInviteDialog(false);
    };

    return (
        <>
            <TouchableOpacity onPress={handleUserInvite}>
                <View className="w-12 h-12 flex items-center justify-center">
                    <View className="relative inline-flex items-center justify-center w-8 h-8 overflow-hidden bg-violet-600 rounded-full">
                        <Text className="font-light text-black text-2xl">+</Text>
                    </View>
                </View>
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
