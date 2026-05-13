import request from '@/utils/request'

export function listMenu(data?: any) {
  return request({ url: '/system/menu/list', method: 'post', data: data || {} })
}

export function menuTree() {
  return request({ url: '/system/menu/tree', method: 'post' })
}

export function addMenu(data: any) {
  return request({ url: '/system/menu/add', method: 'post', data })
}

export function updateMenu(data: any) {
  return request({ url: '/system/menu/update', method: 'post', data })
}

export function deleteMenu(id: number) {
  return request({ url: '/system/menu/delete', method: 'post', data: id })
}
