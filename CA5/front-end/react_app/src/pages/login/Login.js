import "../../assets/styles/login-styles.css"
import * as React from "react";
import {Link} from "react-router-dom";

export default class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {}
    }

    componentDidMount() {
        document.title = "Log in - Bolbolestan";
        document.body.classList.add("main-bg")
    }

    render() {
        return (
            <div className="login-container text-c animated flipInX">
                <h3 className="text-whitesmoke">ورود به سامانه بلبلستان</h3>
                <div className="container-content">
                    <form className="margin-t">
                        <div className="form-group">
                            <input type="text" className="form-control" placeholder="email" required=""/>
                        </div>

                        <div className="form-group">
                            <input type="password" className="form-control" placeholder="password" required=""/>
                        </div>
                        <button type="submit" className="form-button button-l margin-b">ورود</button>
                            
                        <p className="text-whitesmoke text-center"><small>هنوز اکانت ندارید؟</small></p>
                        <a className="text-darkyblue" href="signup.html"><small>اکانت بسازید</small></a>
                    </form>
                </div>
            </div>
        );
    }
}