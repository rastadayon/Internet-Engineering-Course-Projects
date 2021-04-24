import React from 'react'
import './coursesList-styles.css'

function CoursesList(props) {
    return (
        <div class="course-selection-wrapper">
                
            <div class="course-selection course-list row">
                <div class="col-label label">
                    <span>
                        دروس ارائه شده
                    </span>
                </div>
                <div class="col-hidden">
                </div>
                <div class="courses">
                    <div class="filter-header">
                        <div class="row">
                            <div class="col-2 col-md">
                                <div class="filter-index clickable filter-selected">
                                    <span>
                                        همه
                                    </span>
                                </div>
                            </div>
                            <div class="col-2 col-md">
                                <div class="filter-index clickable">
                                    <span>
                                        اختصاصی
                                    </span>
                                </div>
                            </div>
                            <div class="col-2 col-md">
                                <div class="filter-index clickable">
                                    <span>
                                        اصلی
                                    </span>
                                </div>
                            </div>
                            <div class="col-2 col-md">
                                <div class="filter-index clickable">
                                    <span>
                                        پایه
                                    </span>
                                </div>
                            </div>
                            <div class="col-2 col-md">
                                <div class="filter-index clickable">
                                    <span>
                                        عمومی
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="list">
                        <div class="selection-header">
                            <div class="row no-gutters">
                                <div class="col-add">
                                    <div class="header-item">
                                        <span>
                                            &nbsp;
                                        </span>
                                    </div>
                                </div>
                                <div class="col-code-2">
                                    <div class="header-item">
                                        <span>
                                            کد
                                        </span>
                                    </div>
                                </div>
                                <div class="col-capacity">
                                    <div class="header-item">
                                        <span>
                                            ظرفیت
                                        </span>
                                    </div>
                                </div>
                                <div class="col-type">
                                    <div class="header-item">
                                        <span>
                                            نوع
                                        </span>
                                    </div>
                                </div>
                                <div class="col-name-2">
                                    <div class="header-item">
                                        <span>
                                            نام درس
                                        </span>
                                    </div>
                                </div>
                                <div class="col-instructor-2">
                                    <div class="header-item">
                                        <span>
                                            استاد
                                        </span>
                                    </div>
                                </div>
                                <div class="col-units">
                                    <div class="header-item">
                                        <span>
                                            واحد
                                        </span>
                                    </div>
                                </div>
                                <div class="col-explain">
                                    <div class="header-item">
                                        <span>
                                            توضیحات
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="list">
                        <div class="course-item">
                            <div class="row no-gutters">
                                <div class="col-add edit-option">
                                    <div class="edit add-sign clickable">
                                        <span>
                                            <i class="flaticon-add"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-code-2">
                                    <div class="course-index code">
                                        <span>
                                            ۱۱۲۰۰۰۱-۰۱
                                        </span>
                                    </div>
                                </div>
                                <div class="col-capacity">
                                    <div class="course-index bold-item">
                                        <span>
                                            ۲۶/۵۰
                                        </span>
                                    </div>
                                </div>
                                <div class="col-type">
                                    <div class="course-index course-status">
                                        <div class="type-box general">
                                            <span>
                                                عمومی
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-name-2">
                                    <div class="course-index">
                                        <span>
                                            اندیشه اسلامی ۱
                                        </span>
                                    </div>
                                </div>
                                <div class="col-instructor-2">
                                    <div class="course-index">
                                        <span>
                                            محمد تنها
                                        </span>
                                    </div>
                                </div>
                                <div class="col-units">
                                    <div class="course-index bold-item">
                                        <span>
                                            ۲
                                        </span>
                                    </div>
                                </div>
                                <div class="col-explain">
                                    <div class="selection-units">
                                        <span>
                                            &nbsp;
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}

export default CoursesList