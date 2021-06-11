var jwt = require("jsonwebtoken");


function validateToken() {
    const token = localStorage.getItem("token")
    if(token) {
      const decode = jwt.decode(token)
      const now = Math.floor(Date.now() / 1000)
      if(!decode) {
        localStorage.removeItem("token")
        return false;
      }
      if(decode.exp < now) {
        localStorage.removeItem("token")
        return false;
      }
      else {
        return true;
      }
    }
    else {
        return false;
    }
  }

export default validateToken;