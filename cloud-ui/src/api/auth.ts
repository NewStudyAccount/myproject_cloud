import request from '@/utils/request'

// 登录
export function login(data: { username: string; password: string }) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

// 登出
export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

// 刷新 Token
export function refreshToken() {
  return request({
    url: '/auth/refresh',
    method: 'post'
  })
}
