import React from 'react';
import {Text} from 'react-native-ui-lib';
import {Meeting} from '../../../types/Meeting';

export const DurationBadge = ({meeting}: {meeting: Meeting}) => {
    let durationInMin = meeting?.duration || 0; // in minutes

    const now = new Date();
    const startDate = meeting?.date ? new Date(meeting.date) : null;
    const endDate = startDate ? new Date(startDate.getTime() + durationInMin * 60000) : null;

    const diff = endDate?.getTime() - now?.getTime() || 0;

    const hours = Math.floor(diff / 1000 / 60 / 60);
    const minutes = Math.floor((diff / 1000 / 60 / 60 - hours) * 60);

    const getDurationString = () => [hours, minutes === 0 ? '00' : minutes].join(':');

    return <Text className="text-violet-400 text-xs font-semibold">ends in {getDurationString()} h</Text>;
};
