import {apiClient} from '../api/apiClient';

export const getUserMeetings = async (token: string) => {
    const response = await apiClient.get('/users/meetings', {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });

    return response.data;
};

export const addUserMeeting = async (token: string, meeting: any) => {
    await apiClient.post('/meetings', meeting, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};
