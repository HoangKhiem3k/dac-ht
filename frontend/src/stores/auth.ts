import { defineStore } from 'pinia';
import { type Auth } from '@/types/auth';

export const useAuthStore = defineStore({
  id: 'authStore',
  state: () => ({
    auth: {} as Auth
  }),
  getters: {
    getAuth(state): Auth {
      return state.auth
    }
  },
  actions: {
    checkAuth(): boolean {
      //TODO: check authen here
      return true;
    }
  }
})
