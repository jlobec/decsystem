 UserActions = {

     signIn = async (event, {email, password})=> {
        event.preventDefault();
      
        // Send login user request to backend
        const url = config.baseUrl + "api/user/auth/signin";
        const body = {
          nicknameOrEmail: email,
          password: password
        };
        try {
          const { data: signInResult } = await axios.post(url, body);
          console.log(signInResult);
          this.setState({ error: false });
          this.clearForm();
          this.props.history.push("/my");
        } catch (error) {
          this.clearForm();
          this.setState({ error: true });
        }
      }

};

export default UserActions;


