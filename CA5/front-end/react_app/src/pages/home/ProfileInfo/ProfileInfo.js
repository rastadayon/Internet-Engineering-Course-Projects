import React from "react";
import PropTypes, { shape } from 'prop-types'

import "./profileinfo-styles.css"

function ProfileInfo(props) {
    console.log('in profile info')
    console.log(props.studentInfo.map())
    return (
        <div className="std-info col-sm-3">
            {/* <img className="avatar" src="../assets/images/robot.png" alt="avatar"/> */}
            <ul>
                <li>نام: <span className="std-name">{props.name}</span></li>
                <li>شماره دانشجویی: <span className="std-id">۸۱۰۱۹۶۰۰۰</span></li>
                <li>تاریخ تولد: <span className="std-birthday">۱۳۷۸/۱/۱</span></li>
                <li>معدل کل: <span className="std-gpa">۱۷.۸۲</span></li>
                <li>واحد گذرانده: <span className="std-units">۹۴.۰۰</span></li>
                <li>دانشکده: <span className="std-campus">پردیس دانشکده‌های فنی</span></li>
                <li>رشته: <span className="std-major">مهندسی کامپیوتر</span></li>
                <li>مقطع: <span className="std-level>">کارشناسی</span></li>
                <li><span className="std-status">مشغول به تحصیل</span></li>
            </ul>

        </div>
    );
}

ProfileInfo.propTypes = {
    studentInfo:  PropTypes.shape({
        id: PropTypes.string.isRequired,
        name: PropTypes.string.isRequired,
        secondName: PropTypes.string.isRequired,
        birthDate: PropTypes.string.isRequired,
        field: PropTypes.string.isRequired,
        status: PropTypes.string.isRequired,
        faculty: PropTypes.string.isRequired,
        level: PropTypes.string.isRequired,
        img: PropTypes.string.isRequired
    })
}

export default ProfileInfo;