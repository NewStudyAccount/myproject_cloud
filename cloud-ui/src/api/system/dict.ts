import request from '@/utils/request'

// 查询字典类型列表
export function listDictType(data: any) {
  return request({
    url: '/system/dict/type/list',
    method: 'post',
    data
  })
}

// 查询所有字典类型
export function listAllDictType() {
  return request({
    url: '/system/dict/type/listAll',
    method: 'post'
  })
}

// 查询字典类型详情
export function detailDictType(data: any) {
  return request({
    url: '/system/dict/type/detail',
    method: 'post',
    data
  })
}

// 新增字典类型
export function addDictType(data: any) {
  return request({
    url: '/system/dict/type/add',
    method: 'post',
    data
  })
}

// 更新字典类型
export function updateDictType(data: any) {
  return request({
    url: '/system/dict/type/update',
    method: 'post',
    data
  })
}

// 删除字典类型
export function deleteDictType(data: any) {
  return request({
    url: '/system/dict/type/delete',
    method: 'post',
    data
  })
}

// 查询字典数据列表
export function listDictData(data: any) {
  return request({
    url: '/system/dict/data/list',
    method: 'post',
    data
  })
}

// 根据字典类型查询字典数据
export function listDictDataByType(data: string) {
  return request({
    url: '/system/dict/data/listByDictType',
    method: 'post',
    data
  })
}

// 查询字典数据详情
export function detailDictData(data: any) {
  return request({
    url: '/system/dict/data/detail',
    method: 'post',
    data
  })
}

// 新增字典数据
export function addDictData(data: any) {
  return request({
    url: '/system/dict/data/add',
    method: 'post',
    data
  })
}

// 更新字典数据
export function updateDictData(data: any) {
  return request({
    url: '/system/dict/data/update',
    method: 'post',
    data
  })
}

// 删除字典数据
export function deleteDictData(data: any) {
  return request({
    url: '/system/dict/data/delete',
    method: 'post',
    data
  })
}
