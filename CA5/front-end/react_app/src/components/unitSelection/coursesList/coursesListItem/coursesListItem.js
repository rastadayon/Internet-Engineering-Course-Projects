import React from 'react'
import './courseListItem-styles.css'

function CoursesListItem(props) {
    return(
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
    );
}