import {create} from 'zustand';

export interface AuthState {
    token: string;
    setToken: (jwt: string) => void;
}

export const useAuthStore = create<AuthState>(set => ({
    token: '',
    setToken: jwt => set({token: jwt}),
}));
