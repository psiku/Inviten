import React, {useState} from 'react';
import RNPickerSelect from 'react-native-picker-select';
import Icon from 'react-native-vector-icons/Ionicons';
import {useMeetingsStore} from '../../../lib/meetings/meetingsStore';
import {useAuthStore} from '../../../lib/auth/authStore';
import {isMeetingAdmin} from '../../../utils/meetingHelpers';
import {Meeting} from '../../../types/Meeting';

const durations = Array.from(Array(24 * 2 + 1).keys()).map(i => {
    const hours = Math.floor(i / 2);
    const minutes = i % 2 === 0 ? '00' : '30';
    const minutesOverall = i * 30;

    return {
        label: minutesOverall === 0 ? '' : `${[hours, minutes].join(':')} h`,
        value: (i * 30).toString(),
    };
});

export const DurationSpecifyButton = ({meeting}: {meeting: Meeting}) => {
    const {token, user} = useAuthStore();
    const {changeDuration} = useMeetingsStore();
    const [specifiedDuration, setSpecifiedDuration] = useState<string>(meeting?.duration?.toString() || '0');

    const handleDurationSpecified = async (value: string) => {
        setSpecifiedDuration(value);
    };

    const onDonePress = async () => {
        const durationValue = parseInt(specifiedDuration, 10);
        await changeDuration(token, meeting.id, durationValue);
    };

    const renderIcon = () => <Icon name="timer-outline" size={25} color="#7c3aed" />;

    return (
        <>
            <RNPickerSelect
                value={specifiedDuration}
                onValueChange={handleDurationSpecified}
                onDonePress={onDonePress}
                items={durations}
                darkTheme={true}
                style={{
                    inputIOS: {
                        color: '#7c3aed',
                        fontWeight: 'bold',
                        marginTop: 5,
                        marginRight: 30,
                    },
                }}
                placeholder={{}}
                Icon={renderIcon}
                disabled={!isMeetingAdmin(meeting, user)}
                doneText="Choose"
            />
        </>
    );
};
