import { createRouter, createWebHistory } from 'vue-router'
import AdminDashboard from '@/views/AdminDashboard.vue'
import TicketsAdmin from '@/views/admin/TicketsAdmin.vue'

const routes = [
  {
    path: '/',
    redirect: '/admin',
  },
  {
    path: '/admin',
    name: 'AdminDashboard',
    component: AdminDashboard,
    meta: { roles: ['ADMIN'] }, // Только администратор
  },
  {
    path: '/admin/tickets',
    name: 'TicketsAdmin',
    component: TicketsAdmin,
    meta: { roles: ['ADMIN'] }, // Админ и тимлид
  },
  {
    path: '/admin/users',
    name: 'AdminUsers',
    component: () => import('@/views/admin/Users.vue'),
    meta: { roles: ['ADMIN'] }, // Только админ
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/Profile.vue'),
    meta: { roles: ['TEAMLEAD', 'DEPARTMENT', 'TEAM_MEMBER', 'ADMIN'] }, // Все могут
  },
  {
    path: '/department',
    name: 'DepartmentDashboard',
    component: () => import('@/views/DepartmentDashboard.vue'),
    meta: { roles: ['DEPARTMENT'] }, // Тимлид и юзер
  },
  {
    path: '/department/tickets',
    name: 'TicketsDepartment',
    component: () => import('@/views/department/TicketsDepartment.vue'),
    meta: { roles: ['DEPARTMENT'] }, // Тимлид и юзер
  },
  {
    path: '/department/team-members',
    name: 'TeamMembers',
    component: () => import('@/views/department/TeamMembers.vue'),
    meta: { roles: ['DEPARTMENT'] },
  },
  {
    path: '/unauthorized',
    name: 'Unauthorized',
    component: () => import('@/views/Unauthorized.vue'),
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFound.vue'),
  },
  { path: '/tickets', name: 'Ticket', component: () => import('@/views/Ticket.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// Защита маршрутов по ролям
router.beforeEach((to, from, next) => {
  const user = JSON.parse(localStorage.getItem('user')) || null
  const isAuthenticated = !!user

  if (to.name === 'Login') {
    if (isAuthenticated) {
      return next({ name: 'AdminDashboard' })
    }
    return next()
  }

  if (!isAuthenticated) {
    return next({ name: 'Login' })
  }

  // Проверяем, есть ли ограничения по ролям
  if (to.meta.roles && Array.isArray(to.meta.roles)) {
    if (!to.meta.roles.some((role) => role === user.role)) {
      return next({ name: 'Unauthorized' })
    }
  }

  return next()
})

export default router
