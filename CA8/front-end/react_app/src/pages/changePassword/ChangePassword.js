import "../login/login-styles.css"
import * as React from "react";
import {toast} from "react-toastify";
import API from '../../apis/api';
import {Link} from "react-router-dom";
import validateToken from '../../services/validate-tokens'
import authToken from '../../services/auth-token'


export default class ChangePassword extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            password: '',
            confirmPassword: '',
            token: undefined
        }
        this.handlePasswordChange = this.handlePasswordChange.bind(this)
        this.handleConfirmPasswordChange = this.handleConfirmPasswordChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
        if(validateToken()) {
            window.location.href = "http://localhost:3000/"
        }
    }

    handleConfirmPasswordChange(event) {
        this.setState({
            confirmPassword: event.target.value
        });
    }
    handlePasswordChange(event) {
        this.setState({
            password: event.target.value
        });
    }

    componentDidMount() {
        document.title = "Change Password - Bolbolestan";
        document.body.classList.add("main-bg")
        toast.configure({rtl: true, className: "text-center", position: "top-right"});
        let search = window.location.search;
        let params = new URLSearchParams(search);
        let requestToken = params.get('token');
        this.setState({token : requestToken})
    }

    handleSubmit(e) {
        e.preventDefault();
        if(!this.state.password){
            console.log('password empty -_-')
            toast.error('رمز عبور جدید را باید وارد کنید')
            return
        }
        if(!this.state.confirmPassword){
            console.log('confirm empty -_-')
            toast.error('رمز عبور جدید را باید وارد کنید')
            return
        }
        if(this.state.confirmPassword !== this.state.password){
            console.log('confirmation -_-')
            toast.error('اطلاعات وارد شده با هم تطابق ندارند')
            return
        }
        var requestParam = new FormData();
        requestParam.append('newPassword', this.state.password);
        requestParam.append('token', authToken(this.state.token));
        API.post('auth/changePassword/', requestParam).then((resp) => {
            if(resp.status === 200) {
                console.log(resp.data);
                toast.success('رمز عبور با موفقیت تغییر یافت')
            }
        }).catch(error => {
            toast.error('تغییر رمز عبور با شکست روبرو شد')
        })
    }

    render() {
        return (
            <div className="login-container text-c animated flipInX">
                <h3 className="text-whitesmoke">رمز عبور جدید</h3>
                <div className="container-content">
                    <form className="margin-t" onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <input type="password" className="form-control" onChange={this.handlePasswordChange} placeholder="گذرواژه جدید" required=""/>
                        </div>

                        <div className="form-group">
                            <input type="password" className="form-control" onChange={this.handleConfirmPasswordChange} placeholder="تایید گذرواژه جدید" required=""/>
                        </div>
                        <button type="submit" className="form-button button-l margin-b">تغییر رمز عبور</button>
                        {this.state.isLoading &&
                        <span className="spinner-border mr-2" role="status" aria-hidden="true"/>
                        }
                        <p className="text-darkyblue"><Link to= "/login" style={{color: 'inherit'}}>
                                <small>رفتن به صفحه ورود</small>
                            </Link></p>
                    </form>
                </div>
            </div>
        );
    }
}
