/**
 * @format
 */
import {Navigation} from 'react-native-navigation';
import App from './App';
import {SetupScreen} from './src/screens/auth/SetupScreen';
import {setupRoot} from './src/navigation/roots';
import {HomeScreen} from './src/screens/home/HomeScreen';
import {MeetingScreen} from './src/screens/meeting/MeetingScreen';
import {MeetingShareButton} from './src/components/meeting/MeetingShareButton';

Navigation.setDefaultOptions({
    topBar: {
        background: {
            color: 'black',
        },
    },
    layout: {
        componentBackgroundColor: 'black',
    },
});

Navigation.registerComponent(
    'com.inviten.MeetingShareButton',
    () => MeetingShareButton,
);

Navigation.registerComponent('com.inviten.SetupScreen', () => SetupScreen);
Navigation.registerComponent('com.inviten.HomeScreen', () => HomeScreen);
Navigation.registerComponent('com.inviten.MeetingScreen', () => MeetingScreen);

Navigation.events().registerAppLaunchedListener(() => {
    Navigation.setRoot(setupRoot);
});
