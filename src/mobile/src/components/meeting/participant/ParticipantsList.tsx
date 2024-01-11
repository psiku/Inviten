import React from 'react';
import {Meeting} from '../../../types/Meeting';
import {LargeAvatar} from '../../common/avatar/Avatar';
import {FlatList, View} from 'react-native';
import {Participant} from '../../../types/Participant';
import {Text} from 'react-native-ui-lib';

export const ParticipantsList = ({meeting}: {meeting: Meeting}) => {
    const renderItem = ({item}: {item: Participant}) => (
        <View className="flex items-center">
            <LargeAvatar shortName={item.phoneNumber[0]} badge={item.role} />
            <View className="mt-3 px-2">
                <Text className="text-gray-100 text-xs font-semibold">{item.nick}</Text>
            </View>
        </View>
    );

    return (
        <View>
            <FlatList
                horizontal
                data={meeting?.participants}
                renderItem={renderItem}
                keyExtractor={item => item.phoneNumber}
            />
        </View>
    );
};
