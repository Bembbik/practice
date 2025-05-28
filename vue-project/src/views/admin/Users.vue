<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
    <!-- Основной контент -->
    <main class="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
      <!-- Заголовок с поиском -->
      <div class="mb-8">
        <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
          <div>
            <h1 class="text-3xl font-bold text-slate-900">Управление пользователями</h1>
            <p class="mt-2 text-slate-600">
              Создавайте и управляйте учетными записями пользователей
            </p>
          </div>

          <!-- Поиск -->
          <div class="flex items-center space-x-4">
            <div class="relative">
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg
                  class="h-5 w-5 text-slate-400"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                  />
                </svg>
              </div>
              <input
                v-model="searchQuery"
                type="text"
                placeholder="Поиск пользователей..."
                class="block w-full pl-10 pr-3 py-2 border border-slate-300 rounded-lg leading-5 bg-white placeholder-slate-500 focus:outline-none focus:placeholder-slate-400 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
              />
            </div>

            <button
              @click="openCreateModal"
              class="flex items-center px-4 py-2 bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white rounded-lg shadow-lg hover:shadow-xl transition-all duration-200 transform hover:scale-105"
            >
              <svg class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 4v16m8-8H4"
                />
              </svg>
              Новый пользователь
            </button>
          </div>
        </div>
      </div>

      <!-- Фильтры -->
      <div class="bg-white rounded-xl shadow-lg border border-slate-200 p-6 mb-6">
        <div class="flex flex-wrap items-center gap-4">
          <div class="flex items-center space-x-2">
            <label class="text-sm font-medium text-slate-700">Роль:</label>
            <select
              v-model="selectedRole"
              class="border border-slate-300 rounded-lg px-3 py-1 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
              <option value="">Все роли</option>
              <option value="ADMIN">Администратор</option>
              <option value="DEPARTMENT">Руководитель отдела</option>
              <option value="TEAMLEAD">Тимлид</option>
              <option value="TEAM_MEMBER">Сотрудник</option>
            </select>
          </div>

          <div class="flex items-center space-x-2">
            <label class="text-sm font-medium text-slate-700">На странице:</label>
            <select
              v-model="itemsPerPage"
              class="border border-slate-300 rounded-lg px-3 py-1 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
              <option :value="5">5</option>
              <option :value="10">10</option>
              <option :value="25">25</option>
              <option :value="50">50</option>
            </select>
          </div>
        </div>
      </div>

      <!-- Таблица пользователей -->
      <div class="bg-white rounded-xl shadow-lg border border-slate-200 overflow-hidden">
        <div
          class="px-6 py-4 border-b border-slate-200 bg-gradient-to-r from-slate-50 to-slate-100"
        >
          <h2 class="text-lg font-semibold text-slate-900 flex items-center">
            <svg
              class="w-5 h-5 mr-2 text-slate-600"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
              />
            </svg>
            Список пользователей
            <span class="ml-2 text-sm text-slate-500">({{ filteredUsers.length }})</span>
          </h2>
        </div>

        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-slate-200">
            <thead class="bg-slate-50">
              <tr>
                <th class="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                  <div class="flex items-center space-x-1">
                    <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                      />
                    </svg>
                    <span>ФИО</span>
                  </div>
                </th>
                <th class="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                  <div class="flex items-center space-x-1">
                    <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.031 9-11.622 0-1.042-.133-2.052-.382-3.016z"
                      />
                    </svg>
                    <span>Роль</span>
                  </div>
                </th>
                <th class="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                  <div class="flex items-center space-x-1">
                    <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M15 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                      />
                    </svg>
                    <span>Логин</span>
                  </div>
                </th>
                <th class="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                  <div class="flex items-center space-x-1">
                    <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M12 5v.01M12 12v.01M12 19v.01M12 6a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2zm0 7a1 1 0 110-2 1 1 0 010 2z"
                      />
                    </svg>
                    <span>Действия</span>
                  </div>
                </th>
              </tr>
            </thead>
            <tbody class="divide-y divide-slate-200 bg-white">
              <tr
                v-for="user in paginatedUsers"
                :key="user.id"
                class="hover:bg-gradient-to-r hover:from-slate-50 hover:to-slate-100 transition-all duration-200"
              >
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="flex items-center">
                    <div class="flex-shrink-0 h-10 w-10">
                      <div
                        class="h-10 w-10 rounded-full bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center shadow-lg"
                      >
                        <span class="text-white font-semibold text-sm">
                          {{ getUserInitials(user) }}
                        </span>
                      </div>
                    </div>
                    <div class="ml-4">
                      <div class="text-sm font-medium text-slate-900">
                        {{ user.fullName }}
                      </div>
                    </div>
                  </div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <span
                    class="inline-flex items-center px-3 py-1 rounded-full text-xs font-medium"
                    :class="getRoleClass(user.role)"
                  >
                    {{ getRoleLabel(user.role) }}
                  </span>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-slate-900 font-mono">
                  {{ user.username }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                  <div class="flex items-center space-x-2">
                    <button
                      @click="editUser(user)"
                      class="flex items-center px-3 py-1 text-xs font-medium text-blue-600 hover:text-blue-800 hover:bg-blue-50 rounded-lg transition-all duration-200"
                    >
                      <svg
                        class="w-3 h-3 mr-1"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
                        />
                      </svg>
                      Редактировать
                    </button>
                    <button
                      @click="deleteUserById(user.id)"
                      class="flex items-center px-3 py-1 text-xs font-medium text-red-600 hover:text-red-800 hover:bg-red-50 rounded-lg transition-all duration-200"
                    >
                      <svg
                        class="w-3 h-3 mr-1"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
                        />
                      </svg>
                      Удалить
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Пагинация -->
        <div class="bg-white px-6 py-4 border-t border-slate-200">
          <div class="flex items-center justify-between">
            <div class="flex items-center text-sm text-slate-700">
              <span>
                Показано {{ startIndex + 1 }}-{{ Math.min(endIndex, filteredUsers.length) }} из
                {{ filteredUsers.length }} записей
              </span>
            </div>

            <div class="flex items-center space-x-2">
              <button
                @click="previousPage"
                :disabled="currentPage === 1"
                class="relative inline-flex items-center px-3 py-2 border border-slate-300 text-sm font-medium rounded-lg text-slate-700 bg-white hover:bg-slate-50 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <svg class="w-4 h-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M15 19l-7-7 7-7"
                  />
                </svg>
                Назад
              </button>

              <div class="flex items-center space-x-1">
                <button
                  v-for="page in visiblePages"
                  :key="page"
                  @click="goToPage(page)"
                  class="relative inline-flex items-center px-3 py-2 border text-sm font-medium rounded-lg transition-all duration-200"
                  :class="
                    page === currentPage
                      ? 'bg-gradient-to-r from-blue-600 to-indigo-600 text-white border-blue-600 shadow-lg'
                      : 'border-slate-300 text-slate-700 bg-white hover:bg-slate-50'
                  "
                >
                  {{ page }}
                </button>
              </div>

              <button
                @click="nextPage"
                :disabled="currentPage === totalPages"
                class="relative inline-flex items-center px-3 py-2 border border-slate-300 text-sm font-medium rounded-lg text-slate-700 bg-white hover:bg-slate-50 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                Вперед
                <svg class="w-4 h-4 ml-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9 5l7 7-7 7"
                  />
                </svg>
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Модальное окно формы -->
      <UserForm v-if="isFormVisible" :user="editingUser" @close="closeForm" @save="saveUser" />
    </main>
  </div>
</template>

<script>
import axios from 'axios'
import Header from '@/components/Header.vue'
import UserForm from '@/components/admin/UserForm.vue'

export default {
  name: 'AdminUsers',
  components: {
    Header,
    UserForm,
  },
  data() {
    return {
      users: [],
      isFormVisible: false,
      editingUser: null,
      searchQuery: '',
      selectedRole: '',
      currentPage: 1,
      itemsPerPage: 10,
      isLoading: false,
      error: null,
    }
  },
  computed: {
    usersWithFullNames() {
      return this.users.map((user) => ({
        ...user,
        fullName: this.getUserFullName(user),
      }))
    },
    filteredUsers() {
      let filtered = this.users
      if (this.searchQuery) {
        const query = this.searchQuery.toLowerCase()
        filtered = filtered.filter(
          (user) =>
            (user.fullName && user.fullName.toLowerCase().includes(query)) ||
            user.username?.toLowerCase().includes(query),
        )
      }
      if (this.selectedRole) {
        filtered = filtered.filter((user) => user.role === this.selectedRole)
      }
      return filtered
    },
    totalPages() {
      return Math.ceil(this.filteredUsers.length / this.itemsPerPage)
    },
    startIndex() {
      return (this.currentPage - 1) * this.itemsPerPage
    },
    endIndex() {
      return this.startIndex + this.itemsPerPage
    },
    paginatedUsers() {
      return this.filteredUsers.slice(this.startIndex, this.endIndex)
    },
    visiblePages() {
      const pages = []
      const maxVisible = 5
      let start = Math.max(1, this.currentPage - Math.floor(maxVisible / 2))
      let end = Math.min(this.totalPages, start + maxVisible - 1)
      if (end - start + 1 < maxVisible) {
        start = Math.max(1, end - maxVisible + 1)
      }
      for (let i = start; i <= end; i++) {
        pages.push(i)
      }
      return pages
    },
  },
  watch: {
    searchQuery() {
      this.currentPage = 1
    },
    selectedRole() {
      this.currentPage = 1
    },
    itemsPerPage() {
      this.currentPage = 1
    },
  },
  methods: {
    async fetchUsers() {
      this.isLoading = true
      this.error = null
      const apiUrl = import.meta.env.VITE_API_URL
      try {
        const user = JSON.parse(localStorage.getItem('user'))
        if (!user) {
          this.error = 'Пользователь не авторизован'
          return
        }
        const response = await axios.get(`${apiUrl}/admin/users`, {
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
          },
        })
        localStorage.setItem('users', JSON.stringify(response.data))
        this.users = response.data.map((user) => ({
          id: user.id,
          username: user.login,
          fullName: user.fullName || '—', // можно поставить прочерк, если нет имени
          role: user.role,
        }))
      } catch (err) {
        console.error('Ошибка при загрузке пользователей:', err)
        this.error = 'Не удалось загрузить список пользователей'
        this.users = []
      } finally {
        this.isLoading = false
      }
    },
    openCreateModal() {
      this.editingUser = {
        id: null,
        lastName: '',
        firstName: '',
        middleName: '',
        role: 'TEAM_MEMBER',
        username: '',
        password: '',
      }
      this.isFormVisible = true
    },
    editUser(user) {
      this.editingUser = { ...user }
      this.isFormVisible = true
    },
    deleteUser(id) {
      if (confirm('Вы уверены, что хотите удалить этого пользователя?')) {
        this.users = this.users.filter((u) => u.id !== id)
        if (this.paginatedUsers.length === 0 && this.currentPage > 1) {
          this.currentPage--
        }
      }
    },
    async deleteUserById(userId) {
      const apiUrl = import.meta.env.VITE_API_URL

      try {
        const user = JSON.parse(localStorage.getItem('user'))
        if (!user) {
          alert('Пользователь не авторизован')
          return
        }

        // Отправляем DELETE-запрос на сервер
        await axios.delete(`${apiUrl}/admin/users/${userId}`, {
          headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
            Authorization: `Bearer ${user.token}`, // если требуется токен
          },
        })

        // Обновляем список пользователей после успешного удаления
        let users = JSON.parse(localStorage.getItem('users')) || []
        users = users.filter((u) => u.id !== userId)
        localStorage.setItem('users', JSON.stringify(users))

        // Также обновляем список в компоненте
        this.users = users
        alert('Пользователь успешно удален')
      } catch (error) {
        console.error('Ошибка при удалении пользователя:', error)
        alert('Не удалось удалить пользователя')
      }
    },
    async saveUser(userData) {
      await this.fetchUsers() // перезагружаем список
      this.closeForm()
    },
    closeForm() {
      this.isFormVisible = false
      this.editingUser = null
    },
    // getUserFullName(user) {
    //   return [user.lastName, user.firstName, user.middleName].filter(Boolean).join(' ')
    // },

    getUserInitials(user) {
      if (!user.fullName) return '??'

      const parts = user.fullName.split(' ').filter(Boolean)
      if (parts.length === 0) return '??'

      const firstName = parts[1] || ''
      const lastName = parts[0] || ''

      const first = firstName.charAt(0)
      const last = lastName.charAt(0)

      return `${first}${last}`.toUpperCase()
    },
    getRoleClass(role) {
      switch (role) {
        case 'ADMIN':
          return 'bg-purple-100 text-purple-800'
        case 'DEPARTMENT':
          return 'bg-emerald-100 text-emerald-800'
        case 'TEAMLEAD':
          return 'bg-amber-100 text-amber-800'
        case 'TEAM_MEMBER':
          return 'bg-blue-100 text-blue-800'
        default:
          return 'bg-slate-100 text-slate-800'
      }
    },
    getRoleLabel(role) {
      const labels = {
        ADMIN: 'Администратор',
        DEPARTMENT: 'Руководитель отдела',
        TEAMLEAD: 'Тимлид',
        TEAM_MEMBER: 'Сотрудник',
      }
      return labels[role] || role
    },
    goToPage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++
      }
    },
    previousPage() {
      if (this.currentPage > 1) {
        this.currentPage--
      }
    },
  },
  async mounted() {
    await this.fetchUsers()
  },
}
</script>

<style scoped>
/* Анимации для карточек */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fadeIn {
  animation: fadeIn 0.3s ease-out forwards;
}

/* Стили для таблицы */
tbody tr:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* Стили для пагинации */
.pagination-button {
  transition: all 0.2s ease-in-out;
}

.pagination-button:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}
</style>
