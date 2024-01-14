import React from 'react';
import {Text} from 'react-native-ui-lib';

import {formatDistanceToNow} from 'date-fns';

export const DateScheduledBadge = ({date}: {date: string}) => {
    const getDurationString = () => {
        const duration = formatDistanceToNow(new Date(date), {addSuffix: true});
        return duration;
    };

    return <Text className="text-lime-400 text-xs font-semibold">starts {getDurationString()}</Text>;
};
