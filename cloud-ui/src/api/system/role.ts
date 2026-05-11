import request from '@/utils/request'

// 查询角色列表
export function listRole(data: any) {
  return request({
    url: '/system/role/list',
    method: 'post',
    data
  })
}

// 查询所有角色
export function listAllRole() {
  return request({
    url: '/system/role/listAll',
    method: 'post'
  })
}

// 查询角色详情
export function detailRole(data: any) {
  return request({
    url: '/system/role/detail',
    method: 'post',
    data
  })
}

// 新增角色
export function addRole(data: any) {
  return request({
    url: '/system/role/add',
    method: 'post',
    data
  })
}

// 更新角色
export function updateRole(data: any) {
  return request({
    url: '/system/role/update',
    method: 'post',
    data
  })
}

// 删除角色
export function deleteRole(data: any) {
  return request({
    url: '/system/role/delete',
    method: 'post',
    data
  })
}
