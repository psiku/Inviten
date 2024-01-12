import React, {useState} from 'react';
import {TouchableOpacity} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import Dialog from 'react-native-dialog';
import {useAuthStore} from '../../../lib/auth/authStore';
import {useMeetingsStore} from '../../../lib/meetings/meetingsStore';

export const PlaceAddButton = ({meetingId}: {meetingId: string}) => {
    const {token} = useAuthStore();
    const {addPlaceProposal} = useMeetingsStore();

    const [placeName, setPlaceName] = useState<string>('');
    const [showPlaceDialog, setShowPlaceDialog] = useState<boolean>(false);

    const handlePlaceAdd = () => {
        setShowPlaceDialog(true);
    };

    const handleCancel = () => {
        setShowPlaceDialog(false);
    };

    const handleAdd = async () => {
        const placeProposal = {
            name: placeName,
        };

        await addPlaceProposal(token, meetingId, placeProposal);

        setPlaceName('');
        setShowPlaceDialog(false);
    };

    return (
        <>
            <TouchableOpacity onPress={handlePlaceAdd}>
                <Icon name="pluscircle" size={25} color="#7c3aed" />
            </TouchableOpacity>
            <Dialog.Container visible={showPlaceDialog}>
                <Dialog.Title>Add new place</Dialog.Title>
                <Dialog.Description>Please provide the name of the place</Dialog.Description>
                <Dialog.Input value={placeName} onChangeText={setPlaceName} placeholder="Place name" />
                <Dialog.Button label="Cancel" onPress={handleCancel} />
                <Dialog.Button label="Add" onPress={handleAdd} />
            </Dialog.Container>
        </>
    );
};
