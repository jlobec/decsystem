class Auth {
  static getHeaders = () => {
    const token = sessionStorage.getItem("jwtToken");
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };
    return auth;
  };

  static isLoggedUser = () => {
    const token = sessionStorage.getItem("jwtToken");
    return token;
  };

  static logout = () => {
    sessionStorage.removeItem("jwtToken");
  };
}

export default Auth;
