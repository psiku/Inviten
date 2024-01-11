import React from 'react';
import {Text} from 'react-native';
import {Meeting} from '../../types/Meeting';

const PlaningStateBadge = () => {
    return (
        <Text className="text-xs font-bold text-violet-400 italic">
            in planing
        </Text>
    );
};

const ScheduledStateBadge = () => {
    return (
        <Text className="text-xs font-bold text-green-400 italic">
            scheduled
        </Text>
    );
};

const PastStateBadge = () => {
    return <Text className="text-xs font-bold text-gray-400 italic">past</Text>;
};

export const MeetingStateBadge = ({meeting}: {meeting: Meeting}) => {
    const now = new Date().toISOString();
    const meetingDateTime = new Date(`${meeting.date}T${meeting.time}`);

    if (meetingDateTime < new Date(now)) {
        return <PastStateBadge />;
    }

    if (meeting.isDateChosen && meeting.isPlaceChosen) {
        return <ScheduledStateBadge />;
    }

    return <PlaningStateBadge />;
};
