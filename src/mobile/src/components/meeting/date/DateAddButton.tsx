import React from 'react';
import {TouchableOpacity} from 'react-native';
import DatePicker from 'react-native-date-picker';
import Icon from 'react-native-vector-icons/AntDesign';
import {DateProposal} from '../../../types/Date/DateProposal';
import uuid from 'react-native-uuid';

export const DateAddButton = ({
    onAdd = _ => {},
}: {
    onAdd: (date: DateProposal) => void;
}) => {
    const [showDatePicker, setShowDatePicker] = React.useState(false);

    const handlePick = () => {
        setShowDatePicker(true);
    };

    const handleAdd = (value: Date) => {
        const dateProposal = {
            id: uuid.v4(),
            date: value,
            voted: false,
            voters: [],
        };
        onAdd(dateProposal);
        setShowDatePicker(false);
    };

    const handleCancel = () => {
        setShowDatePicker(false);
    };

    return (
        <>
            <TouchableOpacity onPress={handlePick}>
                <Icon name="pluscircle" size={25} color="#a78bfa" />
            </TouchableOpacity>
            <DatePicker
                modal
                open={showDatePicker}
                date={new Date()}
                onConfirm={handleAdd}
                onCancel={handleCancel}
                minuteInterval={15}
            />
        </>
    );
};
