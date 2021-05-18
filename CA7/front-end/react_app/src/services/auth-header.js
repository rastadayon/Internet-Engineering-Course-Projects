import { toast } from "react-toastify";

var jwt = require("jsonwebtoken");



function authHeader() {
    const token = localStorage.getItem("token")
    if(token) {
      const decode = jwt.decode(token)
      console.log("---------------")
      console.log(token)
      const now = Math.floor(Date.now() / 1000)
      if(decode.exp < now) {
        toast.error('ورود شما منقضی شده است و باید  مجددا وارد شوید.')
        localStorage.removeItem("token")
        window.location.href = "http://localhost:3000/login"
      }
      else {
        return {Authorization: token}
      }
    }
    else {
      toast.error('شما وارد سیستم نشده‌اید باید لاگین کنید.')
      window.location.href = "http://localhost:3000/login"
    }
  }

  export default authHeader;