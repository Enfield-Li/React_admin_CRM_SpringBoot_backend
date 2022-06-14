import { AuthProvider } from "react-admin";

type User = {
  id: number;
  fullName: string;
  avatar: string;
};
// https://marmelab.com/react-admin/AuthProviderWriting.html#:~:text=%3D%20%7B%0A%20%20%20%20//%20...-,Example,-Here%20is%20a
export const myAuth: AuthProvider = {
  login: async ({ username, password }) => {
    try {
      const res = await fetch("http://localhost:3080/sales/login", {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ saleName: username, rawPassword: password }),
      });
      const user: User = await res.json();

      localStorage.setItem("saleName", user.fullName);
    } catch (error) {
      return Promise.reject(error);
    }
  },
  logout: () => {
    try {
      fetch("http://localhost:3080/sales/logout", {
        method: "POST",
        credentials: "include",
      });

      localStorage.removeItem("saleName");
      return Promise.resolve();
    } catch (error) {
      return Promise.reject(error);
    }
  },
  checkAuth: () =>
    localStorage.getItem("saleName") ? Promise.resolve() : Promise.reject(),
  checkError: (error) => {
    const status = error.status;
    if (status === 401 || status === 403) {
      localStorage.removeItem("saleName");
      return Promise.reject();
    }
    // other error code (404, 500, etc): no need to log out
    return Promise.resolve();
  },
  getIdentity: async () => {
    try {
      const res = await fetch("http://localhost:3080/sales/verify", {
        method: "POST",
        credentials: "include",
      });

      if (res.ok) {
        const user: User = await res.json();
        localStorage.setItem("saleName", user.fullName);
        return Promise.resolve(user);
      }

      localStorage.removeItem("saleName");
      return Promise.reject();
    } catch (error) {
      return Promise.reject(error);
    }
  },
  getPermissions: () => Promise.resolve(""),
};
