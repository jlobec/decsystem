class Auth {
  static getHeaders = () => {
    const token = sessionStorage.getItem("jwtToken");
    const auth = {
      headers: { Authorization: "Bearer " + token }
    };
    return auth;
  };
}

export default Auth;
