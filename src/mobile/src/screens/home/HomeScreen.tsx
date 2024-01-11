import React from 'react';
import {Meeting} from '../../types/Meeting';
import {SafeAreaView, Text, View} from 'react-native';
import {MeetingList} from '../../components/meeting/MeetingList';
import {Navigation, NavigationFunctionComponent} from 'react-native-navigation';
import {MeetingAddButton} from '../../components/meeting/MeetingAddButton';

export const HomeScreen: NavigationFunctionComponent = props => {
    const handleMeetingSelect = (meeting: Meeting) => {
        Navigation.push(props.componentId, {
            component: {
                name: 'com.inviten.MeetingScreen',
                passProps: {
                    meeting,
                },
            },
        });
    };

    return (
        <SafeAreaView>
            <View className="p-5">
                <View className="flex-row justify-between items-center">
                    <Text className="text-gray-100 text-3xl font-semibold">
                        Meetings
                    </Text>
                    <View>
                        <MeetingAddButton />
                    </View>
                </View>
                <View className="mt-10">
                    <MeetingList onSelect={handleMeetingSelect} />
                </View>
            </View>
        </SafeAreaView>
    );
};
