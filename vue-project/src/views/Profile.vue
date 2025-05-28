<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-3xl mx-auto">
      <div
        class="bg-white shadow-lg rounded-xl overflow-hidden border border-slate-200 hover:shadow-xl transition-all duration-300"
      >
        <!-- Заголовок -->
        <div class="bg-gradient-to-r from-blue-600 to-purple-600 px-6 py-4 text-white">
          <h2 class="text-2xl font-bold flex items-center">
            <svg class="w-6 h-6 mr-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
              />
            </svg>
            Профиль пользователя
          </h2>
        </div>

        <!-- Контент -->
        <div class="p-6 space-y-6">
          <!-- Информация о пользователе -->
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-sm font-medium text-slate-700 flex items-center">
                <svg
                  class="w-4 h-4 mr-2 text-slate-500"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                  />
                </svg>
                ФИО
              </label>
              <div
                class="px-4 py-3 border border-slate-300 rounded-xl bg-slate-50 text-slate-800 shadow-sm"
              >
                {{ user.fullName || 'Не указано' }}
              </div>
            </div>

            <div class="space-y-2">
              <label class="block text-sm font-medium text-slate-700 flex items-center">
                <svg
                  class="w-4 h-4 mr-2 text-slate-500"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.031 9-11.622 0-1.042-.133-2.052-.382-3.016z"
                  />
                </svg>
                Роль
              </label>
              <div
                class="px-4 py-3 border border-slate-300 rounded-xl bg-slate-50 text-slate-800 capitalize shadow-sm"
              >
                {{ roleLabel }}
              </div>
            </div>
          </div>

          <!-- Смена логина -->
          <div class="pt-6 border-t border-slate-200 space-y-4">
            <h3 class="font-semibold text-slate-800 flex items-center">
              <svg
                class="w-5 h-5 mr-2 text-blue-500"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                />
              </svg>
              Сменить логин
            </h3>
            <div class="relative group">
              <div
                class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none transition-colors duration-200 group-focus-within:text-blue-500"
              >
                <svg
                  class="h-5 w-5 text-slate-400 group-focus-within:text-blue-500"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                  />
                </svg>
              </div>
              <input
                v-model="newLogin"
                type="text"
                placeholder="Новый логин"
                class="w-full pl-12 pr-4 py-3 border border-slate-300 rounded-xl bg-slate-50/50 focus:bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all duration-200 hover:border-slate-400"
              />
            </div>
            <button
              @click="changeLogin"
              class="px-6 py-3 bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white font-medium rounded-xl shadow-lg hover:shadow-xl transition-all duration-200 transform hover:scale-105 flex items-center justify-center"
            >
              <svg class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M5 13l4 4L19 7"
                />
              </svg>
              Сохранить новый логин
            </button>
          </div>

          <!-- Смена пароля -->
          <div class="pt-6 border-t border-slate-200 space-y-4">
            <h3 class="font-semibold text-slate-800 flex items-center">
              <svg
                class="w-5 h-5 mr-2 text-green-500"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                />
              </svg>
              Сменить пароль
            </h3>
            <div class="space-y-4">
              <div class="relative group">
                <div
                  class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none transition-colors duration-200 group-focus-within:text-blue-500"
                >
                  <svg
                    class="h-5 w-5 text-slate-400 group-focus-within:text-blue-500"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                    />
                  </svg>
                </div>
                <input
                  v-model="currentPassword"
                  type="password"
                  placeholder="Текущий пароль"
                  class="w-full pl-12 pr-4 py-3 border border-slate-300 rounded-xl bg-slate-50/50 focus:bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all duration-200 hover:border-slate-400"
                />
              </div>
              <div class="relative group">
                <div
                  class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none transition-colors duration-200 group-focus-within:text-green-500"
                >
                  <svg
                    class="h-5 w-5 text-slate-400 group-focus-within:text-green-500"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                    />
                  </svg>
                </div>
                <input
                  v-model="newPassword"
                  type="password"
                  placeholder="Новый пароль"
                  class="w-full pl-12 pr-4 py-3 border border-slate-300 rounded-xl bg-slate-50/50 focus:bg-white focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500 transition-all duration-200 hover:border-slate-400"
                />
              </div>
            </div>
            <button
              @click="changePassword"
              class="px-6 py-3 bg-gradient-to-r from-green-600 to-emerald-600 hover:from-green-700 hover:to-emerald-700 text-white font-medium rounded-xl shadow-lg hover:shadow-xl transition-all duration-200 transform hover:scale-105 flex items-center justify-center"
            >
              <svg class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                />
              </svg>
              Изменить пароль
            </button>
          </div>

          <!-- Сообщение об ошибке или успехе -->
          <div
            v-if="errorMessage"
            class="mt-4 p-4 rounded-xl text-sm flex items-center"
            :class="messageIsSuccess ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'"
          >
            <svg
              class="w-5 h-5 mr-2"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              :class="messageIsSuccess ? 'text-green-500' : 'text-red-500'"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                :d="
                  messageIsSuccess
                    ? 'M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z'
                    : 'M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z'
                "
              />
            </svg>
            {{ errorMessage }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  data() {
    return {
      newLogin: '',
      currentPassword: '',
      newPassword: '',
      errorMessage: '',
      messageIsSuccess: false,
    }
  },
  computed: {
    // Получаем данные пользователя из localStorage
    user() {
      const storedUser = localStorage.getItem('user')
      return storedUser ? JSON.parse(storedUser) : {}
    },
    roleLabel() {
      const roles = {
        admin: 'Администратор',
        department: 'Руководитель отдела',
        team_lead: 'Тимлид',
        teammember: 'Сотрудник',
      }
      return roles[this.user.role] || this.user.role
    },
  },
  methods: {
    changeLogin() {
      if (!this.newLogin.trim()) {
        this.errorMessage = 'Введите корректный логин'
        this.messageIsSuccess = false
        return
      }

      // Обновляем логин в объекте пользователя
      const updatedUser = { ...this.user, username: this.newLogin }

      // Сохраняем обратно
      localStorage.setItem('user', JSON.stringify(updatedUser))
      this.errorMessage = 'Логин успешно изменён'
      this.messageIsSuccess = true

      // Очистка поля
      this.newLogin = ''
    },
    changePassword() {
      if (!this.currentPassword || !this.newPassword) {
        this.errorMessage = 'Заполните все поля'
        this.messageIsSuccess = false
        return
      }

      if (this.currentPassword !== this.user.password) {
        this.errorMessage = 'Текущий пароль неверен'
        this.messageIsSuccess = false
        return
      }

      // Обновляем пароль
      const updatedUser = { ...this.user, password: this.newPassword }
      localStorage.setItem('user', JSON.stringify(updatedUser))
      this.errorMessage = 'Пароль успешно изменён'
      this.messageIsSuccess = true

      // Очистка полей
      this.currentPassword = ''
      this.newPassword = ''
    },
  },
}
</script>

<style scoped>
/* Анимации для элементов */
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

/* Улучшенные стили для полей ввода */
input:focus {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.15);
}

/* Стили для кнопок */
button:active {
  transform: scale(0.98);
}
</style>
