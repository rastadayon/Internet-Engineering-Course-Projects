import "./signup-styles.css"
import * as React from "react";
import {toast} from "react-toastify";
import API from '../../apis/api';
import { Link } from "react-router-dom";
import validateToken from "../../services/validate-tokens";


export default class SignUp extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            secondName: '',
            birthDate: '',
            studentId: '',
            field: '',
            faculty: '',
            level: '',
            email: '',
            password: '',
        }
        this.handleNameChange = this.handleNameChange.bind(this)
        this.handleSecondNameChange = this.handleSecondNameChange.bind(this)
        this.handleStudentIdChange = this.handleStudentIdChange.bind(this)
        this.handleBirthDateChange = this.handleBirthDateChange.bind(this)
        this.handleFieldChange = this.handleFieldChange.bind(this)
        this.handleFacultyChange = this.handleFacultyChange.bind(this)
        this.handleLevelChange = this.handleLevelChange.bind(this)
        this.handleEmailChange = this.handleEmailChange.bind(this)
        this.handlePasswordChange = this.handlePasswordChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)

        if(validateToken()) {
            window.location.href = "http://localhost:3000/"
        }
    }

    handleNameChange(event) {
        this.setState({
            name: event.target.value
        });
    }

    handleSecondNameChange(event) {
        this.setState({
            secondName: event.target.value
        });
    }

    handleStudentIdChange(event) {
        this.setState({
            studentId: event.target.value
        });
    }

    handleFieldChange(event) {
        this.setState({
            field: event.target.value
        });
    }

    handleFacultyChange(event) {
        this.setState({
            faculty: event.target.value
        });
    }

    handleLevelChange(event) {
        this.setState({
            level: event.target.value
        });
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

    handleBirthDateChange(event) {
        this.setState({
            birthDate: event.target.value
        });
    }

    componentDidMount() {
        document.title = "Sign up - Bolbolestan";
        document.body.classList.add("main-bg")
        toast.configure({rtl: true, className: "text-center", position: "top-right"});

    }

    handleSubmit(e) {
        e.preventDefault();
        var shouldReturn = false;
        if(!this.state.email){
            console.log('email empty -_-')
            toast.warning('فیلد ایمیل باید پر باشد')
            shouldReturn = true;
        }
        if(!this.state.password){
            console.log('password empty -_-')
            toast.warning('فیلد گذرواژه باید پر باشد')
            shouldReturn = true;
        }
        if(!this.state.name){
            console.log('name empty -_-')
            toast.warning('فیلد نام باید پر باشد')
            shouldReturn = true;
        }
        if(!this.state.secondName){
            console.log('secondName empty -_-')
            toast.warning('فیلد نام خانوادگی باید پر باشد')
            shouldReturn = true;
        }
        if(!this.state.birthDate){
            console.log('birthDate empty -_-')
            toast.warning('فیلد تاریخ تولد باید پر باشد')
            shouldReturn = true;
        }
        if(!this.state.studentId){
            console.log('studentId empty -_-')
            toast.warning('فیلد شناسه دانشجویی باید پر باشد')
            shouldReturn = true;
        }
        if(!this.state.field){
            console.log('field empty -_-')
            toast.warning('فیلد رشته باید پر باشد')
            shouldReturn = true;
        }
        if(!this.state.faculty){
            console.log('faculty empty -_-')
            toast.warning('فیلد دانشکده باید پر باشد')
            shouldReturn = true;
        }
        if(!this.state.level){
            console.log('level empty -_-')
            toast.warning('فیلد مقطع تحصیلی باید پر باشد')
            shouldReturn = true;
        }
        if(shouldReturn)
            return;
        API.post('auth/signup', {
            name: this.state.name,
            secondName: this.state.secondName,
            studentId: this.state.studentId,
            birthDate: this.state.birthDate,
            field: this.state.field,
            faculty: this.state.faculty,
            level: this.state.level,
            email: this.state.email,
            password: this.state.password
        }).then((resp) => {
            if(resp.status === 200) {
                console.log(resp.data);
                console.log('شد شد')
                toast.success('ساخت اکانت با موفقیت انجام شد - وارد شوید')
            }
        }).catch(error => {
            console.log('نشد')
            toast.error('ساخت اکانت موفقیت آمیز نبود - ایمیل تکراری')
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
                                <input type="text" className="form-control" onChange={this.handleNameChange} placeholder="نام" required=""/>
                        </div>
                        <div className="form-group">
                            <input type="text" className="form-control" onChange={this.handleSecondNameChange} placeholder="نام خانوادگی" required=""/>
                        </div>
                        <div className="form-group">
                            <input type="text" className="form-control" onChange={this.handleBirthDateChange} placeholder="تاریخ تولد" required=""/>
                        </div>
                        <div className="form-group">
                            <input type="text" className="form-control" onChange={this.handleStudentIdChange} placeholder="شناسه دانشجویی" required=""/>
                        </div>
                        <div className="form-group">
                            <input type="text" className="form-control" onChange={this.handleFieldChange} placeholder="رشته" required=""/>
                        </div>
                        <div className="form-group">
                            <input type="text" className="form-control" onChange={this.handleFacultyChange} placeholder="دانشکده" required=""/>
                        </div>
                        <div className="form-group">
                            <input type="text" className="form-control" onChange={this.handleLevelChange} placeholder="مقطع تحصیلی" required=""/>
                        </div>
                        <div className="form-group">
                            <input type="text" className="form-control" onChange={this.handleEmailChange} placeholder="ایمیل" required=""/>
                        </div>
                        <div className="form-group">
                            <input type="password" className="form-control" onChange={this.handlePasswordChange} placeholder="گذرواژه" required=""/>
                        </div>
                        <button type="submit" className="form-button button-l margin-b">ساخت اکانت</button>
                        {this.state.isLoading &&
                        <span className="spinner-border mr-2" role="status" aria-hidden="true"/>
                        }
                        <p class="text-whitesmoke text-center"><small>اکانت ساخته اید؟</small></p>
                        <Link to='/login'><small>وارد شوید</small></Link>
                    </form>
                </div>
            </div>
        );
    }
}
