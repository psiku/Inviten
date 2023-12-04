import React, {useState} from 'react';
import {Meeting} from '../../types/Meeting';
import {SafeAreaView, Text, View} from 'react-native';
import {MeetingList} from '../../components/meeting/MeetingList';
import {Navigation, NavigationFunctionComponent} from 'react-native-navigation';
import {MeetingAddButton} from '../../components/meeting/MeetingAddButton';

const generateRandomString = length =>
    [...Array(length)]
        .map(() =>
            String.fromCharCode(
                Math.floor(Math.random() * 26) +
                    (Math.random() > 0.5 ? 65 : 97),
            ),
        )
        .join('');

const getRandomPeople = (n: number) =>
    Array.from(Array(n).keys()).map(i => ({
        id: `${i}`,
        name: generateRandomString(5),
    }));

const data: Meeting[] = [
    {
        id: '1',
        name: 'Urodziny Maćka',
        createdAt: new Date(),
        participants: getRandomPeople(5),
    },
    {
        id: '2',
        name: 'Kawa z Kasią',
        createdAt: new Date(),
        participants: getRandomPeople(2),
    },
    {
        id: '3',
        name: 'Opijanie 10 pkt za II etap PAP',
        createdAt: new Date(),
        participants: getRandomPeople(30),
    },
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
