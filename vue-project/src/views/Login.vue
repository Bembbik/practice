<template>
  <div
    class="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 px-4 relative overflow-hidden"
  >
    <!-- Декоративные элементы фона -->
    <div class="absolute inset-0 overflow-hidden">
      <div
        class="absolute -top-40 -right-40 w-80 h-80 bg-gradient-to-br from-blue-500/20 to-purple-600/20 rounded-full blur-3xl animate-pulse"
      ></div>
      <div
        class="absolute -bottom-40 -left-40 w-80 h-80 bg-gradient-to-br from-emerald-500/20 to-cyan-500/20 rounded-full blur-3xl animate-pulse"
        style="animation-delay: 1s"
      ></div>
      <div
        class="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-96 h-96 bg-gradient-to-br from-indigo-500/10 to-purple-500/10 rounded-full blur-3xl animate-pulse"
        style="animation-delay: 2s"
      ></div>
    </div>
    <!-- Основная форма -->
    <div class="relative z-10 max-w-md w-full">
      <!-- Карточка входа -->
      <div
        class="bg-white/95 backdrop-blur-xl rounded-2xl shadow-2xl border border-white/20 p-8 transform transition-all duration-500 hover:scale-105 hover:shadow-3xl"
      >
        <!-- Заголовок с иконкой -->
        <div class="text-center mb-8">
          <div
            class="w-16 h-16 bg-gradient-to-br from-blue-500 to-purple-600 rounded-2xl flex items-center justify-center mx-auto mb-4 shadow-lg transform transition-transform duration-300 hover:rotate-6"
          >
            <svg class="w-8 h-8 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z"
              />
            </svg>
          </div>
          <h2
            class="text-3xl font-bold bg-gradient-to-r from-slate-800 to-slate-600 bg-clip-text text-transparent mb-2"
          >
            Добро пожаловать
          </h2>
          <p class="text-slate-600 text-sm">Войдите в систему управления тикетами</p>
        </div>
        <!-- Форма -->
        <form @submit.prevent="login" class="space-y-6">
          <!-- Поле логина -->
          <div class="space-y-2">
            <label class="block text-sm font-semibold text-slate-700 flex items-center">
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
              Логин
            </label>
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
                v-model="credentials.login"
                type="text"
                placeholder="Введите ваш логин"
                required
                :disabled="isLoading"
                class="w-full pl-12 pr-4 py-3 border border-slate-300 rounded-xl bg-slate-50/50 focus:bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all duration-200 hover:border-slate-400 disabled:opacity-50 disabled:cursor-not-allowed text-slate-900 placeholder-slate-500"
              />
            </div>
          </div>
          <!-- Поле пароля -->
          <div class="space-y-2">
            <label class="block text-sm font-semibold text-slate-700 flex items-center">
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
                  d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                />
              </svg>
              Пароль
            </label>
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
                v-model="credentials.password"
                :type="showPassword ? 'text' : 'password'"
                placeholder="Введите ваш пароль"
                required
                :disabled="isLoading"
                class="w-full pl-12 pr-12 py-3 border border-slate-300 rounded-xl bg-slate-50/50 focus:bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all duration-200 hover:border-slate-400 disabled:opacity-50 disabled:cursor-not-allowed text-slate-900 placeholder-slate-500"
              />
              <button
                type="button"
                @click="togglePasswordVisibility"
                class="absolute inset-y-0 right-0 pr-4 flex items-center text-slate-400 hover:text-slate-600 transition-colors duration-200"
                :disabled="isLoading"
              >
                <svg
                  v-if="!showPassword"
                  class="h-5 w-5"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
                  />
                </svg>
                <svg v-else class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L3 3m6.878 6.878L21 21"
                  />
                </svg>
              </button>
            </div>
          </div>
          <!-- Кнопка входа -->
          <button
            type="submit"
            :disabled="isLoading"
            class="w-full py-3 px-4 cursor-pointer bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700 text-white font-semibold rounded-xl transition-all duration-300 transform hover:scale-105 hover:shadow-xl focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none flex items-center justify-center space-x-2"
          >
            <svg
              v-if="isLoading"
              class="animate-spin h-5 w-5 text-white"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
            >
              <circle
                class="opacity-25"
                cx="12"
                cy="12"
                r="10"
                stroke="currentColor"
                stroke-width="4"
              ></circle>
              <path
                class="opacity-75"
                fill="currentColor"
                d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
              ></path>
            </svg>
            <svg v-else class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1"
              />
            </svg>
            <span>{{ isLoading ? 'Вход...' : 'Войти в систему' }}</span>
          </button>
        </form>
        <!-- Сообщение об ошибке -->
        <transition
          enter-active-class="transition ease-out duration-300"
          enter-from-class="transform opacity-0 scale-95"
          enter-to-class="transform opacity-100 scale-100"
          leave-active-class="transition ease-in duration-200"
          leave-from-class="transform opacity-100 scale-100"
          leave-to-class="transform opacity-0 scale-95"
        >
          <div
            v-if="error"
            class="mt-6 p-4 bg-gradient-to-r from-red-50 to-pink-50 border border-red-200 rounded-xl flex items-center space-x-3"
          >
            <div class="flex-shrink-0">
              <svg
                class="w-5 h-5 text-red-500"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                />
              </svg>
            </div>
            <p class="text-red-700 text-sm font-medium">{{ error }}</p>
          </div>
        </transition>
      </div>
      <!-- Дополнительная информация -->
      <div class="mt-6 text-center">
        <p class="text-slate-400 text-sm">
          Система управления тикетами
          <span class="mx-2">•</span>
          <span class="text-slate-300">v1.0</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data() {
    return {
      credentials: {
        login: '',
        password: '',
      },
      error: '',
      isLoading: false,
      showPassword: false,
    }
  },
  methods: {
    async login() {
      if (this.isLoading) return
      this.error = ''
      this.isLoading = true
      try {
        const response = await axios.post(
          `${import.meta.env.VITE_API_URL}/auth/login`,
          {
            login: this.credentials.login,
            password: this.credentials.password,
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Accept: '*/*',
            },
          },
        )
        const user = response.data
        // Сохранение пользователя (в localStorage или Vuex)
        localStorage.setItem('user', JSON.stringify(user))
        // Перенаправление
        this.$router.push(user.redirectUrl || '/unauthorized')
      } catch (err) {
        this.error = err.response?.data?.message || 'Ошибка при входе'
        console.error('Ошибка входа:', err)
      } finally {
        this.isLoading = false
      }
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword
    },
    fillCredentials(login, password) {
      this.credentials.login = login
      this.credentials.password = password
      this.error = ''
    },
  },
  mounted() {
    this.$watch(
      'credentials',
      () => {
        if (this.error) {
          this.error = ''
        }
      },
      { deep: true },
    )
  },
}
</script>
