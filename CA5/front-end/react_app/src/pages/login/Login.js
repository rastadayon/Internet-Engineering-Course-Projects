import "../../assets/styles/login-styles.css"
import * as React from "react";
import {toast} from "react-toastify";
import API from '../../apis/api';


export default class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
        }
        this.handleEmailChange = this.handleEmailChange.bind(this)
        this.handlePasswordChange = this.handlePasswordChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleEmailChange(event) {
        this.setState({
            email: event.target.value
        });
    }
    handlePasswordChange(event) {
        this.setState({
            password: event.target.value
        });
    }

    componentDidMount() {
        document.title = "Log in - Bolbolestan";
        document.body.classList.add("main-bg")
        toast.configure({rtl: true, className: "text-center", position: "top-right"});
    }

    handleSubmit(e) {
        e.preventDefault();
        if(!this.state.email){
            console.log('email empty -_-')
            toast.error('فیلد ایمیل باید پر باشد')
            return
        }
        API.post('student/login/', {
            email: this.state.email
        }).then((resp) => {
            if(resp.status === 200) {
                console.log('شد شد')
                toast.success('ورود با موفقیت انجام شد.')
            }
        }).catch(error => {
            console.log('نشد')
            toast.error('ایمیل نادرست است')
        })
    }

    validateToken() {
        if (typeof this.state.email != "string") return false
        console.log('!isNaN(this.state.email)')
        return !isNaN(this.state.email)
    }

    render() {
        return (
            <div className="login-container text-c animated flipInX">
                <h3 className="text-whitesmoke">ورود به سامانه بلبلستان</h3>
                <div className="container-content">
                    <form className="margin-t" onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <input type="text" className="form-control" onChange={this.handleEmailChange} placeholder="email" required=""/>
                        </div>

                        <div className="form-group">
                            <input type="password" className="form-control" onChange={this.handlePasswordChange} placeholder="password" required=""/>
                        </div>
                        <button type="submit" className="form-button button-l margin-b">ورود</button>
                        {this.state.isLoading &&
                        <span className="spinner-border mr-2" role="status" aria-hidden="true"/>
                        }
                        <p className="text-whitesmoke text-center"><small>هنوز اکانت ندارید؟</small></p>
                        <a className="text-darkyblue" href="signup.html"><small>اکانت بسازید</small></a>
                    </form>
                </div>
            </div>
        );
    }
}