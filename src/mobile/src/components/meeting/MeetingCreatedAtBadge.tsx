import React from 'react';
import {Text} from 'react-native';
import {Meeting} from '../../types/Meeting';
import {formatDistanceToNow} from 'date-fns';

export const MeetingCreatedAtBadge = ({meeting}: {meeting: Meeting}) => {
    if (meeting?.createdAt == null) {
        return null;
    }

    const date = new Date(meeting.createdAt);
    const timeAgo = formatDistanceToNow(date, {addSuffix: true});

    return (
        <Text className="text-gray-300 text-xs font-semibold">{timeAgo}</Text>
    );
};
