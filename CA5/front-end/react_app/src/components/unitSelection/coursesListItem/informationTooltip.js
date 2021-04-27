import React from 'react';
import { toast } from 'react-toastify';
import API from '../../../apis/api';

function getClassData(courseCode, classCode) {
    API.get('offering/classTime/' + courseCode+ '/' + classCode).then(resp => {
        if(resp.status == 200) {
            console.log("success")
            return resp.data
        }
        else{
            toast.error('خطا در انجام عملیات')
            return undefined
        }}).catch(error => {
            console.log(error)
            if(error.response.status == 401 || error.response.status == 403) {
                window.location.href = "http://localhost:3000/login"
            }
            return undefined
        })
}

export default function InformationColumn(courseCode, classCode) {
    var classData = getClassData(courseCode, classCode)
    if(classData)
        console.log(classData)
    else
        console.log("fuck my life")
    return(
        <p>
            {/* {classTime} */}
            &nbsp;
            {/* {classDays} */}
            &nbsp;
            ______________
            &nbsp;

        </p>
    );
}