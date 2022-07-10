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
        body: JSON.stringify({ username, password }),
      });

      if (!res.ok) throw new Error();

      const user: User = await res.json();

      localStorage.setItem("user", JSON.stringify(user));
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

      localStorage.removeItem("user");
      return Promise.resolve();
    } catch (error) {
      return Promise.reject(error);
    }
  },
  checkAuth: () =>
    localStorage.getItem("user") ? Promise.resolve() : Promise.reject(),
  checkError: (error) => {
    const status = error.status;
    if (status === 401 || status === 403) {
      localStorage.removeItem("user");
      return Promise.reject();
    }
    // other error code (404, 500, etc): no need to log out
    return Promise.resolve();
  },
  getIdentity: async () => {
    const user = localStorage.getItem("user");

    if (user === null) return Promise.reject();

    return Promise.resolve(JSON.parse(user));
  },
  getPermissions: () => Promise.resolve(""),
};

export async function me() {
  try {
    const storedUser = localStorage.getItem("user");
    if (!storedUser) return;

    const res = await fetch(`http://localhost:3080/sales/me`, {
      method: "GET",
      credentials: "include",
    });

    if (!res.ok) throw new Error();
  } catch (error) {
    localStorage.removeItem("user");
    console.log(error);
  }
}
