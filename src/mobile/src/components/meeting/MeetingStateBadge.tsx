import React from 'react';
import {Text} from 'react-native';
import {Meeting} from '../../types/Meeting';

const PlaningStateBadge = () => {
    return (
        <Text className="text-xs font-bold text-violet-400">in planing</Text>
    );
};

const ScheduledStateBadge = () => {
    return <Text className="text-xs font-bold text-green-400">scheduled</Text>;
};

const PastStateBadge = () => {
    return <Text className="text-xs font-bold text-gray-400">past</Text>;
};

export const MeetingStateBadge = ({meeting}: {meeting: Meeting}) => {
    const meetingDateTime = new Date(meeting.date);

    if (meeting.date !== null && meetingDateTime < new Date()) {
        return <PastStateBadge />;
    }

    if (meeting.isDateChosen && meeting.isPlaceChosen) {
        return <ScheduledStateBadge />;
    }

    return <PlaningStateBadge />;
};
