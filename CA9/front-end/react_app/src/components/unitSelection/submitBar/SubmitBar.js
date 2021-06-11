import * as React from "react";
import "./submitBar-styles.css";
import { ToastContainer, toast } from 'react-toastify';
import API from '../../../apis/api';
import {enToFaNumber} from "../../../utils/utils" 
import authHeader from '../../../services/auth-header.js'

export default class SubmitBar extends React.Component{

     constructor(props) {
         super(props);
         this.state = {      
        }
     }

    resetSelections(props) {

        //var requestParam = new FormData();
        API.post('offering/reset', {headers: authHeader()}).then(resp => {
            if(resp.status == 200) {
                console.log("done");
                props.updateSelections();
            }
            else{
                toast.error('خطا در انجام عملیات')
            }}).catch(error => {
                console.log(error)
                if(error.response.status == 401 || error.response.status == 403) {
                    window.location.href = "http://localhost:3000/login"
                }
            })
    }

    finalizeSelections(props) {
        //var requestParam = new FormData();
        API.post('offering/finalize', {headers: authHeader()}).then(resp => {
            if(resp.status == 200) {
                if (resp.data === "OK") {
                    props.updateSelections();
                    console.log("done");
                    toast.success('تغییرات با موفقیت اعمال شد.')
                    window.location.href = "http://localhost:3000/schedule"
                }
                else {
                    toast.error(enToFaNumber(resp.data))
                }
            }
            else{
                toast.error('خطا در انجام عملیات')
            }}).catch(error => {
                console.log(error)
                if(error.response.status == 401 || error.response.status == 403) {
                    window.location.href = "http://localhost:3000/login"
                }
            })
    }

    render() {
        return (
            <div className="submit-bar col-sm-12">
                <div className="col-unit-count unit-count">
                    <div className="bold-item">
                        <span>
                            تعداد واحد ثبت شده: {this.props.selections ? enToFaNumber(this.props.selections.totalSubmittedUnits) : ''}
                        </span>
                    </div>
                </div>
                <div className="col-blank">
                    <span>
                        &nbsp;
                    </span>
                </div>
                <div className="col-refresh submit-box refresh">
                    <div className="clickable">
                        <span>
                            <i className="icon flaticon-refresh-arrow" 
                                onClick={() => this.resetSelections(this.props)}></i>
                        </span>
                    </div>
                </div>
                <div className="col-submit submit-box" onClick={() => this.finalizeSelections(this.props)}>
                    <div className="clickable">
                        <span>
                            ثبت نهایی
                        </span>
                    </div>
                </div>
            </div>
        );
    }
}
