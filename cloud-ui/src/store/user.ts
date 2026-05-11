import { defineStore } from 'pinia'
import { login, logout } from '@/api/auth'

interface UserInfo {
  id: number
  username: string
  nickname: string
  avatar: string
}

interface UserState {
  token: string
  userInfo: UserInfo | null
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: localStorage.getItem('token') || '',
    userInfo: null
  }),

  actions: {
    async login(username: string, password: string) {
      const res: any = await login({ username, password })
      this.token = res.data.accessToken
      localStorage.setItem('token', res.data.accessToken)
      return res
    },

    async logout() {
      try {
        await logout()
      } finally {
        this.token = ''
        this.userInfo = null
        localStorage.removeItem('token')
      }
    },

    setUserInfo(userInfo: UserInfo) {
      this.userInfo = userInfo
    }
  }
})
