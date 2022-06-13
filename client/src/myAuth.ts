import { AuthProvider } from "react-admin";

type user = {
  id: number;
  fullName: string;
  avatar: string;
};

let isLogedIn = true;

export const myAuth: AuthProvider = {
  login: () => {
    isLogedIn = true;
    return Promise.resolve();
  },
  //   login: ({ username, password }) => {
  //     const request = new Request("http://localhost:3080/login", {
  //       method: "POST",
  //       body: JSON.stringify({ username, password }),
  //       headers: new Headers({ "Content-Type": "application/json" }),
  //     });
  //     return fetch(request)
  //       .then((response) => {
  //         if (response.status < 200 || response.status >= 300) {
  //           throw new Error(response.statusText);
  //         }
  //         return response.json();
  //       })
  //       .then(() => {
  //         return Promise.resolve();
  //       })
  //       .catch(() => {
  //         throw new Error("Network error");
  //       });
  //   },
  logout: () => {
    isLogedIn = false;
    return Promise.resolve();
  },
  //   logout: () => {
  //     const request = new Request("http://localhost:3080/logout", {
  //       method: "POST",
  //     });
  //     return fetch(request)
  //       .then((response) => {
  //         if (response.status < 200 || response.status >= 300) {
  //           throw new Error(response.statusText);
  //         }
  //         return response.json();
  //       })
  //       .then(() => {
  //         return Promise.resolve();
  //       })
  //       .catch(() => {
  //         throw new Error("Network error");
  //       });
  //   },
  checkError: () => Promise.resolve(),
  checkAuth: () => (isLogedIn ? Promise.resolve() : Promise.reject()),
  getPermissions: () => Promise.resolve(),
  getIdentity: () => Promise.resolve({ id: 1, fullName: "test" }),
  //   getIdentity: () => {
  //     const request = new Request("http://localhost:3080/logout", {
  //       method: "POST",
  //     });
  //     return fetch(request)
  //       .then((response) => {
  //         if (response.status < 200 || response.status >= 300) {
  //           throw new Error(response.statusText);
  //         }
  //         return response.json();
  //       })
  //       .then((user: user) => {
  //         return user;
  //       })
  //       .catch(() => {
  //         throw new Error("Network error");
  //       });
  //   },
};
