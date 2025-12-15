// src/context/AuthContext.jsx
import React, { createContext, useContext, useEffect, useState } from "react";
import { login as apiLogin } from "../api";

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    try {
      const raw = localStorage.getItem("mw_user");
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  });
  const [token, setToken] = useState(() => localStorage.getItem("mw_token") || null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // persistencia
    if (user) localStorage.setItem("mw_user", JSON.stringify(user));
    else localStorage.removeItem("mw_user");
  }, [user]);

  useEffect(() => {
    if (token) localStorage.setItem("mw_token", token);
    else localStorage.removeItem("mw_token");
  }, [token]);

  async function login(credentials) {
    setLoading(true);
    try {
      const res = await apiLogin(credentials);
      // res => { raw, data, token } según api.js
      const { raw, data, token } = res;
      // data aquí debe ser usuario (UsuarioResponseDTO) o wrapper; intenta deducir
      const usuario = data && data.usuario ? data.usuario : data;
      // si token no vino en res.token, intenta ver en raw.headers.authorization o raw.data.token
      const t = token || raw?.headers?.authorization || raw?.data?.token || null;

      setUser(usuario);
      if (t) setToken(t.replace(/^Bearer\s+/i, ""));
      setLoading(false);
      return { success: true, usuario, token: t };
    } catch (err) {
      setLoading(false);
      // intenta extraer mensaje
      const msg = err?.response?.data?.message || err?.message || "Error en login";
      return { success: false, message: msg, error: err };
    }
  }

  function logout() {
    setUser(null);
    setToken(null);
    localStorage.removeItem("mw_user");
    localStorage.removeItem("mw_token");
  }

  const value = { user, token, login, logout, loading, isAuthenticated: !!user };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
