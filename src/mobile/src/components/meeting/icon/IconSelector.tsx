import React from 'react';
import RNPickerSelect from 'react-native-picker-select';
import * as emoji from 'node-emoji';
import {useState} from 'react';
import {Meeting} from '../../../types/Meeting';
import {useMeetingsStore} from '../../../lib/meetings/meetingsStore';
import {useAuthStore} from '../../../lib/auth/authStore';
import {isMeetingAdmin} from '../../../utils/meetingHelpers';

const samples = 30;

const items = new Array(samples)
    .fill(1)
    .map(() => emoji.random())
    .map(e => ({label: e.emoji, value: e.name}));

const defaultIcon = 'handshake';
items.unshift({label: emoji.get(defaultIcon), value: defaultIcon});

export const IconSelector = ({meeting}: {meeting: Meeting}) => {
    const {token, user} = useAuthStore();
    const {changeIcon} = useMeetingsStore();

    const [selectedIcon, setSelectedIcon] = useState<string>(meeting?.icon || 'handshake');

    const handleIconChange = async (value: string) => {
        setSelectedIcon(value);
    };

    const onDonePress = async () => {
        await changeIcon(token, meeting.id, selectedIcon);
    };

    return (
        <RNPickerSelect
            value={selectedIcon}
            onValueChange={handleIconChange}
            onDonePress={onDonePress}
            items={items}
            darkTheme={true}
            style={{inputIOS: {fontSize: 30}}}
            placeholder={{}}
            disabled={!isMeetingAdmin(meeting, user)}
        />
    );
};
