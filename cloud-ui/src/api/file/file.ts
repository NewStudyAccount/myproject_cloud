import request from '@/utils/request'

export function listFile(data: any) {
  return request({ url: '/file/list', method: 'post', data })
}

export function deleteFile(ids: number[]) {
  return request({ url: '/file/delete', method: 'post', data: ids })
}
