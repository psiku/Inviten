import React, {useState} from 'react';
import {Meeting} from '../../types/Meeting';
import {SafeAreaView, Text, View} from 'react-native';
import {MeetingList} from '../../components/meeting/MeetingList';
import {Navigation, NavigationFunctionComponent} from 'react-native-navigation';
import {MeetingAddButton} from '../../components/meeting/MeetingAddButton';

const data: Meeting[] = [
    {id: '1', name: 'Urodziny Maćka'},
    {id: '2', name: 'Kawa z Kasią'},
    {id: '3', name: 'Chlanie w zyrafie'},
];

export const HomeScreen: NavigationFunctionComponent = props => {
    const [meetings, setMeetings] = useState<Meeting[]>(data);

    const handleMeetingSelect = (meeting: Meeting) => {
        console.log('Selected meeting', meeting);
        Navigation.push(props.componentId, {
            component: {
                name: 'com.inviten.MeetingScreen',
                passProps: {
                    meeting,
                },
            },
        });
    };

    const handleMeetingAdd = (meeting: Meeting) => {
        setMeetings([...meetings, meeting]);
    };

    return (
        <SafeAreaView>
            <View className="p-5">
                <View className="flex-row justify-between items-center">
                    <Text className="text-gray-100 text-3xl font-semibold">
                        Meetings
                    </Text>
                    <View>
                        <MeetingAddButton onAdd={handleMeetingAdd} />
                    </View>
                </View>
                <View className="mt-10">
                    <MeetingList
                        meetings={meetings}
                        onSelect={handleMeetingSelect}
                    />
                </View>
            </View>
        </SafeAreaView>
    );
};
