import request from '@/utils/request'

export function listUser(data: any) {
  return request({ url: '/system/user/list', method: 'post', data })
}

export function addUser(data: any) {
  return request({ url: '/system/user/add', method: 'post', data })
}

export function updateUser(data: any) {
  return request({ url: '/system/user/update', method: 'post', data })
}

export function deleteUser(ids: number[]) {
  return request({ url: '/system/user/delete', method: 'post', data: ids })
}
