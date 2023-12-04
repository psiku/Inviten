import React from 'react';
import {
    Text,
    TouchableOpacity,
    TouchableOpacityProps,
    View,
} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';

export const PickButton = (
    props: React.JSX.IntrinsicAttributes &
        React.JSX.IntrinsicClassAttributes<TouchableOpacity> &
        Readonly<TouchableOpacityProps>,
) => (
    <TouchableOpacity {...props}>
        <View className="my-2 flex items-center">
            <Icon name="rightcircle" size={25} color="#a3e635" />
            <Text className="mt-2 text-gray-200 font-semibold">Pick</Text>
        </View>
    </TouchableOpacity>
);
