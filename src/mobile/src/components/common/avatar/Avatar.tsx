<<<<<<< HEAD
import React from 'react';
import {Text, View} from 'react-native';

export const Avatar = ({shortName}: {shortName: string}) => (
    <View className="relative inline-flex items-center justify-center w-6 h-6 overflow-hidden bg-gray-100 rounded-full border-2 border-gray-800">
        <Text className="font-medium text-gray-600 text-xs">{shortName.toUpperCase()}</Text>
    </View>
);

export const LargeAvatar = ({shortName, badge}: {shortName: string; badge?: string | null}) => (
    <View>
        <View className="relative inline-flex items-center justify-center w-12 h-12 overflow-hidden bg-gray-100 rounded-full border-2 border-gray-800">
            <Text className="font-medium text-gray-600 text-xl">{shortName.toUpperCase()}</Text>
        </View>
        {badge ? (
            <View className="absolute -bottom-1 -right-2 z-10 px-1 flex items-center justify-center bg-violet-600 rounded-lg">
                <Text className="text-xs font-medium text-gray-200">{badge}</Text>
            </View>
        ) : null}
    </View>
);
=======
import React from 'react';
import {Text, View} from 'react-native';
import {classNames} from '../../../utils/styleHelpers';

export const Avatar = ({shortName}: {shortName: string}) => (
    <View className="relative inline-flex items-center justify-center w-6 h-6 overflow-hidden bg-gray-100 rounded-full border-2 border-gray-800">
        <Text className="font-medium text-gray-600 text-xs">{shortName.toUpperCase()}</Text>
    </View>
);
export const LargeAvatar = ({
    shortName,
    badge,
    highlighted = false,
}: {
    shortName: string;
    badge?: string | null;
    highlighted: boolean;
}) => (
    <View>
        <View
            className={classNames(
                'relative inline-flex items-center justify-center w-12 h-12 overflow-hidden bg-gray-100 rounded-full',
                highlighted ? 'border-2 border-violet-600' : 'border-2 border-gray-800',
            )}>
            <Text className="font-medium text-gray-600 text-xl">{shortName.toUpperCase()}</Text>
        </View>
        {badge ? (
            <View className="absolute -bottom-1 -right-2 z-10 px-1 flex items-center justify-center bg-violet-600 rounded-lg">
                <Text className="text-xs font-medium text-gray-200">{badge}</Text>
            </View>
        ) : null}
    </View>
);
>>>>>>> 8fd55bd (Optimise date adding)
