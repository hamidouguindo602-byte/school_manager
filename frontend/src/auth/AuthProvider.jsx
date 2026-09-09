import { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { clearAuthSession, getAuthSession, saveAuthSession } from './authStorage';
import { logoutRequest } from '../api/auth.api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => getAuthSession().token || null);
  const [user, setUser] = useState(() => getAuthSession().user || null);

  useEffect(() => {
    if (token && user) {
      saveAuthSession(token, user);
      return;
    }

    if (!token) {
      clearAuthSession();
    }
  }, [token, user]);

  const login = (newToken, newUser) => {
    setToken(newToken);
    setUser(newUser);
  };

  const logout = () => {
    logoutRequest();
    setToken(null);
    setUser(null);
    clearAuthSession();
  };

  const value = useMemo(
    () => ({
      token,
      user,
      isAuthenticated: Boolean(token),
      login,
      logout,
    }),
    [token, user],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error('useAuth must be used inside AuthProvider');
  }

  return context;
}
