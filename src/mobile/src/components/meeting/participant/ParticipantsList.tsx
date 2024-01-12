import React from 'react';
import {Meeting} from '../../../types/Meeting';
import {LargeAvatar} from '../../common/avatar/Avatar';
import {FlatList, View} from 'react-native';
import {Participant} from '../../../types/Participant';
import {Text} from 'react-native-ui-lib';
import {ParticipantInviteButton} from './ParticipantInviteButton';
import {useAuthStore} from '../../../lib/auth/authStore';
import {isMeetingAdmin} from '../../../utils/meetingHelpers';

const getTruncatedNick = (nick: string) => {
    if (nick.length > 12) {
        return nick.substring(0, 12) + '...';
    }

    return nick;
};

export const ParticipantsList = ({meeting}: {meeting: Meeting}) => {
    const {user} = useAuthStore();

    const renderItem = ({item}: {item: Participant}) => (
        <View className="flex items-center">
            <LargeAvatar
                shortName={item.phoneNumber[0]}
                badge={isMeetingAdmin(meeting, item.phoneNumber) ? item.role : null}
            />
            <View className="mt-3 px-2">
                <Text className="text-gray-100 text-xs font-semibold flex flex-wrap w-12">
                    {getTruncatedNick(item.nick)}
                </Text>
            </View>
        </View>
    );

    return (
        <View className="flex-row">
            {isMeetingAdmin(meeting, user) && (
                <View className="px-2 h-12 flex items-center justify-center">
                    <ParticipantInviteButton meetingId={meeting?.id} />
                </View>
            )}

            <FlatList
                horizontal
                data={meeting?.participants}
                renderItem={renderItem}
                keyExtractor={item => item.phoneNumber}
            />
        </View>
    );
};
