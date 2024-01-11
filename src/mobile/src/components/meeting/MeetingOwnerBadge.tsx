import React from 'react';
import {Text} from 'react-native';
import {useAuthStore} from '../../lib/auth/authStore';
import {Meeting} from '../../types/Meeting';

export const MeetingOwnerBadge = ({meeting}: {meeting: Meeting}) => {
    const {user} = useAuthStore();

    const ownerPhoneNumber = meeting.participants.find(
        p => p.role === 'owner',
    )?.phoneNumber;

    console.log('ownerPhoneNumber', ownerPhoneNumber);
    console.log('user', user);

    if (user === ownerPhoneNumber) {
        return (
            <Text className="mr-2 text-orange-300 text-xs font-semibold">
                owned
            </Text>
        );
    }

    return (
        <Text className="mr-2 text-gray-300 text-xs font-semibold">
            invited
        </Text>
    );
};
