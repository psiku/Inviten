import React from 'react';
import {Text, View} from 'react-native';

export const Avatar = ({shortName}: {shortName: string}) => (
    <View className="relative inline-flex items-center justify-center w-6 h-6 overflow-hidden bg-gray-100 rounded-full border-2 border-gray-800">
        <Text className="font-medium text-gray-600 text-xs">{shortName}</Text>
    </View>
);
