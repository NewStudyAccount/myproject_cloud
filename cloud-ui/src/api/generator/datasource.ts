import request from '@/utils/request'

export function listDatasource(data: any) {
  return request({ url: '/generator/datasource/list', method: 'post', data })
}

export function addDatasource(data: any) {
  return request({ url: '/generator/datasource/add', method: 'post', data })
}

export function updateDatasource(data: any) {
  return request({ url: '/generator/datasource/update', method: 'post', data })
}

export function deleteDatasource(ids: number[]) {
  return request({ url: '/generator/datasource/delete', method: 'post', data: ids })
}

export function getTableNames(datasourceId: number) {
  return request({ url: '/generator/datasource/tables', method: 'post', data: datasourceId })
}
