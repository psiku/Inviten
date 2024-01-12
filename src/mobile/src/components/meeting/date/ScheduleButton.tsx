import React from 'react';
import {Text, TouchableOpacity, View} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';

export const ScheduleButton = ({onPress = () => {}}: {onPress: () => void}) => (
    <TouchableOpacity onPress={onPress}>
        <View className="mx-1 my-2 px-2 rounded-lg flex-row items-center">
            <Text className="text-gray-200 font-semibold">Schedule</Text>
            <View className="ml-2">
                <Icon name="rightcircle" size={25} color="#a3e635" />
            </View>
        </View>
    </TouchableOpacity>
);
