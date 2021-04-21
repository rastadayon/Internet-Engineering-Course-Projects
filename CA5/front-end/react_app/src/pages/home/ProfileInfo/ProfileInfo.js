import React from "react";
import PropTypes, { shape } from 'prop-types'

import "./profileinfo-styles.css"

function ProfileInfo(props) {
    console.log(props.studentInfo)
    // console.log(typeof props.studentInfo)
    // console.log(props.studentInfo.entries())

    return (
        <div className="std-info col-sm-3">
            {/* <img className="avatar" src={props.studentInfo ? props.studentInfo.img : '-'} alt="avatar"/> */}
            <ul>
                <li>نام: <span className="std-name">{props.studentInfo ? props.studentInfo.name + ' ' + props.studentInfo.secondName : '-'}</span></li>
                <li>شماره دانشجویی: <span className="std-id">{props.studentInfo ? props.studentInfo.id : '-'}</span></li>
                <li>تاریخ تولد: <span className="std-birthday">{props.studentInfo ? props.studentInfo.birthDate : '-'}</span></li>
                <li>معدل کل: <span className="std-gpa">۱۷.۸۲</span></li>
                <li>واحد گذرانده: <span className="std-units">۹۴.۰۰</span></li>
                <li>دانشکده: <span className="std-campus">{props.studentInfo ? props.studentInfo.faculty : '-'}</span></li>
                <li>رشته: <span className="std-major">{props.studentInfo ? props.studentInfo.field : '-'}</span></li>
                <li>مقطع: <span className="std-level>">{props.studentInfo ? props.studentInfo.level : '-'}</span></li>
                <li><span className="std-status">{props.studentInfo ? props.studentInfo.status : '-'}</span></li>
            </ul>

        </div>
    );
}

export default ProfileInfo;