import React from 'react';
import {Meeting} from '../../../types/Meeting';
import {LargeAvatar} from '../../common/avatar/Avatar';
import {FlatList, View} from 'react-native';
import {Participant} from '../../../types/Participant';
import {Text} from 'react-native-ui-lib';
import {ParticipantInviteButton} from './ParticipantInviteButton';
import {useAuthStore} from '../../../lib/auth/authStore';
import {isMeetingAdmin} from '../../../utils/meetingHelpers';

const getShortName = (participant: Participant) =>
    participant.nick != null && participant.nick.length > 0 ? participant.nick[0] : participant.phoneNumber[0];

const getTruncatedNick = (nick: string) => {
    if (nick.length > 12) {
        return nick.substring(0, 12) + '...';
    }

    return nick;
};

export const ParticipantsList = ({meeting}: {meeting: Meeting}) => {
    const {user} = useAuthStore();

    const isMe = (participant: Participant) => participant?.phoneNumber === user;

    const renderItem = ({item}: {item: Participant}) => (
        <View className="flex items-center">
            <LargeAvatar
                shortName={isMe(item) ? 'I' : getShortName(item)}
                badge={isMeetingAdmin(meeting, item.phoneNumber) ? item.role : null}
            />
            <View className="mt-3 px-2">
                <Text className="text-gray-100 text-xs font-semibold flex text-center flex-wrap w-12">
                    {isMe(item) ? 'Me' : getTruncatedNick(item.nick)}
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
