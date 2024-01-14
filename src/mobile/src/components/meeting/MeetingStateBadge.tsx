import React from 'react';
import {Text} from 'react-native';
import {Meeting} from '../../types/Meeting';

const RunningStateBadge = () => {
    return <Text className="text-xs font-bold text-violet-400">running</Text>;
};

const PlaningStateBadge = () => {
    return <Text className="text-xs font-bold text-gray-400">in planing</Text>;
};

const ScheduledStateBadge = () => {
    return <Text className="text-xs font-bold text-lime-400">scheduled</Text>;
};

const PastStateBadge = () => {
    return <Text className="text-xs font-bold text-gray-400">past</Text>;
};

export const MeetingStateBadge = ({meeting}: {meeting: Meeting}) => {
    if (meeting?.isRunning) {
        return <RunningStateBadge />;
    }

    if (meeting?.isFinished) {
        return <PastStateBadge />;
    }

    if (meeting?.isDateChosen) {
        return <ScheduledStateBadge />;
    }

    return <PlaningStateBadge />;
};
