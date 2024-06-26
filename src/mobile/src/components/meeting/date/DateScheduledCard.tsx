import React from 'react';
import {Text, View} from 'react-native';
import {getDayName, getHour} from '../../../utils/dateHelpers';
import Icon from 'react-native-vector-icons/AntDesign';

export const DateScheduledCard = ({date}: {date: string}) => {
    return (
        <View className="h-20 flex justify-center p-4 bg-lime-400 rounded-l-lg rounded-tr-lg">
            <View className="flex-row items-center justify-between">
                <View className="flex-row space-x-4 items-center">
                    <Icon name="calendar" size={25} color="black" />

                    <View className="flex items-center">
                        <Text className="text-black font-semibold">{getDayName(new Date(date))}</Text>
                        <Text className="text-xl font-bold text-black">{getHour(new Date(date))}</Text>
                    </View>
                </View>
                <View>
                    <Text className="font-bold black">Scheduled</Text>
                </View>
            </View>
        </View>
    );
};
