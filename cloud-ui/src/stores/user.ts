import { defineStore } from 'pinia'
import { ref } from 'vue'
import request from '@/utils/request'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref<any>({})

  async function login(username: string, password: string) {
    const res: any = await request({
      url: '/auth/login',
      method: 'post',
      data: { username, password },
    })
    token.value = res.data.accessToken
    localStorage.setItem('token', res.data.accessToken)
    return res
  }

  function logout() {
    token.value = ''
    userInfo.value = {}
    localStorage.removeItem('token')
  }

  return {
    token,
    userInfo,
    login,
    logout,
  }
})
