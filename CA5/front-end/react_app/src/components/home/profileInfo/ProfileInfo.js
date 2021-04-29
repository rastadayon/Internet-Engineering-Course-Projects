import React from "react";
import {enToFaNumber} from "../../../utils/utils"

import "./profileinfo-styles.css"

function ProfileInfo(props) {
    // console.log(props.studentInfo)

    return (
        <div className="std-info col-sm-3">
            <ul>
                <li><img className="avatar" src={props.studentInfo ? props.studentInfo.img : '-'} alt="avatar"/></li>
                <li>نام: <span className="std-name">{props.studentInfo ? props.studentInfo.name + ' ' + props.studentInfo.secondName : '-'}</span></li>
                <li>شماره دانشجویی: <span className="std-id">{props.studentInfo ? enToFaNumber(props.studentInfo.id) : '-'}</span></li>
                <li>تاریخ تولد: <span className="std-birthday">{props.studentInfo ? enToFaNumber(props.studentInfo.birthDate) : '-'}</span></li>
                <li>معدل کل: <span className="std-gpa">{props.studentInfo ? enToFaNumber(props.studentInfo.gpa) : '-'}</span></li>
                <li>واحد گذرانده: <span className="std-units">{props.studentInfo ? enToFaNumber(props.studentInfo.totalUnits) : '-'}</span></li>
                <li>دانشکده: <span className="std-campus">{props.studentInfo ? props.studentInfo.faculty : '-'}</span></li>
                <li>رشته: <span className="std-major">{props.studentInfo ? props.studentInfo.field : '-'}</span></li>
                <li>مقطع: <span className="std-level>">{props.studentInfo ? props.studentInfo.level : '-'}</span></li>
                <li><span className="std-status">{props.studentInfo ? props.studentInfo.status : '-'}</span></li>
            </ul>

        </div>
    );
}

export default ProfileInfo;