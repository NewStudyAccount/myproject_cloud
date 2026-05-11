import request from '@/utils/request'

// 查询菜单列表
export function listMenu(data: any) {
  return request({
    url: '/system/menu/list',
    method: 'post',
    data
  })
}

// 查询菜单树
export function treeMenu() {
  return request({
    url: '/system/menu/tree',
    method: 'post'
  })
}

// 查询菜单详情
export function detailMenu(data: any) {
  return request({
    url: '/system/menu/detail',
    method: 'post',
    data
  })
}

// 新增菜单
export function addMenu(data: any) {
  return request({
    url: '/system/menu/add',
    method: 'post',
    data
  })
}

// 更新菜单
export function updateMenu(data: any) {
  return request({
    url: '/system/menu/update',
    method: 'post',
    data
  })
}

// 删除菜单
export function deleteMenu(data: any) {
  return request({
    url: '/system/menu/delete',
    method: 'post',
    data
  })
}
