import axios from "axios";

const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8086/api";

const api = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
});

// interceptor para añadir Authorization si hay token en localStorage
api.interceptors.request.use(
  (cfg) => {
    const t = localStorage.getItem("mw_token");
    if (t)
      cfg.headers = { ...(cfg.headers || {}), Authorization: `Bearer ${t}` };
    return cfg;
  },
  (err) => Promise.reject(err)
);

// helper para extraer data del wrapper {success,message,data}
function unwrap(res) {
  if (!res) return res;
  if (res.data && typeof res.data === "object") {
    if (res.data.data !== undefined) return res.data.data;
    return res.data;
  }
  return res;
}

/* ---------- Autenticación ---------- */
export const login = async (payload) => {
  const res = await api.post("/usuarios/login", payload);
  const data = res?.data;
  const token =
    data?.data?.token || data?.token || res?.headers?.authorization || null;
  return { raw: res, data: unwrap(res), token };
};

export function setAuthToken(token) {
  if (token) localStorage.setItem("mw_token", token.replace(/^Bearer\s+/i, ""));
  else localStorage.removeItem("mw_token");
}

export function clearAuthToken() {
  localStorage.removeItem("mw_token");
}

/* ---------- Clientes / Mascotas ---------- */
export const getClientes = () => api.get("/clientes").then(unwrap);
export const getClienteById = (id) => api.get(`/clientes/${id}`).then(unwrap);
export const createCliente = (payload) =>
  api.post("/clientes", payload).then(unwrap);
export const updateCliente = (id, payload) =>
  api.put(`/clientes/${id}`, payload).then(unwrap);
export const deleteCliente = (id) => api.delete(`/clientes/${id}`).then(unwrap);

export const getMascotas = () => api.get("/mascotas").then(unwrap);
export const getMascotasByCliente = (clienteId) =>
  api.get(`/mascotas/cliente/${clienteId}`).then(unwrap);
export const getMascotaById = (id) => api.get(`/mascotas/${id}`).then(unwrap);
export const createMascota = (payload) =>
  api.post("/mascotas", payload).then(unwrap);
export const updateMascota = (id, payload) =>
  api.put(`/mascotas/${id}`, payload).then(unwrap);
export const deleteMascota = (id) => api.delete(`/mascotas/${id}`).then(unwrap);

/* ---------- Servicios / Personal ---------- */
export const getServicios = () => api.get("/servicios").then(unwrap);
export const getServicioById = (id) => api.get(`/servicios/${id}`).then(unwrap);
export const createServicio = (payload) =>
  api.post("/servicios", payload).then(unwrap);
export const updateServicio = (id, payload) =>
  api.put(`/servicios/${id}`, payload).then(unwrap);
export const deleteServicio = (id) =>
  api.delete(`/servicios/${id}`).then(unwrap);

export const getPersonal = () => api.get("/personal").then(unwrap);
export const getPersonalById = (id) => api.get(`/personal/${id}`).then(unwrap);
export const createPersonal = (payload) =>
  api.post("/personal", payload).then(unwrap);
export const updatePersonal = (id, payload) =>
  api.put(`/personal/${id}`, payload).then(unwrap);
export const deletePersonal = (id) =>
  api.delete(`/personal/${id}`).then(unwrap);

/* ---------- Agenda / Citas ---------- */
export const getAgendaByFecha = (fecha) =>
  api
    .get(`/agenda/periodo?fechaInicio=${fecha}&fechaFin=${fecha}`)
    .then(unwrap);

export const createAgenda = (payload) =>
  api.post("/agenda", payload).then(unwrap);

export const updateAgenda = (id, payload) =>
  api.put(`/agenda/${id}`, payload).then(unwrap);
export const getAgendaById = (id) => api.get(`/agenda/${id}`).then(unwrap);

export const validarConflictos = ({
  fecha,
  horaInicio,
  horaFin,
  idsPersonal = [],
}) => {
  const ids = Array.isArray(idsPersonal) ? idsPersonal.join(",") : idsPersonal;
  return api
    .post(
      `/agenda/validar/conflictos?fecha=${fecha}&horaInicio=${horaInicio}&horaFin=${horaFin}&idsPersonal=${ids}`
    )
    .then(unwrap);
};

/* ---------- Horarios / Disponibilidad / Festivos / Bloqueos ---------- */
export const getHorarioDia = (diaSemana) =>
  api.get(`/horarios/dia/${diaSemana}`).then(unwrap);

export const getFestivoByFecha = (fecha) =>
  api.get(`/festivos/fecha/${fecha}`).then(unwrap);

export const getBloqueos = () => api.get("/bloqueos").then(unwrap);
export const createBloqueo = (payload) =>
  api.post("/bloqueos", payload).then(unwrap);
export const updateBloqueo = (id, payload) =>
  api.put(`/bloqueos/${id}`, payload).then(unwrap);
export const deleteBloqueo = (id) => api.delete(`/bloqueos/${id}`).then(unwrap);

/* ---------- Disponibilidad Personal ---------- */
export const getDisponibilidadPersonal = (idPersonal, inicio, fin) =>
  api
    .get(
      `/disponibilidad-personal/personal/${idPersonal}?inicio=${inicio}&fin=${fin}`
    )
    .then(unwrap);

export const createDisponibilidadPersonal = (payload) =>
  api.post("/disponibilidad-personal", payload).then(unwrap);

export const updateDisponibilidadPersonal = (id, payload) =>
  api.put(`/disponibilidad-personal/${id}`, payload).then(unwrap); // ✅ NUEVO

export const deleteDisponibilidadPersonal = (id) =>
  api.delete(`/disponibilidad-personal/${id}`).then(unwrap);

/* ---------- Recordatorios ---------- */
export const getRecordatoriosPendientes = () =>
  api.get("/recordatorios/pendientes").then(unwrap);
export const createRecordatorio = (payload) =>
  api.post("/recordatorios", payload).then(unwrap);
export const marcarRecordatorioEnviado = (id) =>
  api.put(`/recordatorios/${id}/enviar`).then(unwrap);

/* ---------- Reportes ---------- */
export const reporteDia = (fecha) =>
  api.get(`/agenda/reporte/dia?fecha=${fecha}`).then(unwrap);
export const reportePeriodo = (fechaInicio, fechaFin) =>
  api
    .get(
      `/agenda/reporte/periodo?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
    )
    .then(unwrap);
export const indicadoresPersonal = (fechaInicio, fechaFin) =>
  api
    .get(
      `/agenda/reporte/personal?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`
    )
    .then(unwrap);

export default api;
