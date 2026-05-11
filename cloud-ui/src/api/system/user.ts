import request from '@/utils/request'

// 查询用户列表
export function listUser(data: any) {
  return request({
    url: '/system/user/list',
    method: 'post',
    data
  })
}

// 查询用户详情
export function detailUser(data: any) {
  return request({
    url: '/system/user/detail',
    method: 'post',
    data
  })
}

// 新增用户
export function addUser(data: any) {
  return request({
    url: '/system/user/add',
    method: 'post',
    data
  })
}

// 更新用户
export function updateUser(data: any) {
  return request({
    url: '/system/user/update',
    method: 'post',
    data
  })
}

// 删除用户
export function deleteUser(data: any) {
  return request({
    url: '/system/user/delete',
    method: 'post',
    data
  })
}
