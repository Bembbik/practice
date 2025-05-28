<template>
  <div
    class="fixed inset-0 z-50 flex items-center justify-center p-4 backdrop-blur-md bg-slate-900/40 animate-fadeIn"
    @click.self="$emit('close')"
  >
    <div
      class="bg-white w-full max-w-2xl rounded-3xl shadow-2xl border border-slate-200/50 animate-slideUp overflow-hidden"
    >
      <!-- Заголовок -->
      <div
        class="px-8 py-6 border-b border-slate-200/50 bg-gradient-to-r from-blue-50 via-indigo-50 to-purple-50 relative overflow-hidden"
      >
        <!-- Декоративные элементы -->
        <div
          class="absolute top-0 right-0 w-32 h-32 bg-gradient-to-br from-blue-200/30 to-purple-200/30 rounded-full -translate-y-16 translate-x-16"
        ></div>
        <div
          class="absolute bottom-0 left-0 w-24 h-24 bg-gradient-to-tr from-indigo-200/30 to-blue-200/30 rounded-full translate-y-12 -translate-x-12"
        ></div>

        <div class="relative z-10">
          <h2 class="text-2xl font-bold text-slate-900 flex items-center">
            <div
              class="w-10 h-10 bg-gradient-to-br from-blue-500 via-indigo-500 to-purple-500 rounded-xl flex items-center justify-center mr-4 shadow-lg shadow-blue-500/25"
            >
              <svg
                v-if="editing"
                class="w-5 h-5 text-white"
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
              <svg
                v-else
                class="w-5 h-5 text-white"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z"
                />
              </svg>
            </div>
            <div>
              <span class="block">{{
                editing ? 'Редактировать пользователя' : 'Новый пользователь'
              }}</span>
              <span class="text-sm font-normal text-slate-600 block mt-1">
                {{
                  editing
                    ? 'Обновите информацию о пользователе'
                    : 'Заполните данные для создания нового пользователя'
                }}
              </span>
            </div>
          </h2>
        </div>

        <button
          @click="$emit('close')"
          class="absolute top-6 right-6 p-2 hover:bg-white/50 rounded-xl transition-all duration-200 group z-20"
        >
          <svg
            class="w-5 h-5 text-slate-400 group-hover:text-slate-600"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M6 18L18 6M6 6l12 12"
            />
          </svg>
        </button>
      </div>

      <!-- Форма -->
      <form @submit.prevent="handleSubmit" class="p-8 space-y-8 max-h-[70vh] overflow-y-auto">
        <!-- Превью полного имени -->
        <div
          v-if="form.fullName"
          class="bg-gradient-to-r from-blue-50 to-indigo-50 border border-blue-200/50 px-6 py-4 rounded-2xl text-sm relative overflow-hidden"
        >
          <div
            class="absolute top-0 right-0 w-16 h-16 bg-blue-200/20 rounded-full -translate-y-8 translate-x-8"
          ></div>
          <div class="relative z-10 flex items-center">
            <svg
              class="w-5 h-5 text-blue-600 mr-3"
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
            <div>
              <span class="text-blue-700 font-medium">Полное имя:</span>
              <strong class="text-blue-800 ml-2">{{ form.fullName }}</strong>
            </div>
          </div>
        </div>

        <!-- Персональные данные -->
        <div class="space-y-4">
          <h3 class="text-lg font-semibold text-slate-800 flex items-center">
            <div
              class="w-6 h-6 bg-gradient-to-br from-emerald-500 to-teal-500 rounded-lg flex items-center justify-center mr-3"
            >
              <svg class="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                />
              </svg>
            </div>
            Персональные данные
          </h3>

          <div class="form-group">
            <label class="form-label"> <span class="text-red-500">*</span> Полное имя </label>
            <input
              v-model="form.fullName"
              type="text"
              required
              class="form-input"
              placeholder="Иванов Иван Иванович"
            />
          </div>
        </div>

        <!-- Роль и права доступа -->
        <div class="space-y-4">
          <h3 class="text-lg font-semibold text-slate-800 flex items-center">
            <div
              class="w-6 h-6 bg-gradient-to-br from-purple-500 to-pink-500 rounded-lg flex items-center justify-center mr-3"
            >
              <svg class="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"
                />
              </svg>
            </div>
            Роль и доступ
          </h3>
          <div class="form-group">
            <label class="form-label"> <span class="text-red-500">*</span> Роль в системе </label>
            <select v-model="role" required class="form-select">
              <option value="ADMIN">👑 Администратор</option>
              <option value="DEPARTMENT">🏢 Руководитель отдела</option>
              <option value="TEAMLEAD">👥 Тимлид</option>
              <option value="TEAM_MEMBER">👤 Сотрудник</option>
            </select>
          </div>

          <!-- Отделы (только если роль — руководитель) -->
          <div
            v-if="role === 'DEPARTMENT'"
            class="space-y-4 p-6 bg-gradient-to-r from-amber-50 to-orange-50 border border-amber-200/50 rounded-2xl"
          >
            <div class="flex items-center mb-4">
              <div
                class="w-6 h-6 bg-gradient-to-br from-amber-500 to-orange-500 rounded-lg flex items-center justify-center mr-3"
              >
                <svg
                  class="w-3 h-3 text-white"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"
                  />
                </svg>
              </div>
              <h4 class="font-semibold text-amber-800">Управление отделом</h4>
            </div>

            <div class="form-group">
              <label class="form-label text-amber-700">Выберите отдел</label>
              <select v-model="form.departmentId" class="form-select">
                <option value="">-- Выберите существующий отдел --</option>
                <option v-for="dept in tempDepartments" :key="dept.id" :value="dept.id">
                  🏢 {{ dept.name }}
                </option>
              </select>
            </div>

            <div class="border-t border-amber-200 pt-4">
              <label class="form-label text-amber-700 mb-3">Или создайте новый отдел</label>
              <div class="flex gap-3">
                <input
                  v-model="newDepartmentName"
                  placeholder="Название нового отдела"
                  class="form-input flex-1"
                />
                <button
                  @click="addNewDepartment"
                  type="button"
                  class="btn-accent whitespace-nowrap"
                  :disabled="!newDepartmentName.trim()"
                >
                  <svg class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M12 4v16m8-8H4"
                    />
                  </svg>
                  Создать
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Учетные данные -->
        <div class="space-y-4">
          <h3 class="text-lg font-semibold text-slate-800 flex items-center">
            <div
              class="w-6 h-6 bg-gradient-to-br from-cyan-500 to-blue-500 rounded-lg flex items-center justify-center mr-3"
            >
              <svg class="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z"
                />
              </svg>
            </div>
            Учетные данные
          </h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="form-group">
              <label class="form-label"> <span class="text-red-500">*</span> Логин </label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                  <svg
                    class="w-4 h-4 text-slate-400"
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
                  v-model="form.username"
                  type="text"
                  required
                  class="form-input pl-12"
                  placeholder="login123"
                />
              </div>
            </div>
            <div class="form-group">
              <label class="form-label">
                <span v-if="!editing" class="text-red-500">*</span> Пароль
              </label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                  <svg
                    class="w-4 h-4 text-slate-400"
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
                  v-model="form.password"
                  :type="showPassword ? 'text' : 'password'"
                  :required="!editing"
                  class="form-input pl-12 pr-12"
                  :placeholder="
                    editing ? 'Новый пароль (оставьте пустым, если не меняете)' : 'Введите пароль'
                  "
                />
                <button
                  @click="togglePassword"
                  type="button"
                  class="absolute inset-y-0 right-0 pr-4 flex items-center text-slate-400 hover:text-slate-600 transition-colors"
                >
                  <svg
                    v-if="showPassword"
                    class="w-4 h-4"
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
                  <svg v-else class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878a3 3 0 104.244-4.244L15.5 7M9.878 9.878L3 3m6.878 6.878L21 21"
                    />
                  </svg>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Кнопки -->
        <div class="flex justify-end space-x-4 pt-6 border-t border-slate-200">
          <button @click="$emit('close')" type="button" class="btn-secondary">
            <svg class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
            Отмена
          </button>
          <button type="submit" class="btn-primary" :disabled="isLoading">
            <svg
              v-if="editing"
              class="w-4 h-4 mr-2"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5 13l4 4L19 7"
              />
            </svg>
            <svg v-else class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 4v16m8-8H4"
              />
            </svg>
            <span v-if="editing">{{ isLoading ? 'Сохранение...' : 'Сохранить изменения' }}</span>
            <span v-else>{{ isLoading ? 'Создание...' : 'Создать пользователя' }}</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  props: {
    user: {
      type: Object,
      default: () => ({
        id: null,
        fullName: '',
        role: 'TEAM_MEMBER',
        username: '',
        password: '',
        departmentId: '',
      }),
    },
  },
  data() {
    return {
      departments: [],
      showPassword: false,
      newDepartmentName: '',
      tempDepartments: [],
      isLoading: false,
      error: '',
      role: this.user.role || 'TEAM_MEMBER',
      form: {
        fullName: this.user.fullName || '',
        username: this.user.username || this.user.login || '',
        password: '',
        departmentId: this.user.departmentId || '',
      },
    }
  },
  async mounted() {
    console.log('🔧 UserForm mounted, user prop:', this.user)

    try {
      const response = await axios.get(`${import.meta.env.VITE_API_URL}/admin/departments`)
      this.departments = response.data
      this.tempDepartments = [...this.departments]
    } catch (error) {
      console.error('Ошибка загрузки отделов:', error)
      alert('Не удалось загрузить список отделов')
    }
  },
  computed: {
    editing() {
      return !!this.user.id
    },
  },
  methods: {
    togglePassword() {
      this.showPassword = !this.showPassword
    },
    addNewDepartment() {
      this.form.departmentId = null
    },
    async handleSubmit() {
      if (this.isLoading) return

      console.log('🚀 Отправка формы, текущие данные формы:', {
        fullName: this.form.fullName,
        username: this.form.username,
        role: this.role,
        editing: this.editing,
      })

      // Валидация
      if (!this.form.fullName.trim()) {
        alert('Пожалуйста, введите полное имя')
        return
      }

      if (!this.form.username.trim()) {
        alert('Пожалуйста, введите логин')
        return
      }

      if (!this.editing && !this.form.password.trim()) {
        alert('Пожалуйста, введите пароль')
        return
      }

      this.error = ''
      this.isLoading = true

      // Убеждаемся, что отправляем актуальные данные из формы
      const payload = {
        login: this.form.username.trim(),
        fullName: this.form.fullName.trim(),
        role: this.role,
      }

      // Добавляем пароль только если он указан
      if (this.form.password.trim()) {
        payload.password = this.form.password
      }

      // Логика для роли DEPARTMENT
      if (this.role === 'DEPARTMENT') {
        if (this.newDepartmentName.trim()) {
          // Создание нового отдела
          payload.newDepartmentName = this.newDepartmentName.trim()
        } else if (this.form.departmentId) {
          // Выбран существующий отдел
          payload.departmentId = this.form.departmentId
        }
      }

      console.log('📤 Отправляемые данные:', payload)

      try {
        const url = this.editing
          ? `${import.meta.env.VITE_API_URL}/admin/users/${this.user.id}`
          : `${import.meta.env.VITE_API_URL}/admin/users/create`

        const response = await axios({
          method: this.editing ? 'put' : 'post',
          url,
          data: payload,
          headers: {
            'Content-Type': 'application/json',
            Accept: '*/*',
          },
        })

        console.log('✅ Успех:', response.data)

        this.$emit('save', response.data)
        this.$emit('close')
      } catch (error) {
        console.error('❌ Ошибка:', error)
        this.error = error.response?.data?.message || 'Не удалось сохранить пользователя'
        alert(this.error)
      } finally {
        this.isLoading = false
      }
    },
  },
  // Добавляем watcher для отслеживания изменений в форме
  watch: {
    'form.username'(newVal, oldVal) {
      console.log('👀 Логин изменился:', { old: oldVal, new: newVal })
    },
  },
}
</script>

<style scoped>
/* Форма группы */
.form-group {
  margin-bottom: 1rem;
}

.form-label {
  display: block;
  font-size: 0.875rem; /* text-sm */
  font-weight: 600; /* font-semibold */
  color: #334155; /* text-slate-700 */
  margin-bottom: 0.75rem; /* mb-3 */
}

/* Поля ввода */
.form-input {
  width: 100%;
  border: 1px solid #cbd5e1; /* border-slate-300 */
  padding: 0.875rem 1rem; /* py-3.5 px-4 */
  font-size: 0.875rem; /* text-sm */
  border-radius: 1rem; /* rounded-2xl */
  background-color: rgba(255, 255, 255, 0.5); /* bg-white/50 */
  backdrop-filter: blur(0.25rem); /* backdrop-blur-sm */
  transition: all 0.2s ease-in-out;
  outline: none;
  appearance: none;
  box-shadow: none;
}

.form-input:focus {
  border-color: #3b82f6; /* focus:border-blue-500 */
  box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.1); /* focus:ring-4 focus:ring-blue-500/20 */
  background-color: #ffffff; /* focus:bg-white */
}

.form-input:hover {
  border-color: #94a3b8; /* hover:border-slate-400 */
  background-color: rgba(255, 255, 255, 0.8); /* hover:bg-white/80 */
}

.form-input::placeholder {
  color: #94a3b8; /* placeholder-slate-500 */
}

/* Селект */
.form-select {
  width: 100%;
  border: 1px solid #cbd5e1; /* border-slate-300 */
  padding: 0.875rem 2.5rem 0.875rem 1rem; /* py-3.5 px-4 + padding-right для иконки */
  font-size: 0.875rem; /* text-sm */
  border-radius: 1rem; /* rounded-2xl */
  background-color: rgba(255, 255, 255, 0.5); /* bg-white/50 */
  backdrop-filter: blur(0.25rem); /* backdrop-blur-sm */
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  background-image: url("data:image/svg+xml;charset=US-ASCII,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 4 5'><path fill='%23666' d='M2 0L0 2h4zm0 5L0 3h4z'/></svg>");
  background-repeat: no-repeat;
  background-position: right 1rem center;
  background-size: 12px;
  transition: all 0.2s ease-in-out;
  outline: none;
}

.form-select:focus {
  border-color: #3b82f6; /* focus:border-blue-500 */
  box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.1); /* focus:ring-4 focus:ring-blue-500/20 */
  background-color: #ffffff; /* focus:bg-white */
}

.form-select:hover {
  border-color: #94a3b8; /* hover:border-slate-400 */
  background-color: rgba(255, 255, 255, 0.8); /* hover:bg-white/80 */
}

/* Кнопки */
.btn-primary {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.875rem 1.5rem; /* py-3.5 px-6 */
  font-size: 0.875rem; /* text-sm */
  font-weight: 600; /* font-semibold */
  border-radius: 1rem; /* rounded-2xl */
  background: linear-gradient(
    to right,
    #2563eb,
    #4f46e5,
    #7c3aed
  ); /* from-blue-600 via-indigo-600 to-purple-600 */
  color: #ffffff;
  box-shadow: 0 4px 14px rgba(59, 130, 246, 0.25); /* shadow-lg shadow-blue-500/25 */
  transition: all 0.2s ease-in-out;
  text-decoration: none;
}

.btn-primary:hover {
  box-shadow: 0 8px 20px rgba(59, 130, 246, 0.3); /* hover:shadow-xl hover:shadow-blue-500/30 */
  transform: scale(1.02) translateY(-2px); /* hover:scale-[1.02] hover:-translate-y-0.5 */
  background: linear-gradient(
    to right,
    #1d4ed8,
    #3730a3,
    #6b21a8
  ); /* hover:from-blue-700 hover:via-indigo-700 hover:to-purple-700 */
}

.btn-primary:active {
  transform: scale(0.98); /* active:scale-[0.98] */
}

.btn-secondary {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.875rem 1.5rem; /* py-3.5 px-6 */
  font-size: 0.875rem; /* text-sm */
  font-weight: 600; /* font-semibold */
  border-radius: 1rem; /* rounded-2xl */
  background-color: #f1f5f9; /* bg-slate-100 */
  color: #334155; /* text-slate-700 */
  border: 1px solid #e2e8f0; /* border-slate-200 */
  transition: all 0.2s ease-in-out;
}

.btn-secondary:hover {
  background-color: #e2e8f0; /* hover:bg-slate-200 */
  border-color: #cbd5e1; /* hover:border-slate-300 */
}

.btn-secondary:active {
  transform: scale(0.98); /* active:scale-[0.98] */
}

.btn-accent {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.625rem 1rem; /* py-2.5 px-4 */
  font-size: 0.875rem; /* text-sm */
  font-weight: 600; /* font-semibold */
  border-radius: 0.75rem; /* rounded-xl */
  background: linear-gradient(to right, #d97706, #ea580c); /* from-amber-500 to-orange-500 */
  color: #ffffff;
  box-shadow: 0 4px 10px rgba(251, 146, 60, 0.25); /* shadow-md shadow-amber-500/25 */
  transition: all 0.2s ease-in-out;
}

.btn-accent:hover {
  box-shadow: 0 6px 16px rgba(251, 146, 60, 0.3); /* hover:shadow-lg hover:shadow-amber-500/30 */
  background: linear-gradient(
    to right,
    #b45309,
    #c24914
  ); /* hover:from-amber-600 hover:to-orange-600 */
  transform: scale(1.02); /* hover:scale-[1.02] */
}

.btn-accent:active {
  transform: scale(0.98); /* active:scale-[0.98] */
}

.btn-accent:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

/* Анимации */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.animate-fadeIn {
  animation: fadeIn 0.4s ease-out forwards;
}

.animate-slideUp {
  animation: slideUp 0.4s cubic-bezier(0.34, 1.56, 0.64, 1) forwards;
}

/* Скроллбар */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f5f9;
  border-radius: 10px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: linear-gradient(to bottom, #cbd5e1, #94a3b8);
  border-radius: 10px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(to bottom, #94a3b8, #64748b);
}
</style>
