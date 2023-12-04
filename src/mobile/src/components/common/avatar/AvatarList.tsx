import React from 'react';
import {Text, View} from 'react-native';
import {Avatar} from './Avatar';

export const AvatarList = ({
    shortNames,
    maxLength = 3,
}: {
    shortNames: string[];
    maxLength?: number;
}) => {
    const namesToDisplay = shortNames.slice(0, maxLength);
    return (
        <View className="flex-row">
            {namesToDisplay.map((shortName, i) => (
                <View className="-mr-2" key={i}>
                    <Avatar shortName={shortName} />
                </View>
            ))}
            {shortNames.length > maxLength && (
                <View className="relative inline-flex items-center justify-center w-6 h-6 overflow-hidden bg-gray-800 rounded-full">
                    <Text className="font-medium text-gray-200 text-xs">
                        +{shortNames.length - maxLength}
                    </Text>
                </View>
            )}
        </View>
    );
};
