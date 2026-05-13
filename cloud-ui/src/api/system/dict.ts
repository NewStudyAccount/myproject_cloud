import request from '@/utils/request'

export function listDictType(data: any) {
  return request({ url: '/system/dict/type/list', method: 'post', data })
}

export function addDictType(data: any) {
  return request({ url: '/system/dict/type/add', method: 'post', data })
}

export function updateDictType(data: any) {
  return request({ url: '/system/dict/type/update', method: 'post', data })
}

export function deleteDictType(ids: number[]) {
  return request({ url: '/system/dict/type/delete', method: 'post', data: ids })
}

export function listDictData(dictType: string) {
  return request({ url: '/system/dict/data/list', method: 'post', data: { dictType } })
}

export function addDictData(data: any) {
  return request({ url: '/system/dict/data/add', method: 'post', data })
}

export function updateDictData(data: any) {
  return request({ url: '/system/dict/data/update', method: 'post', data })
}

export function deleteDictData(ids: number[]) {
  return request({ url: '/system/dict/data/delete', method: 'post', data: ids })
}
