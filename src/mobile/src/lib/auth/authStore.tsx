import {create} from 'zustand';

export interface AuthState {
    token: string;
    user: string;
    setAuth: (jwt: string, user: string) => void;
}

export const useAuthStore = create<AuthState>(set => ({
    token: '',
    user: '',
    setAuth: (jwt: string, user: string) => set({token: jwt, user: user}),
}));
