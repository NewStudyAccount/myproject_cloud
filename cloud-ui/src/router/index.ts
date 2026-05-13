import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const Layout = () => import('@/layout/index.vue')

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录' },
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '首页', icon: 'HomeFilled' },
      },
    ],
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    meta: { title: '系统管理', icon: 'Setting' },
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/system/user/index.vue'),
        meta: { title: '用户管理', icon: 'User' },
      },
      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/system/role/index.vue'),
        meta: { title: '角色管理', icon: 'UserFilled' },
      },
      {
        path: 'menu',
        name: 'Menu',
        component: () => import('@/views/system/menu/index.vue'),
        meta: { title: '菜单管理', icon: 'Menu' },
      },
      {
        path: 'dict',
        name: 'Dict',
        component: () => import('@/views/system/dict/index.vue'),
        meta: { title: '字典管理', icon: 'Collection' },
      },
    ],
  },
  {
    path: '/generator',
    component: Layout,
    redirect: '/generator/datasource',
    meta: { title: '代码生成', icon: 'Document' },
    children: [
      {
        path: 'datasource',
        name: 'Datasource',
        component: () => import('@/views/generator/datasource/index.vue'),
        meta: { title: '数据源管理', icon: 'Connection' },
      },
      {
        path: 'config',
        name: 'GenConfig',
        component: () => import('@/views/generator/config/index.vue'),
        meta: { title: '生成配置', icon: 'Setting' },
      },
    ],
  },
  {
    path: '/file',
    component: Layout,
    children: [
      {
        path: '',
        name: 'File',
        component: () => import('@/views/file/index.vue'),
        meta: { title: '文件管理', icon: 'FolderOpened' },
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    next()
  } else if (!token) {
    next('/login')
  } else {
    next()
  }
})

export default router
