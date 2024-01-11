import React from 'react';
import {TouchableOpacity} from 'react-native';
import DatePicker from 'react-native-date-picker';
import Icon from 'react-native-vector-icons/AntDesign';
import {DateProposal} from '../../../types/Date/DateProposal';
import uuid from 'react-native-uuid';
import {useMeetingsStore} from '../../../lib/meetings/meetingsStore';
import {useAuthStore} from '../../../lib/auth/authStore';

export const DateAddButton = ({meetingId}: {meetingId: string}) => {
    const {token, user} = useAuthStore();
    const {addDateProposal} = useMeetingsStore();
    const [showDatePicker, setShowDatePicker] = React.useState(false);

    const handlePick = () => {
        setShowDatePicker(true);
    };

    const handleAdd = async (value: Date) => {
        const dateProposal: DateProposal = {
            id: uuid.v4().toString(),
            proposedDate: value,
            proposedBy: user,
            votes: [],
        };

        await addDateProposal(token, meetingId, dateProposal);

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
