import request from '@/utils/request'

export function listGenConfig(data: any) {
  return request({ url: '/generator/gen/list', method: 'post', data })
}

export function addGenConfig(data: any) {
  return request({ url: '/generator/gen/add', method: 'post', data })
}

export function updateGenConfig(data: any) {
  return request({ url: '/generator/gen/update', method: 'post', data })
}

export function deleteGenConfig(ids: number[]) {
  return request({ url: '/generator/gen/delete', method: 'post', data: ids })
}

export function previewCode(configId: number) {
  return request({ url: '/generator/gen/preview', method: 'post', data: configId })
}

export function downloadCode(configId: number) {
  return request({ url: '/generator/gen/download', method: 'post', data: configId, responseType: 'blob' })
}
