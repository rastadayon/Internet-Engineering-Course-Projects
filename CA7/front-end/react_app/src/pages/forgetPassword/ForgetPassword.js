import "../login/login-styles.css"
import * as React from "react";
import {toast} from "react-toastify";
import API from '../../apis/api';
import {Link} from "react-router-dom";


export default class ForgetPassword extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
        }
        this.handleEmailChange = this.handleEmailChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleEmailChange(event) {
        this.setState({
            email: event.target.value
        });
    }

    componentDidMount() {
        document.title = "Forget Password - Bolbolestan";
        document.body.classList.add("main-bg")
        toast.configure({rtl: true, className: "text-center", position: "top-right"});
    }

    handleSubmit(e) {
        var requestParam = new FormData();
        requestParam.append('email', this.state.email);
        e.preventDefault();
        if(!this.state.email){
            console.log('email empty -_-')
            toast.warning('فیلد ایمیل باید پر باشد')
            return
        }
        API.post('auth/forget/', requestParam).then((resp) => {
            if(resp.status === 200) {
                console.log(resp.data);
                
                toast.success('لینک بازیابی رمز عبور به ایمیل شما ارسال شد.')
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
                <h3 className="text-whitesmoke">بازیابی رمز عبور</h3>
                <div className="container-content">
                    <form className="margin-t" onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <input type="text" className="form-control" onChange={this.handleEmailChange} placeholder="email" required=""/>
                        </div>
                        <button type="submit" className="form-button button-l margin-b">ارسال لینک بازیابی به ایمیل</button>
                        {this.state.isLoading &&
                        <span className="spinner-border mr-2" role="status" aria-hidden="true"/>
                        }
                        <p className="text-whitesmoke text-center"><small>حساب کاربری دارید؟</small></p>
                        <p className="text-darkyblue"><Link to= "/login" style={{color: 'inherit'}}>
                                <small>وارد شوید</small>
                            </Link></p>
                    </form>
                </div>
            </div>
        );
    }
}
