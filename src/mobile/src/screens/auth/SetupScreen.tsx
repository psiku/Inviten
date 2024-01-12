import React, {useState} from 'react';
import {Navigation} from 'react-native-navigation';
import {Button, Colors, Text, TextField, View} from 'react-native-ui-lib';
import {homeRoot} from '../../navigation/roots';
import {useAuthStore} from '../../lib/auth/authStore';
import {getAuth} from '../../lib/auth/authService';

export const SetupScreen = () => {
    const {setAuth} = useAuthStore();

    const phoneRegex = /^\d{3}\s?\d{3}\s?\d{3}$/;
    const [userPhoneNumber, setUserPhoneNumber] = useState<string>('');

    const isPhoneNumberValid = (value: string) => phoneRegex.test(value);

    const goHomeScreen = () => Navigation.setRoot(homeRoot);

    const authenticate = async () => {
        const {token, phoneNumber} = await getAuth(userPhoneNumber);
        console.log('token', token, 'phoneNumber', userPhoneNumber);

        setAuth(token, phoneNumber);

        goHomeScreen();
    };

    return (
        <View flex paddingH-25 paddingT-80>
            <Text grey80 text20>
                Hello!
            </Text>
            <Text grey60 text70>
                Please provide your mobile phone number to authenticate
            </Text>
            <View marginT-50 marginB-50 center>
                <TextField
                    backgroundDark
                    blue30
                    text40
                    maxLength={9}
                    keyboardType="numeric"
                    placeholder="123 777 888"
                    value={userPhoneNumber}
                    onChangeText={text => setUserPhoneNumber(text)}
                />
            </View>
            <View center>
                <Button
                    text70
                    black
                    backgroundColor={Colors.grey80}
                    disabledBackgroundColor={Colors.grey20}
                    label="Continue"
                    disabled={!isPhoneNumberValid(userPhoneNumber)}
                    onPress={authenticate}
                />
            </View>
        </View>
    );
};
