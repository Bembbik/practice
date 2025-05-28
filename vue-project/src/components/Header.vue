<template>
  <header v-if="isAuthenticated" class="bg-gradient-to-r from-slate-900 to-slate-800 shadow-lg">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <!-- Логотип и название -->
        <div class="flex items-center">
          <div class="flex-shrink-0 flex items-center space-x-3">
            <div
              class="w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-600 rounded-lg flex items-center justify-center"
            >
              <svg class="w-5 h-5 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z"
                />
              </svg>
            </div>
            <div class="hidden sm:block">
              <h1 class="text-xl font-bold text-white">Система Тикетов</h1>
              <p class="text-xs text-slate-300">{{ getRoleLabel(userRole) }}</p>
            </div>
            <!-- Мобильная версия названия -->
            <div class="sm:hidden">
              <h1 class="text-lg font-bold text-white">Тикеты</h1>
            </div>
          </div>
        </div>

        <!-- Навигация дл�� десктопа -->
        <nav class="hidden md:flex space-x-1">
          <router-link
            v-for="item in navigationItems"
            :key="item.path"
            :to="item.path"
            class="px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200 flex items-center space-x-2"
            :class="{
              'bg-slate-700 text-white': isActiveRoute(item.path),
              'text-slate-300 hover:text-white hover:bg-slate-700': !isActiveRoute(item.path),
            }"
          >
            <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                :d="item.icon"
              />
            </svg>
            <span>{{ item.label }}</span>
          </router-link>
        </nav>

        <!-- Меню пользователя -->
        <div class="flex items-center space-x-2 sm:space-x-4">
          <!-- Уведомления - скрыты на маленьких экранах -->
          <button
            class="hidden sm:flex relative p-2 text-slate-300 hover:text-white hover:bg-slate-700 rounded-lg transition-all duration-200"
          >
            <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"
              />
            </svg>
            <span
              v-if="notificationCount > 0"
              class="absolute -top-1 -right-1 h-4 w-4 bg-red-500 text-white text-xs rounded-full flex items-center justify-center animate-pulse"
            >
              {{ notificationCount }}
            </span>
          </button>

          <!-- Профиль -->
          <div class="relative" ref="profileMenu">
            <button
              @click="toggleProfileMenu"
              class="flex items-center space-x-2 sm:space-x-3 text-sm rounded-lg hover:bg-slate-700 p-1 sm:p-2 transition-all duration-200"
            >
              <div
                class="h-7 w-7 sm:h-8 sm:w-8 rounded-lg flex items-center justify-center shadow-lg"
                :class="getUserAvatarClass(userRole)"
              >
                <span class="text-white font-semibold text-sm">{{ userInitial }}</span>
              </div>
              <div class="hidden md:block text-left">
                <div class="text-slate-200 font-medium">{{ user?.username }}</div>
                <div class="text-xs text-slate-400">{{ getRoleLabel(userRole) }}</div>
              </div>
              <!-- Показываем имя пользователя на планшетах -->
              <div class="hidden sm:block md:hidden text-left">
                <div class="text-slate-200 font-medium text-sm">{{ user?.username }}</div>
              </div>
              <svg
                class="h-3 w-3 sm:h-4 sm:w-4 text-slate-400 transition-transform duration-200"
                :class="{ 'rotate-180': profileMenuOpen }"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M19 9l-7 7-7-7"
                />
              </svg>
            </button>

            <!-- Выпадающее меню профиля -->
            <transition
              enter-active-class="transition ease-out duration-200"
              enter-from-class="transform opacity-0 scale-95"
              enter-to-class="transform opacity-100 scale-100"
              leave-active-class="transition ease-in duration-75"
              leave-from-class="transform opacity-100 scale-100"
              leave-to-class="transform opacity-0 scale-95"
            >
              <div
                v-show="profileMenuOpen"
                class="absolute right-0 mt-2 w-56 bg-slate-800 rounded-xl shadow-xl z-50 border border-slate-700"
              >
                <div class="px-4 py-3 border-b border-slate-700">
                  <div class="flex items-center space-x-3">
                    <div
                      class="h-10 w-10 rounded-lg flex items-center justify-center"
                      :class="getUserAvatarClass(userRole)"
                    >
                      <span class="text-white font-semibold">{{ userInitial }}</span>
                    </div>
                    <div>
                      <div class="text-slate-200 font-medium">{{ user?.fullName }}</div>
                      <div class="text-xs text-slate-400">{{ getRoleLabel(userRole) }}</div>
                    </div>
                  </div>
                </div>
                <div class="py-2">
                  <!-- Уведомления в мобильном меню -->
                  <a
                    href="#"
                    class="sm:hidden flex items-center px-4 py-2 text-sm text-slate-300 hover:bg-slate-700 hover:text-white transition-colors duration-200"
                    @click.prevent="toggleNotifications"
                  >
                    <div class="relative mr-3">
                      <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"
                        />
                      </svg>
                      <span
                        v-if="notificationCount > 0"
                        class="absolute -top-1 -right-1 h-3 w-3 bg-red-500 text-white text-xs rounded-full flex items-center justify-center"
                        style="font-size: 8px"
                      >
                        {{ notificationCount }}
                      </span>
                    </div>
                    Уведомления
                  </a>
                  <a
                    href="#"
                    class="flex items-center px-4 py-2 text-sm text-slate-300 hover:bg-slate-700 hover:text-white transition-colors duration-200"
                    @click.prevent="viewProfile"
                  >
                    <svg class="w-4 h-4 mr-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                      />
                    </svg>
                    Профиль
                  </a>
                  <a
                    href="#"
                    class="flex items-center px-4 py-2 text-sm text-slate-300 hover:bg-slate-700 hover:text-white transition-colors duration-200"
                    @click.prevent="logout"
                  >
                    <svg class="w-4 h-4 mr-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
                      />
                    </svg>
                    Выйти
                  </a>
                </div>
              </div>
            </transition>
          </div>

          <!-- Мобильное меню -->
          <div class="md:hidden" ref="mobileMenuButton">
            <button
              @click="toggleMobileMenu"
              class="p-1.5 sm:p-2 rounded-lg text-slate-300 hover:text-white hover:bg-slate-700 transition-all duration-200"
            >
              <svg
                class="h-5 w-5 sm:h-6 sm:w-6 transition-transform duration-200"
                :class="{ 'rotate-90': mobileMenuOpen }"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  v-if="!mobileMenuOpen"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4 6h16M4 12h16M4 18h16"
                />
                <path
                  v-else
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M6 18L18 6M6 6l12 12"
                />
              </svg>
            </button>
          </div>
        </div>
      </div>

      <!-- Мобильная навигация -->
      <transition
        enter-active-class="transition ease-out duration-200"
        enter-from-class="transform opacity-0 scale-95"
        enter-to-class="transform opacity-100 scale-100"
        leave-active-class="transition ease-in duration-75"
        leave-from-class="transform opacity-100 scale-100"
        leave-to-class="transform opacity-0 scale-95"
      >
        <div
          v-show="mobileMenuOpen"
          class="md:hidden border-t border-slate-700 bg-slate-800"
          ref="mobileMenu"
        >
          <div class="px-2 pt-2 pb-3 space-y-1">
            <router-link
              v-for="item in navigationItems"
              :key="item.path"
              :to="item.path"
              @click="closeMobileMenu"
              class="flex items-center space-x-3 px-3 py-2 rounded-lg text-base font-medium transition-all duration-200"
              :class="{
                'bg-slate-700 text-white': isActiveRoute(item.path),
                'text-slate-300 hover:text-white hover:bg-slate-700': !isActiveRoute(item.path),
              }"
            >
              <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  :d="item.icon"
                />
              </svg>
              <span>{{ item.label }}</span>
            </router-link>
          </div>
        </div>
      </transition>
    </div>
  </header>
</template>

<script>
export default {
  name: 'Header',
  data() {
    return {
      profileMenuOpen: false,
      mobileMenuOpen: false,
      notificationCount: 3,
    }
  },
  computed: {
    isAuthenticated() {
      const user = this.$store?.getters?.isAuthenticated || JSON.parse(localStorage.getItem('user'))
      return !!user
    },
    user() {
      const user = this.$store?.getters?.user || JSON.parse(localStorage.getItem('user'))
      return user
    },
    userRole() {
      return this.user?.role || 'team'
    },
    userInitial() {
      return this.user?.username ? this.user.username.charAt(0).toUpperCase() : 'A'
    },
    navigationItems() {
      const routes = {
        ADMIN: [
          {
            path: '/admin',
            label: 'Панель управления',
            icon: 'M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z',
          },
          {
            path: '/admin/tickets',
            label: 'Тикеты',
            icon: 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z',
          },
          {
            path: '/admin/users',
            label: 'Пользователи',
            icon: 'M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z',
          },
        ],
        DEPARTMENT: [
          {
            path: '/department',
            label: 'Панель отдела',
            icon: 'M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4',
          },
          {
            path: '/department/tickets',
            label: 'Тикеты отдела',
            icon: 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z',
          },
          {
            path: '/department/team-members',
            label: 'Команды',
            icon: 'M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z',
          },
        ],
        TEAMLEAD: [
          {
            path: '/team-lead',
            label: 'Панель команды',
            icon: 'M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z',
          },
          {
            path: '/team-lead/tickets',
            label: 'Тикеты команды',
            icon: 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z',
          },
          {
            path: '/team-lead/members',
            label: 'Участники',
            icon: 'M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z',
          },
        ],
        TEAM_MEMBER: [
          {
            path: '/team/tickets',
            label: 'Мои тикеты',
            icon: 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z',
          },
        ],
      }

      return routes[this.userRole] || routes.team
    },
  },

  methods: {
    toggleProfileMenu() {
      this.profileMenuOpen = !this.profileMenuOpen
      if (this.profileMenuOpen) {
        this.mobileMenuOpen = false
      }
    },
    toggleMobileMenu() {
      this.mobileMenuOpen = !this.mobileMenuOpen
      if (this.mobileMenuOpen) {
        this.profileMenuOpen = false
      }
    },
    closeMobileMenu() {
      this.mobileMenuOpen = false
    },
    toggleNotifications() {
      this.profileMenuOpen = false
      // Логика для уведомлений
      console.log('Открыть уведомления')
    },
    isActiveRoute(path) {
      const currentPath = this.$route.path

      // Точное совпадение
      if (currentPath === path) {
        return true
      }

      // Для вложенных маршрутов проверяем только если это не родительский маршрут
      // Например, для /admin/tickets не должен подсвечиваться /admin
      if (path !== '/' && currentPath.startsWith(path + '/')) {
        // Проверяем, есть ли в navigationItems более точное совпадение
        const hasMoreSpecificMatch = this.navigationItems.some(
          (item) =>
            item.path !== path &&
            item.path.startsWith(path + '/') &&
            currentPath.startsWith(item.path),
        )

        // Если есть более точное совпадение, не подсвечиваем текущий пункт
        return !hasMoreSpecificMatch
      }

      return false
    },
    getRoleLabel(role) {
      const labels = {
        ADMIN: 'Администратор',
        DEPARTMENT: 'Руководитель отдела',
        TEAMLEAD: 'Тимлид',
        TEAM_MEMBER: 'Сотрудник',
      }
      return labels[role] || 'Пользователь'
    },
    getUserAvatarClass(role) {
      const classes = {
        admin: 'bg-gradient-to-br from-purple-500 to-indigo-600',
        department: 'bg-gradient-to-br from-blue-500 to-cyan-600',
        team_lead: 'bg-gradient-to-br from-emerald-500 to-green-600',
        team: 'bg-gradient-to-br from-amber-500 to-orange-600',
      }
      return classes[role] || 'bg-gradient-to-br from-slate-500 to-slate-600'
    },
    viewProfile() {
      this.profileMenuOpen = false
      // Логика для проcмотра профиля
      console.log('Открыть профиль')
    },
    logout() {
      if (this.$store) {
        this.$store.dispatch('logout')
      } else {
        localStorage.removeItem('user')
      }
      this.$router.push({ name: 'Login' })
    },
    handleClickOutside(event) {
      // Проверяем клик вне меню профиля
      if (
        this.profileMenuOpen &&
        this.$refs.profileMenu &&
        !this.$refs.profileMenu.contains(event.target)
      ) {
        this.profileMenuOpen = false
      }

      // Проверяем клик вне мобильного меню
      if (this.mobileMenuOpen) {
        const mobileButton = this.$refs.mobileMenuButton
        const mobileMenu = this.$refs.mobileMenu

        if (
          mobileButton &&
          mobileMenu &&
          !mobileButton.contains(event.target) &&
          !mobileMenu.contains(event.target)
        ) {
          this.mobileMenuOpen = false
        }
      }
    },
  },
  mounted() {
    // Закрытие меню при клике вне их области
    document.addEventListener('click', this.handleClickOutside)
  },
  beforeUnmount() {
    // Удаляем обработчик событий при уничтожении компонента
    document.removeEventListener('click', this.handleClickOutside)
  },
}
</script>

<style scoped>
/* Анимации */
.rotate-90 {
  transform: rotate(90deg);
}

.rotate-180 {
  transform: rotate(180deg);
}

/* Улучшенные переходы */
.transition-transform {
  transition-property: transform;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 200ms;
}

/* Стили для активных ссылок */
.router-link-active {
  background-color: #334155 !important; /* цвет bg-slate-700 */
  color: #ffffff !important; /* цвет white */
}

/* Анимация пульсации для уведомлений */
@keyframes pulse {
  0%,
  100% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
}

.animate-pulse {
  animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}
</style>
