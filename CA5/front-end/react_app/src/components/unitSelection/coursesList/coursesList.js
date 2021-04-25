import React from 'react'
import './coursesList-styles.css'

import CoursesListItem from './coursesListItem/coursesListItem'

function CoursesList(props) {
    // console.log(props)
    return (
        <div className="course-selection-wrapper">
                
            <div className="course-selection course-list row">
                <div className="col-label label">
                    <span>
                        دروس ارائه شده
                    </span>
                </div>
                <div className="col-hidden">
                </div>
                <div className="courses">
                    <div className="filter-header">
                        <div className="row">
                            <div className="col-2 col-md">
                                <div className={["filter-index clickable", (props.searchFilter == "All") ? "filter-selected" : ""].join(' ')}
                                onClick={() => props.updateSearchFilter("All")}>
                                    <span>
                                        همه
                                    </span>
                                </div>
                            </div>
                            <div className="col-2 col-md">
                                <div className={["filter-index clickable", (props.searchFilter == "Takhasosi") ? "filter-selected" : ""].join(' ')}
                                onClick={() => props.updateSearchFilter("Takhasosi")}>
                                    <span>
                                        اختصاصی
                                    </span>
                                </div>
                            </div>
                            <div className="col-2 col-md">
                                <div className={["filter-index clickable", (props.searchFilter == "Asli") ? "filter-selected" : ""].join(' ')}
                                onClick={() => props.updateSearchFilter("Asli")}>
                                    <span>
                                        اصلی
                                    </span>
                                </div>
                            </div>
                            <div className="col-2 col-md">
                                <div className={["filter-index clickable", (props.searchFilter == "Paaye") ? "filter-selected" : ""].join(' ')}
                                onClick={() => props.updateSearchFilter("Paaye")}>
                                    <span>
                                        پایه
                                    </span>
                                </div>
                            </div>
                            <div className="col-2 col-md">
                                <div className={["filter-index clickable", (props.searchFilter == "Umumi") ? "filter-selected" : ""].join(' ')}
                                onClick={() => props.updateSearchFilter("Umumi")}>
                                    <span>
                                        عمومی
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="list">
                        <div className="selection-header">
                            <div className="row no-gutters">
                                <div className="col-add">
                                    <div className="header-item">
                                        <span>
                                            &nbsp;
                                        </span>
                                    </div>
                                </div>
                                <div className="col-code-2">
                                    <div className="header-item">
                                        <span>
                                            کد
                                        </span>
                                    </div>
                                </div>
                                <div className="col-capacity">
                                    <div className="header-item">
                                        <span>
                                            ظرفیت
                                        </span>
                                    </div>
                                </div>
                                <div className="col-type">
                                    <div className="header-item">
                                        <span>
                                            نوع
                                        </span>
                                    </div>
                                </div>
                                <div className="col-name-2">
                                    <div className="header-item">
                                        <span>
                                            نام درس
                                        </span>
                                    </div>
                                </div>
                                <div className="col-instructor-2">
                                    <div className="header-item">
                                        <span>
                                            استاد
                                        </span>
                                    </div>
                                </div>
                                <div className="col-units">
                                    <div className="header-item">
                                        <span>
                                            واحد
                                        </span>
                                    </div>
                                </div>
                                <div className="col-explain">
                                    <div className="header-item">
                                        <span>
                                            توضیحات
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="list">
                        {
                            props.courses == undefined ?
                            <div className="text-center mt-5">
                                <div className="spinner-border" role="status"></div>
                            </div> :
                            props.courses.map((course, index) => <CoursesListItem key={index} course={course}/>)
                        }
                    </div>
                </div>
            </div>
        </div>

    );
}

export default CoursesList