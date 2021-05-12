import React from 'react'
import './coursesListItem-styles.css'
import {enToFaNumber} from "../../../utils/utils"
import { toast } from 'react-toastify';
import API from '../../../apis/api';
import {
    createMuiTheme,
    MuiThemeProvider,
    withStyles
} from "@material-ui/core/styles";
import Tooltip from "@material-ui/core/Tooltip";
import InformationColumn from "./informationTooltip"

const theme = createMuiTheme({
    overrides: {
      MuiTooltip: {
        tooltip: {
          fontSize: "1em",
          color: "grey",
          backgroundColor: "white",
          fontFamily: "Vazir",
          direction: "rtl",
          textAlign: "center",
          borderRadius: "0px",
          border: "0.1em solid grey",
          width: "12em",
          whiteSpace: "pre-line !important",
          position: "absolute",
          right: "-3.7em"
        },
        arrow: {
            "&::before": {
                backgroundColor: "white",
                border: "0.1em solid grey"
            }
        }
      }
    }
});



export default class CoursesListItem extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            classData: undefined,
            examData: undefined,
            prerequisites: undefined
        }
        this.getIcon = this.getIcon.bind(this);
        this.getIconClass = this.getIconClass.bind(this);
        this.getAction = this.getAction.bind(this);
        this.getCapacityStyle = this.getCapacityStyle.bind(this);
        this.getCourseType = this.getCourseType.bind(this);
        this.getCourseTypeClass = this.getCourseTypeClass.bind(this);
        this.selectCourse = this.selectCourse.bind(this);
        this.getTooltipInfo = this.getTooltipInfo.bind(this);
        this.getClassTimeFromAPI = this.getClassTimeFromAPI.bind(this);
        this.getPrerequisitesFromAPI = this.getPrerequisitesFromAPI.bind(this);
    }

    componentDidMount() {
        if(this.props.course) {
            this.getClassTimeFromAPI(this.props.course.courseCode, this.props.course.classCode)
            this.getExamTimeFromAPI(this.props.course.courseCode, this.props.course.classCode)
            this.getPrerequisitesFromAPI(this.props.course.courseCode, this.props.course.classCode)
        }
    }

    getIcon(capacity, signedup) {
        if (signedup < capacity) {
            return "flaticon-add"
        }
        else {
            return "flaticon-clock-circular-outline"
        }
    }
    
    getIconClass(capacity, signedup) {
        if (signedup < capacity) {
            return "add-sign"
        }
        else {
            return "full-sign"
        }
    }
    
    getAction(capacity, signedup) {
        if (signedup < capacity) {
            return "add"
        }
        else {
            return "wait"
        }
    }
    
    getCapacityStyle(capacity, signedup) {
        let style = "course-index bold-item ";
        if (signedup >= capacity) {
            style = style + "unavailable";
        }
        return style;
    }
    
    getCourseType(type) {
        if (type == "Umumi")
            return "عمومی"
        else if (type == "Asli")
            return "اصلی"
        else if (type == "Takhasosi")
            return "تخصصی"
        else if (type == "Paaye")
            return "پایه"
    }
    
    getCourseTypeClass(type) {
        if (type == "Umumi")
            return "general"
        else if (type == "Asli")
            return "required"
        else if (type == "Takhasosi")
            return "specialized"
        else if (type == "Paaye")
            return "basic"
    }
    
    selectCourse(props) {
        var requestParam = new FormData();
        requestParam.append('courseCode', props.course.courseCode);
        requestParam.append('classCode', props.course.classCode);
    
        var action = this.getAction(props.course.capacity, props.course.signedUp)
    
        API.put('offering/' + action, requestParam).then(resp => {
            if(resp.status == 200) {
                if (resp.data === "OK") {
                    props.updateSelections();
                    console.log("done");
                }
                else {
                    if (action == "wait")
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

    getClassTimeFromAPI(courseCode, classCode) {
        API.get('offering/classTime/' + courseCode+ '/' + classCode).then(resp => {
            if(resp.status == 200) {
                this.setState({classData: resp.data})
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

    getExamTimeFromAPI(courseCode, classCode) {
        API.get('offering/examTime/' + courseCode+ '/' + classCode).then(resp => {
            if(resp.status == 200) {
                this.setState({examData: resp.data})
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

    getPrerequisitesFromAPI(courseCode, classCode) {
        API.get('offering/prerequisites/' + courseCode+ '/' + classCode).then(resp => {
            if(resp.status == 200) {
                if(resp.data.length == 0)
                    this.setState({prerequisites: '-'})
                else
                    this.setState({prerequisites: resp.data})
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

    getTooltipInfo() {
        return(
            <p>
                {this.state.classData ? enToFaNumber(this.state.classData.time) + '\n' : '-\n'}
            
                {this.state.classData ? this.state.classData.farsiDays + '\n' : '-\n'}

                ________________
                <br/>
                <b>پیش نیازی‌ها</b>
                <br/>

                {this.state.prerequisites ? this.state.prerequisites + '\n' : '-\n'}

                <b>امتحان</b>
                {'\n'}
                {this.state.examData ? enToFaNumber(this.state.examData.examDuration) 
                + ' - ' + enToFaNumber(this.state.examData.date) + '\n': '-\n'}
            </p>
            
        );
    }

    render() {
        return(
            <div className="course-item selected">
                <MuiThemeProvider theme={theme}>
                <Tooltip title={this.getTooltipInfo()} placement="right" arrow>
                <div className="row no-gutters">
                    {/* <div className="tooltip"> */}
                    <div className="col-add edit-option first-item">
                        <div className={["edit", this.getIconClass(this.props.course.capacity, this.props.course.signedUp), "clickable"].join(' ')}>
                            <span>
                                <i className={this.getIcon(this.props.course.capacity, this.props.course.signedUp)} 
                                    onClick={() => this.selectCourse(this.props)}></i>
                            </span>
                        </div>
                    </div>
                    <div className="col-code-2 item">
                        <div className="course-index code">
                            <span>
                                {enToFaNumber(this.props.course.courseCode) + '-' + enToFaNumber(this.props.course.classCode)}
                            </span>
                        </div>
                    </div>
                    <div className="col-capacity item">
                        <div className={this.getCapacityStyle(this.props.course.capacity, this.props.course.signedUp)}>
                            <span>
                                {enToFaNumber(this.props.course.signedUp) + '/' + enToFaNumber(this.props.course.capacity)}
                            </span>
                        </div>
                    </div>
                    <div className="col-type item">
                        <div className="course-index course-status">
                            <div className={["type-box", this.getCourseTypeClass(this.props.course.type)].join(' ')}>
                                <span>
                                    {this.getCourseType(this.props.course.type)}
                                </span>
                            </div>
                        </div>
                    </div>
                    <div className="col-name-2 item">
                        <div className="course-index">
                            <span>
                                {this.props.course.name}
                            </span>
                        </div>
                    </div>
                    <div className="col-instructor-2 item">
                        <div className="course-index">
                            <span>
                                {this.props.course.instructor}
                            </span>
                        </div>
                    </div>
                    <div className="col-units last-item">
                        <div className="course-index bold-item">
                            <span>
                                {enToFaNumber(this.props.course.units)}
                            </span>
                        </div>
                    </div>
                    {/* </div> */}
                    {/* <div className="col-explain">
                        <div className="selection-units">
                        <span>
                        &nbsp;
                        </span>
                        </div>
                    </div> */}
                </div>
                    </Tooltip>
                    </MuiThemeProvider>
            </div>
        );
    }

}