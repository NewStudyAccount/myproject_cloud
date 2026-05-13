import request from '@/utils/request'

export function listRole(data: any) {
  return request({ url: '/system/role/list', method: 'post', data })
}

export function addRole(data: any) {
  return request({ url: '/system/role/add', method: 'post', data })
}

export function updateRole(data: any) {
  return request({ url: '/system/role/update', method: 'post', data })
}

export function deleteRole(ids: number[]) {
  return request({ url: '/system/role/delete', method: 'post', data: ids })
}
