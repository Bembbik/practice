<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
    <main class="max-w-4xl mx-auto py-16 px-4 sm:px-6 lg:px-8">
      <div class="text-center">
        <!-- Анимированная иконка 404 -->
        <div class="mb-8 flex justify-center">
          <div class="relative">
            <div
              class="w-32 h-32 bg-gradient-to-br from-blue-500 to-purple-600 rounded-3xl flex items-center justify-center shadow-2xl animate-bounce"
            >
              <svg
                class="w-16 h-16 text-white"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9.172 16.172a4 4 0 015.656 0M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                />
              </svg>
            </div>
            <!-- Декоративные элементы -->
            <div
              class="absolute -top-2 -right-2 w-6 h-6 bg-gradient-to-br from-amber-400 to-orange-500 rounded-full animate-pulse"
            ></div>
            <div
              class="absolute -bottom-2 -left-2 w-4 h-4 bg-gradient-to-br from-emerald-400 to-green-500 rounded-full animate-pulse"
              style="animation-delay: 0.5s"
            ></div>
          </div>
        </div>

        <!-- Основной контент -->
        <div
          class="bg-white rounded-2xl shadow-xl p-8 sm:p-12 border border-slate-200 max-w-2xl mx-auto"
        >
          <div class="mb-6">
            <h1
              class="text-6xl sm:text-8xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600 mb-4"
            >
              404
            </h1>
            <h2 class="text-2xl sm:text-3xl font-bold text-slate-900 mb-4">Страница не найдена</h2>
            <p class="text-lg text-slate-600 mb-8 leading-relaxed">
              К сожалению, запрашиваемая страница не существует или была перемещена. Проверьте
              правильность адреса или вернитесь на главную страницу.
            </p>
          </div>

          <!-- Действия -->
          <div class="flex flex-col sm:flex-row gap-4 justify-center items-center">
            <button
              @click="goHome"
              class="flex items-center space-x-2 px-6 py-3 bg-gradient-to-r from-blue-600 to-indigo-600 text-white font-semibold rounded-xl hover:from-blue-700 hover:to-indigo-700 transition-all duration-300 shadow-lg hover:shadow-xl transform hover:scale-105"
            >
              <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"
                />
              </svg>
              <span>На главную</span>
            </button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import Header from '@/components/Header.vue'

export default {
  name: 'NotFound',
  components: {
    Header,
  },
  methods: {
    goHome() {
      // Определяем домашнюю страницу в зависимости от роли пользователя
      const user = this.$store?.getters?.user || JSON.parse(localStorage.getItem('user'))
      const userRole = user?.role || 'team'

      const homeRoutes = {
        admin: '/admin',
        department: '/department',
        team_lead: '/team-lead',
        team: '/team/tickets',
      }

      const homePath = homeRoutes[userRole] || '/team/tickets'
      this.$router.push(homePath)
    },
    goBack() {
      // Если есть история, возвращаемся назад, иначе идем на главную
      if (window.history.length > 1) {
        this.$router.go(-1)
      } else {
        this.goHome()
      }
    },
  },
}
</script>

<style scoped>
/* Дополнительные анимации */
@keyframes bounce {
  0%,
  20%,
  53%,
  80%,
  100% {
    transform: translate3d(0, 0, 0);
  }
  40%,
  43% {
    transform: translate3d(0, -8px, 0);
  }
  70% {
    transform: translate3d(0, -4px, 0);
  }
  90% {
    transform: translate3d(0, -2px, 0);
  }
}

.animate-bounce {
  animation: bounce 2s infinite;
}

/* Плавное появление элементов */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.bg-white {
  animation: fadeInUp 0.6s ease-out;
}

/* Градиентный текст для лучшей поддержки */
.bg-clip-text {
  -webkit-background-clip: text;
  background-clip: text;
}
</style>
