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
                v-if="currentTeam"
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
                  d="M12 4v16m8-8H4"
                />
              </svg>
            </div>
            <div>
              <span class="block">{{
                currentTeam ? 'Редактировать команду' : 'Новая команда'
              }}</span>
              <span class="text-sm font-normal text-slate-600 block mt-1">
                {{
                  currentTeam
                    ? 'Обновите информацию о команде'
                    : 'Создайте новую команду и назначьте тимлида'
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

      <!-- Основная форма -->
      <div class="p-8 max-h-[70vh] overflow-y-auto">
        <!-- Основные данные команды -->
        <div class="space-y-6">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- Название команды -->
            <div class="form-group">
              <label class="form-label flex items-center">
                <svg
                  class="w-4 h-4 mr-2 text-blue-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 515.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 919.288 0M15 7a3 3 0 11-6 0 3 3 0 616 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                  />
                </svg>
                Название команды
              </label>
              <input
                v-model="form.name"
                type="text"
                placeholder="Например: Команда разработки"
                class="form-input"
                required
              />
            </div>

            <!-- Выбор тимлида -->
            <div class="form-group">
              <label class="form-label flex items-center">
                <svg
                  class="w-4 h-4 mr-2 text-indigo-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M5 3v4M3 5h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z"
                  />
                </svg>
                Тимлид команды
              </label>
              <select v-model="form.teamLeadId" class="form-select" required>
                <option value="">-- Выберите тимлида --</option>
                <option v-for="user in teamLeadUsers" :key="user.id" :value="user.id">
                  {{ user.fullName }} )
                </option>
              </select>
            </div>
          </div>

          <!-- Превью выбранного тимлида -->
          <div
            v-if="form.teamLeadId"
            class="bg-gradient-to-r from-indigo-50 to-purple-50 border border-indigo-200/50 px-6 py-4 rounded-2xl"
          >
            <div class="flex items-center">
              <div
                class="w-10 h-10 rounded-full bg-gradient-to-br from-indigo-500 to-purple-600 flex items-center justify-center text-white font-bold shadow-md mr-4"
              >
                {{ getSelectedTeamLead?.name?.charAt(0) || '?' }}
              </div>
              <div>
                <div class="font-medium text-indigo-900">{{ getSelectedTeamLead?.fullName }}</div>
                <span
                  class="inline-flex items-center px-2 py-1 mt-1 text-xs font-medium bg-indigo-100 text-indigo-800 rounded-full"
                >
                  Тимлид
                </span>
              </div>
            </div>
          </div>

          <!-- Кнопки сохранения основных данных -->
          <div class="flex justify-end space-x-3 pt-4 border-t border-slate-200">
            <button type="button" @click="$emit('close')" class="btn-secondary">Отмена</button>
            <button
              @click="saveBasicInfo"
              :disabled="isLoading || !form.name.trim() || !form.teamLeadId"
              class="btn-primary"
            >
              <svg
                v-if="!isLoading"
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
              <svg v-else class="w-4 h-4 mr-2 animate-spin" fill="none" viewBox="0 0 24 24">
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
              {{
                isLoading
                  ? 'Сохранение...'
                  : currentTeam
                    ? 'Сохранить изменения'
                    : 'Создать команду'
              }}
            </button>
          </div>
        </div>

        <!-- Управление участниками (показывается только после сохранения основных данных) -->
        <div v-if="showMembersSection" class="mt-8 pt-8 border-t border-slate-200">
          <div class="space-y-6">
            <h3 class="text-lg font-semibold text-slate-800 flex items-center">
              <div
                class="w-6 h-6 bg-gradient-to-br from-purple-500 to-fuchsia-500 rounded-lg flex items-center justify-center mr-3"
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
                    d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"
                  />
                </svg>
              </div>
              Участники команды
            </h3>

            <!-- Добавление нового участника -->
            <div class="bg-slate-50 rounded-xl p-4">
              <div class="flex items-center space-x-3">
                <div class="flex-1">
                  <select v-model="selectedMemberId" class="form-select">
                    <option value="">-- Выберите участника для добавления --</option>
                    <option v-for="user in availableTeamMembers" :key="user.id" :value="user.id">
                      {{ user.fullName }}
                    </option>
                  </select>
                </div>
                <button
                  @click="addTeamMember"
                  :disabled="!selectedMemberId || isAddingMember"
                  class="btn-primary px-4 py-2"
                >
                  <svg
                    v-if="!isAddingMember"
                    class="w-4 h-4 mr-2"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M12 4v16m8-8H4"
                    />
                  </svg>
                  <svg v-else class="w-4 h-4 mr-2 animate-spin" fill="none" viewBox="0 0 24 24">
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
                  {{ isAddingMember ? 'Добавление...' : 'Добавить' }}
                </button>
              </div>
            </div>

            <!-- Список текущих участников -->
            <div class="space-y-3">
              <h4 class="text-sm font-medium text-slate-700">
                Текущие участники ({{ currentMembers.length }})
              </h4>

              <div
                v-if="currentMembers.length === 0"
                class="text-center py-6 bg-slate-50 rounded-xl border border-dashed border-slate-300"
              >
                <svg
                  class="w-10 h-10 mx-auto text-slate-400"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="1.5"
                    d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"
                  />
                </svg>
                <p class="mt-2 text-sm text-slate-500">Нет участников в команде</p>
                <p class="text-xs text-slate-400 mt-1">Добавьте участников используя форму выше</p>
              </div>

              <div v-else class="space-y-2">
                <div
                  v-for="member in currentMembers"
                  :key="member.id"
                  class="p-3 rounded-xl border border-purple-200 bg-gradient-to-r from-purple-50 to-fuchsia-50 flex items-center justify-between group hover:shadow-md transition-all duration-200"
                >
                  <div class="flex items-center">
                    <div
                      class="w-8 h-8 rounded-full bg-gradient-to-br from-purple-500 to-fuchsia-600 flex items-center justify-center text-white font-bold shadow-md mr-3"
                    >
                      {{ member.name?.charAt(0) || '?' }}
                    </div>
                    <div>
                      <div class="font-medium text-purple-900">{{ member.name }}</div>
                      <div class="text-xs text-purple-600">{{ member.email }}</div>
                    </div>
                  </div>
                  <button
                    @click="removeMember(member.id)"
                    :disabled="isRemovingMember === member.id"
                    class="p-1.5 rounded-lg bg-white/50 text-purple-500 hover:bg-white hover:text-red-500 hover:shadow-sm transition-all duration-200 disabled:opacity-50"
                  >
                    <svg
                      v-if="isRemovingMember !== member.id"
                      class="w-4 h-4"
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
                    <svg v-else class="w-4 h-4 animate-spin" fill="none" viewBox="0 0 24 24">
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
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  props: {
    team: {
      type: Object,
      default: () => null,
    },
    allTeams: {
      type: Array,
      required: true,
    },
    allUsers: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      isLoading: false,
      isAddingMember: false,
      isRemovingMember: null,
      showMembersSection: false,
      savedTeamId: null,
      form: {
        name: this.team?.name || '',
        teamLeadId: this.team?.teamLeadId || '',
      },
      selectedMemberId: '',
      teamLeadUsers: [],
      teamMemberUsers: [],
      currentMembers: [],
    }
  },
  computed: {
    currentTeam() {
      return this.team?.id ? this.team : null
    },
    availableTeamMembers() {
      // Исключаем уже добавленных участников и выбранного тимлида
      const currentMemberIds = this.currentMembers.map((m) => m.id)
      return this.teamMemberUsers.filter(
        (user) => user.id !== this.form.teamLeadId && !currentMemberIds.includes(user.id),
      )
    },
    getSelectedTeamLead() {
      return this.teamLeadUsers.find((user) => user.id === this.form.teamLeadId)
    },
  },
  async mounted() {
    await this.loadTeamLeads()
    await this.loadTeamMembers()

    if (this.currentTeam) {
      this.showMembersSection = true
      this.savedTeamId = this.currentTeam.id
      await this.loadCurrentMembers()
    }
  },
  methods: {
    async loadTeamLeads() {
      try {
        const user = JSON.parse(localStorage.getItem('user'))
        const departmentId = user?.departmentId

        if (!departmentId) {
          console.error('Department ID not found')
          return
        }

        const response = await axios.get(`${import.meta.env.VITE_API_URL}/department/team-leads`)
        this.teamLeadUsers = response.data
      } catch (error) {
        console.error('Ошибка при загрузке тимлидов:', error)
        this.teamLeadUsers = []
      }
    },

    async loadTeamMembers() {
      try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/department/team-members`)
        this.teamMemberUsers = response.data
      } catch (error) {
        console.error('Ошибка при загрузке участников:', error)
        this.teamMemberUsers = []
      }
    },

    async loadCurrentMembers() {
      if (!this.savedTeamId) return
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_API_URL}/department/teams/${this.savedTeamId}/members`,
        )

        this.currentMembers = Array.isArray(response.data) ? response.data : []
      } catch (error) {
        console.error('Ошибка при загрузке участников:', error)
        this.currentMembers = []
      }
    },

    async saveBasicInfo() {
      if (!this.form.name.trim() || !this.form.teamLeadId) {
        alert('Пожалуйста, заполните все обязательные поля')
        return
      }

      this.isLoading = true

      const user = JSON.parse(localStorage.getItem('user'))
      const departmentId = user?.departmentId

      if (!departmentId) {
        alert('Не удалось определить отдел')
        this.isLoading = false
        return
      }

      const payload = {
        name: this.form.name,
        teamLeadId: this.form.teamLeadId,
      }

      try {
        let response
        if (this.currentTeam) {
          // Редактирование
          response = await axios.put(
            `${import.meta.env.VITE_API_URL}/department/${departmentId}/teams/${this.currentTeam.id}`,
            payload,
          )
        } else {
          // Создание
          response = await axios.post(
            `${import.meta.env.VITE_API_URL}/department/${departmentId}/teams/create`,
            payload,
          )
        }

        this.savedTeamId = response.data.id || this.currentTeam?.id
        this.showMembersSection = true

        // Загружаем текущих участников после сохранения
        await this.loadCurrentMembers()

        this.$emit('save', response.data)

        // Показываем уведомление об успехе
        alert(this.currentTeam ? 'Команда успешно обновлена!' : 'Команда успешно создана!')
      } catch (error) {
        console.error('Ошибка при сохранении команды:', error)
        alert('Не удалось сохранить команду')
      } finally {
        this.isLoading = false
      }
    },

    async addTeamMember() {
      if (!this.selectedMemberId || !this.savedTeamId) return

      this.isAddingMember = true

      const user = JSON.parse(localStorage.getItem('user'))
      const departmentId = user?.departmentId

      try {
        await axios.post(
          `${import.meta.env.VITE_API_URL}/department/${departmentId}/teams/${this.savedTeamId}/add-member?userId=${this.selectedMemberId}`,
        )

        // Обновляем список участников
        await this.loadCurrentMembers()
        this.selectedMemberId = ''
      } catch (error) {
        console.error('Ошибка при добавлении участника:', error)
        alert('Не удалось добавить участника в команду')
      } finally {
        this.isAddingMember = false
      }
    },

    async removeMember(memberId) {
      if (!this.savedTeamId) return

      this.isRemovingMember = memberId

      const user = JSON.parse(localStorage.getItem('user'))
      const departmentId = user?.departmentId

      try {
        await axios.delete(
          `${import.meta.env.VITE_API_URL}/department/${departmentId}/teams/${this.savedTeamId}/remove-member?userId=${memberId}`,
        )

        // Обновляем список участников
        await this.loadCurrentMembers()
      } catch (error) {
        console.error('Ошибка при удалении участника:', error)
        alert('Не удалось удалить участника из команды')
      } finally {
        this.isRemovingMember = null
      }
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
  padding: 0.875rem 1.5rem;
  font-size: 0.875rem;
  font-weight: 600;
  border-radius: 1rem;
  background: linear-gradient(to right, #2563eb, #4f46e5, #7c3aed);
  color: #ffffff;
  box-shadow: 0 4px 14px rgba(59, 130, 246, 0.25);
  transition: all 0.2s ease-in-out;
  text-decoration: none;
  border: none;
  cursor: pointer;
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

.btn-primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
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
  cursor: pointer;
}

.btn-secondary:hover {
  background-color: #e2e8f0; /* hover:bg-slate-200 */
  border-color: #cbd5e1; /* hover:border-slate-300 */
}

.btn-secondary:active {
  transform: scale(0.98); /* active:scale-[0.98] */
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
