import React, {useState} from 'react';
import {TouchableOpacity} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import Dialog from 'react-native-dialog';
import {PlaceProposal} from '../../../types/Place/PlaceProposal';
import uuid from 'react-native-uuid';

export const PlaceAddButton = ({
    onAdd = _ => {},
}: {
    onAdd: (place: PlaceProposal) => void;
}) => {
    const [placeName, setPlaceName] = useState(null);
    const [showPlaceDialog, setShowPlaceDialog] = useState(false);

    const handlePlaceAdd = () => {
        setShowPlaceDialog(true);
    };

    const handleCancel = () => {
        setShowPlaceDialog(false);
    };

    const handleAdd = () => {
        const placeProposal = {
            id: uuid.v4(),
            name: placeName,
            voted: false,
            voters: [],
        };

        onAdd(placeProposal);

        setPlaceName(null);
        setShowPlaceDialog(false);
    };

    return (
        <>
            <TouchableOpacity onPress={handlePlaceAdd}>
                <Icon name="pluscircle" size={25} color="#a78bfa" />
            </TouchableOpacity>
            <Dialog.Container visible={showPlaceDialog}>
                <Dialog.Title>Add new place</Dialog.Title>
                <Dialog.Description>
                    Please provide the name of the place
                </Dialog.Description>
                <Dialog.Input
                    value={placeName}
                    onChangeText={setPlaceName}
                    placeholder="Place name"
                />
                <Dialog.Button label="Cancel" onPress={handleCancel} />
                <Dialog.Button label="Add" onPress={handleAdd} />
            </Dialog.Container>
        </>
    );
};
